package id.xxx.fake.gps.utils

import android.location.Address
import id.xxx.data.source.fake.gps.utils.IMapper
import id.xxx.data.source.fake.gps.utils.IMapperList
import id.xxx.data.source.fake.gps.utils.IMapperNullable
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.response.Features

object DataMapper {
    val searchModelToHistoryModel = object : IMapper<SearchModel, HistoryModel> {
        override fun map(input: SearchModel) = HistoryModel(
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

    val toSearchModelNullable = object : IMapperNullable<PlacesEntity, SearchModel> {
        override fun map(input: PlacesEntity?) = input?.let {
            SearchModel(
                id = it.id!!,
                name = it.name,
                date = it.date,
                longitude = it.longitude,
                latitude = it.latitude,
                address = it.address
            )
        }
    }

    val toHistoryModel = object : IMapper<HistoryEntity, HistoryModel> {
        override fun map(input: HistoryEntity) = HistoryModel(
            id = input.id,
            address = input.address,
            longitude = input.longitude,
            latitude = input.latitude,
            date = input.date
        )
    }

    val toHistoryEntity = object : IMapper<HistoryModel, HistoryEntity> {
        override fun map(input: HistoryModel) = HistoryEntity(
            id = input.id,
            address = input.address,
            latitude = input.latitude,
            longitude = input.longitude,
            date = input.date
        )
    }

    fun toSearchEntity(name: String) = object : IMapper<Address, PlacesEntity> {
        override fun map(input: Address): PlacesEntity = PlacesEntity(
            name = name,
            address = input.getAddressLine(0),
            latitude = input.latitude,
            longitude = input.longitude
        )
    }
}