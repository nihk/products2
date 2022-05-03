package nick.template.detail.models

internal sealed class ProductDetailEvent
internal data class LoadProductEvent(val id: String) : ProductDetailEvent()

internal sealed class ProductDetailResult
internal data class LoadProductResult(val product: ProductDetailItem?) : ProductDetailResult()

internal data class ProductDetailState(
    val product: ProductDetailItem? = null
)
