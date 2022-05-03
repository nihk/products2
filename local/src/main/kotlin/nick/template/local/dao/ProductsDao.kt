package nick.template.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nick.template.local.models.Product

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<Product>): List<Long>

    @Query(
        """
            DELETE
            FROM products
        """
    )
    suspend fun nuke(): Int

    @Query(
        """
            SELECT *
            FROM products
        """
    )
    fun queryAll(): Flow<List<Product>>

    @Query(
        """
            SELECT *
            FROM products
            WHERE id = :id
        """
    )
    fun queryById(id: String): Flow<Product?>

    @Transaction
    suspend fun nukeThenInsert(products: List<Product>): List<Long> {
        nuke()
        return insert(products)
    }
}
