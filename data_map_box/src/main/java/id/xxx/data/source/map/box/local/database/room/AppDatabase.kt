package id.xxx.data.source.map.box.local.database.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.data.source.map.box.local.dao.PlacesDao
import id.xxx.data.source.map.box.local.database.Encryption
import id.xxx.data.source.map.box.local.entity.PlacesEntity

@Database(
    entities = [
        PlacesEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract val searchHistoryDao: PlacesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase {
            instance ?: synchronized(AppDatabase::class.java) {
                instance = Room.databaseBuilder(app, AppDatabase::class.java, this::class.java.name)
                    .fallbackToDestructiveMigration()
                    .addCallback(CallbackDatabase(app))
                    .openHelperFactory(Encryption.passphrase)
                    .build()
            }
            return instance as AppDatabase
        }
    }
}