package nick.template.list.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestScope
import nick.template.list.data.ProductListRepository
import nick.template.list.models.FetchProductsEvent
import nick.template.list.models.ProductClickedEvent
import nick.template.list.models.ProductListEffect
import nick.template.list.models.ProductListState
import nick.template.list.models.ProductsPacket
import nick.template.list.shared.FakeOnProductClicked
import nick.template.list.shared.NoOpLogger
import nick.template.list.shared.products
import nick.template.test.CoroutinesTestRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ProductListViewModelTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @Test
    fun `should emit product states when products are fetched`() = viewModel(
        packets = flowOf(ProductsPacket.Fresh(products))
    ) {
        fetchProducts()

        assertStates(
            ProductListState(),
            ProductListState().copy(isLoading = true),
            ProductListState().copy(products = products),
        )
    }

    @Test
    fun `should handle cached when one is emitted while fetching`() = viewModel(
        packets = flowOf(ProductsPacket.Cached(products))
    ) {
        fetchProducts()

        assertStates(
            ProductListState(),
            ProductListState().copy(isLoading = true),
            ProductListState().copy(isLoading = true, products = products),
        )
    }

    @Test
    fun `should handle error when one is thrown while fetching`() {
        val throwable: Throwable = RuntimeException("Uh oh!")
        viewModel(
            packets = flowOf(ProductsPacket.Error(throwable = throwable))
        ) {
            fetchProducts()

            assertStates(
                ProductListState(),
                ProductListState().copy(isLoading = true),
                ProductListState().copy(error = throwable),
            )
        }
    }

    @Test
    fun `should handle product click when product is clicked`() = viewModel {
        clickProduct(id = "1234")

        assertProductClicked(id = "1234")
    }

    private fun viewModel(
        packets: Flow<ProductsPacket> = emptyFlow(),
        block: ProductListViewModelRobot.() -> Unit
    ) {
        ProductListViewModelRobot(
            packets = packets,
            scope = TestScope(rule.dispatcher)
        )
            .block()
    }

    internal class ProductListViewModelRobot(
        packets: Flow<ProductsPacket>,
        scope: CoroutineScope
    ) {
        private val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsPacket> {
                return packets
            }
        }
        private val onProductClicked = FakeOnProductClicked()
        private val viewModel = ProductListViewModel(
            onProductClicked = onProductClicked,
            repository = repository,
            logger = NoOpLogger(),
        )
        private val collectedStates = mutableListOf<ProductListState>()
        private val collectedEffects = mutableListOf<ProductListEffect>()

        init {
            merge(
                viewModel.states.onEach(collectedStates::add),
                viewModel.effects.onEach(collectedEffects::add)
            ).launchIn(scope)
        }

        fun fetchProducts() {
            viewModel.processEvent(FetchProductsEvent)
        }

        fun clickProduct(id: String) {
            viewModel.processEvent(ProductClickedEvent(id = id))
        }

        fun assertStates(vararg expectation: ProductListState) {
            assertEquals(expectation.size, collectedStates.size)
            expectation.zip(collectedStates) { expected, collected ->
                assertEquals(expected, collected)
            }
        }

        fun assertEffects(vararg expectation: ProductListEffect) {
            assertEquals(expectation.size, collectedEffects.size)
            expectation.zip(collectedEffects) { expected, collected ->
                assertEquals(expected, collected)
            }
        }

        fun assertProductClicked(id: String) {
            assertEquals(id, onProductClicked.clickedId)
        }
    }
}
