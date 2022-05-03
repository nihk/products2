package nick.template.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.template.local.models.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class FakeDatabase : RoomDatabase(), ProductsDaoProvider
