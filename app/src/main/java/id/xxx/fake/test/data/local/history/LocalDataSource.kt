package id.xxx.fake.test.data.local.history

class LocalDataSource(private val dao: Dao) {
    suspend fun insert(entity: HistoryEntity) = dao.insert(entity)

    suspend fun clear() = dao.clear()

    suspend fun delete(id: Long) = dao.delete(id)

    fun getPaging() = dao.getPaging()
}