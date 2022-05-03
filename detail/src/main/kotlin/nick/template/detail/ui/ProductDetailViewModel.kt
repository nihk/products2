package nick.template.detail.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import nick.template.detail.models.LoadProductEvent
import nick.template.detail.models.LoadProductResult
import nick.template.detail.models.ProductDetailEvent
import nick.template.detail.models.ProductDetailResult
import nick.template.detail.models.ProductDetailState
import nick.template.local.dao.ProductsDao
import nick.template.logging.Logger
import nick.template.mvi.MviViewModel

internal class ProductDetailViewModel(
    private val dao: ProductsDao,
    private val id: String,
    private val logger: Logger
) : MviViewModel<ProductDetailEvent, ProductDetailResult, ProductDetailState, Nothing>(
    ProductDetailState()
) {
    override fun onSubscription() {
        processEvent(LoadProductEvent(id))
    }

    override fun Flow<ProductDetailEvent>.toResults(): Flow<ProductDetailResult> {
        return merge(
            filterIsInstance<LoadProductEvent>().toLoadProductResults()
        )
    }

    override fun ProductDetailResult.reduce(state: ProductDetailState): ProductDetailState {
        return when (this) {
            is LoadProductResult -> state.copy(
                product = product
            )
            else -> state
        }
    }

    private fun Flow<LoadProductEvent>.toLoadProductResults(): Flow<ProductDetailResult> {
        return flatMapLatest { event -> dao.queryById(event.id) }
            .onEach { product -> if (product == null) logger.e("Product was null") }
            .filterNotNull()
            .map { product -> LoadProductResult(product.toProductDetailItem()) }
    }

    // todo: use creationextras
    class Factory @Inject constructor(
        private val dao: ProductsDao,
        private val logger: Logger
    ) {
        fun create(
            id: String,
            owner: SavedStateRegistryOwner
        ): ViewModelProvider.Factory {
            return object : AbstractSavedStateViewModelFactory(owner, null) {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return ProductDetailViewModel(dao, id, logger) as T
                }
            }
        }
    }
}
