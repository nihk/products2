package nick.template.list.ui

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import javax.inject.Inject
import nick.template.navigation.Screen

class ProductListScreen @Inject constructor() : Screen {
    override val name: String = Name

    @Composable
    override fun Content(arguments: Bundle?) {
        Box(
            modifier = Modifier
                .background(color = Color.Red)
                .fillMaxSize()
        )
    }

    companion object {
        const val Name = "product-list"
    }
}
