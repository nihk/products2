package nick.template.list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoSet
import nick.template.list.data.DefaultProductListRepository
import nick.template.list.data.ProductListRepository
import nick.template.list.ui.ProductListScreen
import nick.template.navigation.Screen

@Module
@InstallIn(ViewModelComponent::class)
interface ProductListModule {
    @Binds
    @IntoSet
    fun productListScreen(screen: ProductListScreen): Screen

    @Binds
    fun productListRepository(repository: DefaultProductListRepository): ProductListRepository
}