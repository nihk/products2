package nick.template.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import nick.template.list.ui.productListName

@Module
@InstallIn(ActivityComponent::class)
interface MainModule {
    @Provides
    @StartDestination
    fun startDestination() = productListName()
}
