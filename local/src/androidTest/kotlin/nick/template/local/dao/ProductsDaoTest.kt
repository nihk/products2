package nick.template.local.dao

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import nick.template.local.models.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class ProductsDaoTest {
    @get:Rule
    val database = InMemoryDatabaseRule(FakeDatabase::class.java)
    private val dao get() = database.database().productsDao()

    @Test
    fun queryByIdGetsCorrectProduct() = runBlocking {
        val products = createProducts("1", "2")
        dao.insert(products)

        val product = dao.queryById("2").firstOrNull()

        assertNotNull(product)
        assertEquals(createProducts("2").first(), product)
    }

    @Test
    fun nukeThenInsertClearsPreviousRecords() = runBlocking {
        val products = createProducts("1", "2")
        dao.insert(products)
        val newProducts = createProducts("3", "4")

        dao.nukeThenInsert(newProducts)
        val product = dao.queryById("1").firstOrNull()

        assertNull(product)
    }

    private fun createProducts(vararg ids: String): List<Product> {
        return ids.map { id ->
            Product(
                id = id,
                imageUrl = "",
                type = "",
                price = "",
                name = ""
            )
        }
    }
}
