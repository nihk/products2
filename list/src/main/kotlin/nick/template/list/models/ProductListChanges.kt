package nick.template.list.models

internal sealed class ProductListEvent
internal object FetchProductsEvent : ProductListEvent()
internal data class ProductClickedEvent(val id: String) : ProductListEvent()

internal sealed class ProductListResult
internal object StartLoadingResult : ProductListResult()
internal data class FetchProductsResult(
    val isCached: Boolean,
    val products: List<ProductListItem>?,
    val error: Throwable?
) : ProductListResult()

internal sealed class ProductListEffect

internal data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<ProductListItem>? = null,
    val error: Throwable? = null
)
