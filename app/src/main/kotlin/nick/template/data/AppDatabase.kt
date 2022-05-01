package nick.template.data

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.template.local.dao.ProductsDaoProvider
import nick.template.local.models.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class AppDatabase :
    RoomDatabase(),
    ProductsDaoProvider {
    companion object {
        const val name = "products.db"
    }
}
