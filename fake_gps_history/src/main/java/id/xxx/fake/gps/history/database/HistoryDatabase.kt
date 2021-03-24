package id.xxx.fake.gps.history.database

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
abstract class HistoryDatabase : RoomDatabase(), IHistoryDao {

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null

        fun getInstance(app: Application): HistoryDatabase {
            instance ?: synchronized(HistoryDatabase::class.java) {
                instance = Room.databaseBuilder(app, HistoryDatabase::class.java, "id.xxx.fake.gps")
//                instance = Room.inMemoryDatabaseBuilder(app, HistoryDatabase::class.java)
                    .fallbackToDestructiveMigration()
//                    .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("xxx.base.data".toCharArray())))
                    .build()
            }
            return instance as HistoryDatabase
        }
    }
}