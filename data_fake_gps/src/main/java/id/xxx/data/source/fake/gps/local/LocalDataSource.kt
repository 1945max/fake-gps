package id.xxx.data.source.fake.gps.local

import id.xxx.data.source.fake.gps.local.dao.HistoryDao
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity

class LocalDataSource(private val historyDao: HistoryDao) {

    fun getPagingSource() = historyDao.getPagingSource()

    suspend fun insert(historyEntity: HistoryEntity) = historyDao.insert(historyEntity)

    suspend fun delete(entity: HistoryEntity) = historyDao.delete(entity)
}