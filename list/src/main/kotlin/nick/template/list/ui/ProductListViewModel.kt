package nick.template.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest
import nick.template.list.data.throttleFirst
import nick.template.list.data.ProductListRepository
import nick.template.list.models.FetchProductsEvent
import nick.template.list.models.FetchProductsResult
import nick.template.list.models.ProductClickedEvent
import nick.template.list.models.ProductListEffect
import nick.template.list.models.ProductListEvent
import nick.template.list.models.ProductListResult
import nick.template.list.models.ProductListState
import nick.template.list.models.ProductsPacket
import nick.template.list.models.StartLoadingResult
import nick.template.logging.Logger
import nick.template.mvi.MviViewModel

internal class ProductListViewModel @Inject constructor(
    private val onProductClicked: OnProductClicked,
    private val repository: ProductListRepository,
    private val logger: Logger,
) : MviViewModel<ProductListEvent, ProductListResult, ProductListState, ProductListEffect>(
    ProductListState()
) {
    override fun onSubscription() {
        processEvent(FetchProductsEvent)
    }

    override fun Flow<ProductListEvent>.toResults(): Flow<ProductListResult> {
        return merge(
            filterIsInstance<FetchProductsEvent>().toFetchProductsResults(),
            filterIsInstance<ProductClickedEvent>().toProductClickedResults()
        )
    }

    override fun ProductListResult.reduce(state: ProductListState): ProductListState {
        return when (this) {
            is StartLoadingResult -> state.copy(isLoading = true)
            is FetchProductsResult -> state.copy(
                isLoading = isCached,
                products = products,
                error = error
            )
            else -> state
        }
    }

    private fun Flow<FetchProductsEvent>.toFetchProductsResults(): Flow<ProductListResult> {
        return flatMapLatest { repository.products() }
            .mapLatest<ProductsPacket, ProductListResult> { packet ->
                FetchProductsResult(
                    isCached = packet is ProductsPacket.Cached,
                    products = packet.products,
                    error = (packet as? ProductsPacket.Error)?.throwable
                )
            }
            .onStart { emit(StartLoadingResult) }
    }

    private fun Flow<ProductClickedEvent>.toProductClickedResults(): Flow<ProductListResult> {
        return throttleFirst() // Workaround for androidx Navigation fast clicking
            .onEach { event -> logger.d("Clicked product with id: ${event.id}") }
            .transformLatest { event -> onProductClicked.onProductClicked(event.id) }
    }

    class Factory @Inject constructor(
        private val onProductClicked: OnProductClicked,
        private val repository: ProductListRepository,
        private val logger: Logger,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            @Suppress("unchecked_cast")
            return ProductListViewModel(onProductClicked, repository, logger) as T
        }
    }
}
