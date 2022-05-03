package nick.template.list.shared

import nick.template.list.ui.OnProductClicked

class FakeOnProductClicked : OnProductClicked {
    var clickedId: String? = null

    override suspend fun onProductClicked(id: String) {
        clickedId = id
    }
}
