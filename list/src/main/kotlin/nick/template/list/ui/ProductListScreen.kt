package nick.template.list.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import javax.inject.Inject
import nick.template.list.R
import nick.template.list.models.FetchProductsEvent
import nick.template.list.models.ProductClickedEvent
import nick.template.list.models.ProductListItem
import nick.template.navigation.Screen

// fixme: throttle clicks
internal class ProductListScreen @Inject constructor(
    private val imageLoader: ImageLoader
) : Screen {
    override val name: String = Name

    @Composable
    override fun Content(arguments: Bundle?) {
        val viewModel = hiltViewModel<ProductListViewModel>()
        val state by viewModel.states.collectAsState()
        val products = state.products

        if (!products.isNullOrEmpty()) {
            ProductsList(viewModel, products)
        }

        if (state.isLoading) {
            Loading()
        }

        if (state.error != null && !products.isNullOrEmpty()) {
            Error(viewModel)
        }
    }

    @Composable
    private fun ProductsList(viewModel: ProductListViewModel, products: List<ProductListItem>) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = products,
                key = ProductListItem::id
            ) { product ->
                @OptIn(ExperimentalMaterialApi::class)
                Card(
                    elevation = 2.dp,
                    onClick = { viewModel.processEvent(ProductClickedEvent(product.id)) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.h6,
                            )
                            Text(
                                text = product.price,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Box {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                imageLoader = imageLoader,
                                modifier = Modifier
                                    .height(96.dp)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Loading() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    private fun Error(viewModel: ProductListViewModel) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = stringResource(id = R.string.error_message))
                Button(onClick = { viewModel.processEvent(FetchProductsEvent) }) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
        }
    }

    companion object {
        const val Name = "product-list"
    }
}

fun productListName() = ProductListScreen.Name
