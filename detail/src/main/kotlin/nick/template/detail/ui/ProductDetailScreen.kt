package nick.template.detail.ui

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import javax.inject.Inject
import nick.template.navigation.Screen

class ProductDetailScreen @Inject constructor() : Screen {
    override val name: String = Name

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ArgId) { type = NavType.StringType }
    )

    @Composable
    override fun Content(arguments: Bundle?) {
        val id = requireNotNull(arguments?.getString(ArgId))
        Box(
            modifier = Modifier
                .background(color = Color.Green)
                .fillMaxSize()
        )
    }

    companion object {
        const val Name = "product-detail"
        private const val ArgId = "id"

        fun route(id: String): String {
            return "${Name}/$id"
        }
    }
}
