package id.xxx.fake.gps.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentChange.Type
import id.xxx.data.source.firebase.firestore.history.local.LocalDataSource
import id.xxx.data.source.firebase.firestore.history.model.networkBoundResourceFireStore
import id.xxx.data.source.firebase.firestore.history.remote.RemoteDataSource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository
import id.xxx.fake.gps.utils.DataMapper.historyEntityToHistoryModel
import id.xxx.fake.gps.utils.DataMapper.historyModelToHistoryEntity
import id.xxx.fake.gps.utils.DataMapper.historyModelToHistoryFireStoreModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HistoryRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : IRepository<HistoryModel> {
    override fun insert(model: HistoryModel) =
        remote.insert(historyModelToHistoryFireStoreModel.map(model))

    override suspend fun clear() = local.clear()

    override fun delete(model: HistoryModel) = remote.delete(model.id)

    override fun getHistory() = networkBoundResourceFireStore(
        loadFromDB = {
            Pager(
                config = PagingConfig(pageSize = 30, enablePlaceholders = false)
            ) { local.getPagingSource() }.flow.map {
                it.map { historyEntity -> historyEntityToHistoryModel.map(historyEntity) }
            }
        },
        createCall = { remote.addSnapshotListener() },
        saveFetchResult = { statDocumentChange(it) }
    ).flowOn(Dispatchers.IO)

    private suspend fun statDocumentChange(it: List<DocumentChange>?) =
        it?.forEach { documentChange ->
            GlobalScope.launch {
                val data = documentChange.document.toObject(HistoryModel::class.java)
                when (documentChange.type) {
                    Type.MODIFIED -> local.update(historyModelToHistoryEntity.map(data))
                    Type.REMOVED -> local.delete(historyModelToHistoryEntity.map(data))
                    Type.ADDED -> data.apply {
                        if (!id.isBlank()) local.insert(historyModelToHistoryEntity.map(this))
                    }
                }
            }
        }
}