package nick.template.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: String,
    val type: String
)
