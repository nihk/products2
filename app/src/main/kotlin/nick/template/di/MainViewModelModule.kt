package nick.template.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.template.list.ui.OnProductClicked
import nick.template.navigation.ToDetailOnProductClicked

@Module
@InstallIn(ViewModelComponent::class)
interface MainViewModelModule {
    @Binds
    fun onProductClicked(clicked: ToDetailOnProductClicked): OnProductClicked
}
