package id.xxx.fake.gps.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.fake.gps.history.data.local.dao.IHistoryDao
import id.xxx.fake.gps.history.data.local.entity.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(), IHistoryDao {

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase {
            instance ?: synchronized(AppDatabase::class.java) {
                instance = Room.databaseBuilder(app, AppDatabase::class.java, "id.xxx.fake.gps")
                    .fallbackToDestructiveMigration()
//                    .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("xxx.base.data".toCharArray())))
                    .build()
            }
            return instance as AppDatabase
        }
    }
}