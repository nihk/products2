package nick.template.list.models

// Model class to be used by the product list feature. It is agnostic of any Room/Retrofit APIs/models
internal data class ProductListItem(
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: String
)
