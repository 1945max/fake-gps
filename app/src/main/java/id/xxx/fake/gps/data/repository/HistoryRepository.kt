package id.xxx.fake.gps.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.google.firebase.firestore.DocumentChange
import id.xxx.data.source.firebase.firestore.history.local.LocalDataSource
import id.xxx.data.source.firebase.firestore.history.model.networkBoundResourceFireStore
import id.xxx.data.source.firebase.firestore.history.remote.RemoteDataSource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository
import id.xxx.fake.gps.utils.DataMapper.historyEntityToHistoryModel
import id.xxx.fake.gps.utils.DataMapper.historyModelToHistoryEntity
import id.xxx.fake.gps.utils.DataMapper.historyModelToHistoryFireStoreModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

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
            Pager(config = PagingConfig(pageSize = 30, enablePlaceholders = false)) {
                local.getPagingSource()
            }.flow.map { it.map { historyEntity -> historyEntityToHistoryModel.map(historyEntity) } }
        },
        createCall = { remote.addSnapshotListener() },
        saveFetchResult = {
            val data = it.document.toObject(HistoryModel::class.java)
            when (it.type) {
                DocumentChange.Type.MODIFIED -> local.update(historyModelToHistoryEntity.map(data))
                DocumentChange.Type.REMOVED -> local.delete(historyModelToHistoryEntity.map(data))
                DocumentChange.Type.ADDED -> data.apply {
                    if (!id.isBlank()) local.insert(historyModelToHistoryEntity.map(this))
                }
            }
        }
    )
}