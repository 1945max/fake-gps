package id.xxx.data.source.firebase.auth.local.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.data.source.firebase.auth.local.dao.AuthDao
import id.xxx.data.source.firebase.auth.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(app: Application): AppDatabase {
            instance ?: synchronized(AppDatabase::class.java) {
                instance = Room.databaseBuilder(app, AppDatabase::class.java, this::class.java.name)
                    .fallbackToDestructiveMigration()
//                    .openHelperFactory(Encryption.passphrase)
                    .build()
            }
            return instance as AppDatabase
        }
    }
}