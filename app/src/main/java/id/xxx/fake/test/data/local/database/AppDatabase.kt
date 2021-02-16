package id.xxx.fake.test.data.local.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.fake.test.data.local.history.Dao
import id.xxx.fake.test.data.local.history.Entity

@Database(
    entities = [
        Entity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val historyDao: Dao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase {
            instance ?: synchronized(AppDatabase::class.java) {
//                instance = Room.databaseBuilder(app, AppDatabase::class.java, this::class.java.name)
                instance = Room.inMemoryDatabaseBuilder(app, AppDatabase::class.java)
                    .fallbackToDestructiveMigration()
//                    .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("xxx.base.data".toCharArray())))
                    .build()
            }
            return instance as AppDatabase
        }
    }
}