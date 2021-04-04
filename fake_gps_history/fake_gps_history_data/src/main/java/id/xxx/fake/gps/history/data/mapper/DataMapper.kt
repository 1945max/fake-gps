package id.xxx.fake.gps.history.data.mapper

import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity
import id.xxx.fake.gps.history.data.source.remote.response.HistoryFireStoreResponse
import id.xxx.fake.gps.history.domain.model.HistoryModel

object DataMapper {

    fun HistoryModel.toHistoryEntity() = HistoryEntity(
        address = address,
        latitude = latitude,
        longitude = longitude,
        date = date
    )

    fun HistoryEntity.toHistoryModel() = HistoryModel(
        id = id,
        address = address,
        latitude = latitude,
        longitude = longitude,
        date = date
    )

    fun HistoryFireStoreResponse.toHistoryEntity() = HistoryEntity(
        historyId = id,
        userId = userId,
        address = address,
        latitude = latitude,
        longitude = longitude,
        date = date
    )
}