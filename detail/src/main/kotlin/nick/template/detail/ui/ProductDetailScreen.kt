package nick.template.detail.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
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

    }

    companion object {
        private const val ArgId = "id"
        const val Name = "product-detail/{$ArgId}"

        fun route(id: String): String {
            return Name.replace(ArgId, id)
        }
    }
}
