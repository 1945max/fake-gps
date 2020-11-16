package id.xxx.data.source.firebase.firestore.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.data.source.firebase.firestore.history.local.dao.HistoryDao
import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase {
            instance ?: synchronized(AppDatabase::class.java) {
                instance = Room.databaseBuilder(app, AppDatabase::class.java, this::class.java.name)
                    .fallbackToDestructiveMigration()
//                    .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("xxx.base.data".toCharArray())))
                    .build()
            }
            return instance as AppDatabase
        }
    }
}