package id.xxx.data.source.firebase.firestore.history.local

import id.xxx.data.source.firebase.firestore.history.local.dao.HistoryDao
import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity

class LocalDataSource(private val dao: HistoryDao) {

    fun getPagingSource() = dao.getPagingSource()

    suspend fun insert(historyEntity: HistoryEntity) = dao.insert(historyEntity)

    suspend fun update(historyEntity: HistoryEntity) = dao.update(historyEntity)

    suspend fun delete(entity: HistoryEntity) = dao.delete(entity)

}