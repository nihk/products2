package nick.template.navigation

import javax.inject.Inject
import nick.template.detail.ui.productDetailRoute
import nick.template.list.ui.OnProductClicked

class ToDetailOnProductClicked @Inject constructor(
    private val navigator: Navigator
) : OnProductClicked {
    override suspend fun onProductClicked(id: String) {
        navigator.process(
            Navigator.Command.Destination(productDetailRoute(id))
        )
    }
}
