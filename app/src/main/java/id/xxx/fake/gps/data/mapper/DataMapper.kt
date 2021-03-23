package id.xxx.fake.gps.data.mapper

import id.xxx.fake.gps.data.local.history.HistoryEntity
import id.xxx.fake.gps.domain.history.model.HistoryModel

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
}