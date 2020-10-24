package id.xxx.data.source.map.box.local

import id.xxx.data.source.map.box.local.dao.PlacesDao
import id.xxx.data.source.map.box.local.entity.PlacesEntity

class LocalDataSource constructor(private val dao: PlacesDao) {

    fun getPaging(value: String) = dao.getPaged(value)

    fun getByID(id: Int) = dao.getByID(id)

    fun select(value: String) = dao.selectInColumnNameOrAddress(value)

    fun select(latitude: Double, longitude: Double) =
        dao.selectInColumnNameOrAddress(latitude, longitude)

    fun search(value: String) = dao.likeColumnNameOrAddress(value)

    suspend fun insert(vararg entity: PlacesEntity) = dao.insert(*entity)

    fun insert(entity: PlacesEntity) = dao.insert(entity)
}