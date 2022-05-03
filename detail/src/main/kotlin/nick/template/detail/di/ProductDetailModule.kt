package nick.template.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet
import nick.template.detail.ui.ProductDetailScreen
import nick.template.navigation.Screen

@Module
@InstallIn(ViewModelComponent::class)
internal interface ProductDetailModule {
    @Binds
    @IntoSet
    fun productDetailScreen(screen: ProductDetailScreen): Screen
}
