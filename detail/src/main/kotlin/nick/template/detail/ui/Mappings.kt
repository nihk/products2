package nick.template.detail.ui

import nick.template.detail.models.ProductDetailItem
import nick.template.local.models.Product

internal fun Product.toProductDetailItem(): ProductDetailItem {
    return ProductDetailItem(
        id = id,
        imageUrl = imageUrl,
        name = name,
        price = price,
        type = type
    )
}
