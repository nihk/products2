package nick.template.list.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import nick.template.list.models.ProductsPacket
import nick.template.list.shared.products
import org.junit.Rule
import org.junit.Test

class ProductListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showLoading() = productList {
        composeTestRule.renderScreen()

        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun whenErrorStateWithNoProducts_showError() = productList {
        setPacket(
            ProductsPacket.Error(
                throwable = RuntimeException("Uh oh!"),
                products = null
            )
        )

        composeTestRule.renderScreen()

        composeTestRule.onNodeWithText("Something went terribly wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("retry").assertIsDisplayed()
    }

    @Test
    fun whenErrorStateWithProducts_doesNotShowError() = productList {
        setPacket(
            ProductsPacket.Error(
                throwable = RuntimeException("Uh oh!"),
                products = products
            )
        )

        composeTestRule.renderScreen()

        composeTestRule.onNodeWithText("Something went terribly wrong").assertDoesNotExist()
        composeTestRule.onNodeWithText("retry").assertDoesNotExist()
    }

    @Test
    fun whenHasProducts_showsProducts() = productList {
        setPacket(ProductsPacket.Fresh(products))

        composeTestRule.renderScreen()

        composeTestRule.onNodeWithText(products.first().name).assertIsDisplayed()
    }

    @Test
    fun whenProductClicked_forwardsIdToCallback() = productList {
        setPacket(ProductsPacket.Fresh(products))

        composeTestRule.renderScreen()
        composeTestRule.clickProduct(products.first().name)

        assertProductClicked(id = products.first().id)
    }
}
