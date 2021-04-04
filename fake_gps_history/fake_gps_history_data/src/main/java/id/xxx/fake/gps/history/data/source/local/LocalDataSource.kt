package id.xxx.fake.gps.history.data.source.local

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import id.xxx.fake.gps.history.data.source.local.dao.HistoryDao
import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity

class LocalDataSource(private val dao: HistoryDao) {
    suspend fun insert(entity: HistoryEntity) = dao.insert(entity)

    suspend fun insert(data: List<HistoryEntity>) = dao.insert(data).isNotEmpty()

    suspend fun insert(vararg data: HistoryEntity) = dao.insert(*data).isNotEmpty()

    suspend fun clear() = dao.clear()

    suspend fun delete(id: Long) = dao.delete(id)

    suspend fun getLastDate() = dao.getLastDate()

    fun getPaging() = dao.getPaging()

    fun getList() = dao.getList()
}