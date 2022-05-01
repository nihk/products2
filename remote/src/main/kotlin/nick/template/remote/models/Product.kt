package nick.template.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val code: String,
    val image: String,
    val name: String,
    val price: String,
    val type: String
)
