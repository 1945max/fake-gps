package id.xxx.fake.gps.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import id.xxx.data.source.firebase.firestore.history.local.LocalDataSource
import id.xxx.data.source.firebase.firestore.history.model.Type
import id.xxx.data.source.firebase.firestore.history.model.networkBoundResourceFireStore
import id.xxx.data.source.firebase.firestore.history.remote.RemoteDataSource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository
import id.xxx.fake.gps.utils.DataMapper.historyEntityToHistoryModel
import id.xxx.fake.gps.utils.DataMapper.historyFireStoreModelToHistoryEntity
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

    override fun delete(model: HistoryModel) =
        remote.delete(historyModelToHistoryFireStoreModel.map(model))

    override fun getHistory() = networkBoundResourceFireStore(
        loadFromDB = {
            Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false)) {
                local.getPagingSource()
            }.flow.map { it.map { historyEntity -> historyEntityToHistoryModel.map(historyEntity) } }
        },
        createCall = { remote.addSnapshotListener() },
        saveFetchResult = {
            when (it) {
                is Type.Modified -> local.update(historyFireStoreModelToHistoryEntity.map(it.data))
                is Type.Removed -> local.delete(historyFireStoreModelToHistoryEntity.map(it.data))
                is Type.Added -> it.data.apply {
                    if (!id.isBlank()) local.insert(historyFireStoreModelToHistoryEntity.map(it.data))
                }
            }
        }
    )
}