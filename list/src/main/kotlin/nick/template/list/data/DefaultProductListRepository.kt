package nick.template.list.data

import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import nick.template.list.models.LocalProduct
import nick.template.list.models.ProductsPacket
import nick.template.list.models.RemoteProduct
import nick.template.local.dao.ProductsDao
import nick.template.logging.Logger
import nick.template.remote.client.ProductsClient

internal class DefaultProductListRepository @Inject constructor(
    private val client: ProductsClient,
    private val dao: ProductsDao,
    private val logger: Logger
) : ProductListRepository {

    override fun products(): Flow<ProductsPacket> {
        return flow {
            emit(State.Loading)

            try {
                val products = client.products()
                emit(State.Success(products))
            } catch (throwable: Throwable) {
                if (throwable is CancellationException) throw throwable
                logger.e("Failed to get products", throwable)
                emit(State.Error(throwable))
            }
        }.flatMapLatest { state ->
            val productItems = dao.queryAll().map(List<LocalProduct>::toProductItems)

            when (state) {
                State.Loading -> productItems.map(ProductsPacket::Cached)
                is State.Success -> {
                    val latestProducts = state.remoteProducts.toLocalProducts()
                    dao.nukeThenInsert(latestProducts)
                    productItems.map(ProductsPacket::Fresh)
                }
                is State.Error -> productItems.map { products -> ProductsPacket.Error(state.throwable, products) }
            }
        }
    }

    private sealed class State {
        object Loading : State()
        data class Success(val remoteProducts: List<RemoteProduct>) : State()
        data class Error(val throwable: Throwable) : State()
    }
}
