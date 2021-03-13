package id.xxx.fake.gps.data

import id.xxx.base.domain.mapper.IMapper
import id.xxx.fake.gps.data.local.history.HistoryEntity
import id.xxx.fake.test.domain.history.model.HistoryModel
import id.xxx.fake.test.domain.search.model.SearchModel

object DataMapper {
    val searchModelToHistoryModel = object : IMapper<SearchModel, HistoryModel> {
        override fun map(input: SearchModel) = HistoryModel(
            address = input.address,
            longitude = input.longitude,
            latitude = input.latitude,
        )
    }

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