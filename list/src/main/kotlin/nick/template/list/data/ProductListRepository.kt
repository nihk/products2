package nick.template.list.data

import kotlinx.coroutines.flow.Flow
import nick.template.list.models.ProductsPacket

interface ProductListRepository {
    fun products(): Flow<ProductsPacket>
}
