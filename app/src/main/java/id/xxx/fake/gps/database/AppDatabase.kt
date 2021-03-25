package id.xxx.fake.gps.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.xxx.auth.data.email.source.local.dao.IAuthDao
import id.xxx.auth.data.email.source.local.entity.UserEntity
import id.xxx.fake.gps.history.data.local.dao.IHistoryDao
import id.xxx.fake.gps.history.data.local.entity.HistoryEntity
import id.xxx.map.box.search.data.source.local.dao.ISearchDao
import id.xxx.map.box.search.data.source.local.entity.PlacesEntity
import org.koin.dsl.module

@Database(
    entities = [
        HistoryEntity::class,
        UserEntity::class,
        PlacesEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(), IHistoryDao, IAuthDao, ISearchDao {

    companion object {

        val module = module {
            single { getInstance(get()) }
            single { get<AppDatabase>().historyDao() }
            single { get<AppDatabase>().userDao() }
            single { get<AppDatabase>().placesDao() }
        }

        @Volatile
        private var instance: AppDatabase? = null

        private fun getInstance(app: Application): AppDatabase {
            instance ?: synchronized(AppDatabase::class.java) {
                instance = Room.databaseBuilder(app, AppDatabase::class.java, "id.xxx.fake.gps")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
//                    .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("xxx.base.data".toCharArray())))
                    .build()
            }
            return instance as AppDatabase
        }
    }
}