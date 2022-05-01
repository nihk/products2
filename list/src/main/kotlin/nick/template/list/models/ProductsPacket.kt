package nick.template.list.models

sealed class ProductsPacket {
    abstract val products: List<ProductListItem>?

    data class Cached(override val products: List<ProductListItem>) : ProductsPacket()
    data class Fresh(override val products: List<ProductListItem>) : ProductsPacket()
    data class Error(
        val throwable: Throwable,
        override val products: List<ProductListItem>? = null
    ) : ProductsPacket()
}
