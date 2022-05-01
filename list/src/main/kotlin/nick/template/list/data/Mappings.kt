package nick.template.list.data

import nick.template.list.models.LocalProduct
import nick.template.list.models.ProductListItem
import nick.template.list.models.RemoteProduct

internal fun List<RemoteProduct>.toLocalProducts(): List<LocalProduct> {
    return map { remoteProduct ->
        with(remoteProduct) {
            LocalProduct(
                id = code,
                imageUrl = image,
                name = name,
                price = price,
                type = type
            )
        }
    }
}

internal fun List<LocalProduct>.toProductItems(): List<ProductListItem> {
    return map { product ->
        ProductListItem(
            id = product.id,
            imageUrl = product.imageUrl,
            name = product.name,
            price = product.price
        )
    }
}
