package nick.template.remote.models

import com.squareup.moshi.JsonClass
import nick.template.remote.models.Product

@JsonClass(generateAdapter = true)
data class Cart(
    val entries: List<Product>
)
