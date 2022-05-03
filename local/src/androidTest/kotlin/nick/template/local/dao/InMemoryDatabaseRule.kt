package nick.template.local.dao

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class InMemoryDatabaseRule<T>(
    private val clazz: Class<T>
) : TestWatcher()
        where T : RoomDatabase {

    private var database: T? = null
    fun database(): T = requireNotNull(database)

    override fun starting(description: Description) {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            clazz
        ).allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description?) {
        database?.close()
    }
}
