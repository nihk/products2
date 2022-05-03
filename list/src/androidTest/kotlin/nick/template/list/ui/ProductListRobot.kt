package nick.template.list.ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import nick.template.list.data.ProductListRepository
import nick.template.list.models.ProductsPacket
import nick.template.list.shared.FakeOnProductClicked
import nick.template.list.shared.NoOpLogger
import nick.template.test.FakeImageLoader
import org.junit.Assert.assertEquals

internal fun productList(block: ProductListRobot.() -> Unit) {
    ProductListRobot().block()
}

internal class ProductListRobot {
    private val packets = MutableStateFlow<ProductsPacket?>(null)
    private val onProductClicked = FakeOnProductClicked()

    fun setPacket(packet: ProductsPacket) {
        packets.value = packet
    }

    fun ComposeContentTestRule.clickProduct(name: String) {
        onNodeWithText(name).performClick()
    }

    fun ComposeContentTestRule.renderScreen() {
        val viewModelFactory = ProductListViewModel.Factory(
            onProductClicked = onProductClicked,
            repository = object : ProductListRepository {
                override fun products(): Flow<ProductsPacket> = packets.filterNotNull()
            },
            logger = NoOpLogger()
        )

        val screen = ProductListScreen(
            viewModelFactory = viewModelFactory,
            imageLoader = FakeImageLoader()
        )

        setContent {
            screen.Content(arguments = null)
        }
    }

    fun assertProductClicked(id: String) {
        assertEquals(id, onProductClicked.clickedId)
    }
}
