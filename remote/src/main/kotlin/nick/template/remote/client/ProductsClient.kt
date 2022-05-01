package nick.template.remote.client

import android.content.res.Resources
import javax.inject.Inject
import nick.template.logging.Logger
import nick.template.remote.R
import nick.template.remote.models.Product
import nick.template.remote.services.ProductsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface ProductsClient {
    suspend fun products(): List<Product>
}

class DefaultProductsClient @Inject constructor(
    appResources: Resources,
    logger: Logger
) : ProductsClient {
    private val service = Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor(logger::d))
                .build()
        )
        .baseUrl(appResources.getString(R.string.base_url))
        .build()
        .create(ProductsService::class.java)

    override suspend fun products(): List<Product> {
        return service.cart().entries
    }
}
