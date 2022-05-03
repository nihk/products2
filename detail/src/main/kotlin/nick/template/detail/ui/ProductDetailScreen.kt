package nick.template.detail.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.compose.AsyncImage
import javax.inject.Inject
import nick.template.detail.R
import nick.template.navigation.Screen

internal class ProductDetailScreen @Inject constructor(
    private val viewModelFactory: ProductDetailViewModel.Factory,
    private val imageLoader: ImageLoader
) : Screen {
    override val name: String = Name

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ArgId) { type = NavType.StringType }
    )

    @Composable
    override fun Content(arguments: Bundle?) {
        val id = requireNotNull(arguments?.getString(ArgId))
        val viewModel = viewModel<ProductDetailViewModel>(
            factory = viewModelFactory.create(id, LocalSavedStateRegistryOwner.current)
        )
        val state by viewModel.states.collectAsState()
        val product = state.product

        if (product != null) {
            // fixme: landscape is getting cut off and verticalScroll breaks size of image for some reason
            Column {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    imageLoader = imageLoader,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.body1
                    )
                }
                Text(
                    text =  stringResource(
                        id = R.string.product_type_formatter,
                        product.type
                    ),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Text(
                    text =  stringResource(
                        id = R.string.product_code_formatter,
                        product.id
                    ),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    companion object {
        const val ArgId = "id"
        const val Name = "product-detail/{$ArgId}"
    }
}

fun productDetailRoute(id: String): String {
    return ProductDetailScreen.Name.replace("{${ProductDetailScreen.ArgId}}", id)
}
