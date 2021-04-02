package id.xxx.fake.gps.history.data.source.local

import id.xxx.fake.gps.history.data.source.local.dao.HistoryDao
import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity

class LocalDataSource(private val dao: HistoryDao) {
    suspend fun insert(entity: HistoryEntity) = dao.insert(entity)

    suspend fun clear() = dao.clear()

    suspend fun delete(id: Long) = dao.delete(id)

    fun getPaging() = dao.getPaging()
}