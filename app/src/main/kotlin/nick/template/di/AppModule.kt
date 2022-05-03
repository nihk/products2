package nick.template.di

import android.app.Application
import androidx.room.Room
import coil.ImageLoader
import coil.imageLoader
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nick.template.data.AppDatabase
import nick.template.local.dao.ProductsDao
import nick.template.logging.AndroidLogger
import nick.template.logging.Logger

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {
        @Provides
        fun appResources(application: Application) = application.resources

        @Provides
        fun appDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.name)
                .build()
        }

        @Provides
        fun productsDao(appDatabase: AppDatabase): ProductsDao = appDatabase.productsDao()

        @Provides
        fun imageLoader(application: Application): ImageLoader = application.imageLoader
    }

    @Binds
    fun logger(logger: AndroidLogger): Logger
}
