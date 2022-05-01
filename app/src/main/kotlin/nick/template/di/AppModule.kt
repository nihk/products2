package nick.template.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nick.template.logging.AndroidLogger
import nick.template.logging.Logger

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {
        @Provides
        fun appResources(application: Application) = application.resources
    }

    @Binds
    fun logger(logger: AndroidLogger): Logger
}
