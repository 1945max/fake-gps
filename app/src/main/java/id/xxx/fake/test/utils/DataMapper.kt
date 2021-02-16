package id.xxx.fake.test.utils

import id.xxx.fake.test.data.local.history.Entity
import id.xxx.fake.test.domain.halper.IMapper
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

    fun HistoryModel.toHistoryEntity() = Entity(
        address = address,
        latitude = latitude,
        longitude = longitude,
        date = date
    )

    fun Entity.toHistoryModel() = HistoryModel(
        id = id,
        address = address,
        latitude = latitude,
        longitude = longitude,
        date = date
    )
}