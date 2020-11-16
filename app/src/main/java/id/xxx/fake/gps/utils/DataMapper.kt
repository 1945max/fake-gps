package id.xxx.fake.gps.utils

import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity
import id.xxx.data.source.firebase.firestore.history.model.HistoryFireStoreModel
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.response.Features
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.search.model.SearchModel

object DataMapper {
    val searchModelToHistoryModel = object : IMapper<SearchModel, HistoryModel> {
        override fun map(input: SearchModel) = HistoryModel(
            id = "",
            address = input.address,
            longitude = input.longitude,
            latitude = input.latitude,
        )
    }

    val toListSearchEntity = object : IMapperList<Features, PlacesEntity> {
        override fun map(input: List<Features>): List<PlacesEntity> = input.map {
            PlacesEntity(
                name = it.text,
                address = it.place_name,
                longitude = it.center[0],
                latitude = it.center[1]
            )
        }
    }

    val toSearchModel = object : IMapper<PlacesEntity, SearchModel> {
        override fun map(input: PlacesEntity) = SearchModel(
            id = input.id!!,
            name = input.name,
            date = input.date,
            longitude = input.longitude,
            latitude = input.latitude,
            address = input.address
        )
    }

    val historyFireStoreModelToHistoryEntity = object : IMapper<HistoryFireStoreModel, HistoryEntity> {
        override fun map(input: HistoryFireStoreModel) = HistoryEntity(
            id = input.id,
            address = input.address,
            latitude = input.latitude,
            longitude = input.longitude,
            date = input.date
        )
    }

    val historyModelToHistoryFireStoreModel = object : IMapper<HistoryModel, HistoryFireStoreModel> {
        override fun map(input: HistoryModel) = HistoryFireStoreModel(
            id = input.id,
            address = input.address,
            latitude = input.latitude,
            longitude = input.longitude,
            date = input.date
        )
    }

    val historyEntityToHistoryModel = object : IMapper<HistoryEntity, HistoryModel> {
        override fun map(input: HistoryEntity) = HistoryModel(
            id = input.id,
            address = input.address,
            latitude = input.latitude,
            longitude = input.longitude,
            date = input.date
        )
    }
}