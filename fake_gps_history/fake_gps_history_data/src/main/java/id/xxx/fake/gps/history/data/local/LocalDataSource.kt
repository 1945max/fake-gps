package id.xxx.fake.gps.history.data.local

import id.xxx.fake.gps.history.data.local.dao.Dao
import id.xxx.fake.gps.history.data.local.entity.HistoryEntity

class LocalDataSource(private val dao: Dao) {
    suspend fun insert(entity: HistoryEntity) = dao.insert(entity)

    suspend fun clear() = dao.clear()

    suspend fun delete(id: Long) = dao.delete(id)

    fun getPaging() = dao.getPaging()
}