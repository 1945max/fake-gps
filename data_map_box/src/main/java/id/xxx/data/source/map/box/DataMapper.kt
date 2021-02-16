package id.xxx.data.source.map.box

import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.response.Features
import id.xxx.fake.test.domain.halper.IMapper
import id.xxx.fake.test.domain.search.model.SearchModel

object DataMapper {
    fun PlacesEntity.toSearchModel() = SearchModel(
        id = id!!,
        name = name,
        date = date,
        longitude = longitude,
        latitude = latitude,
        address = address
    )

    val toListSearchEntity = object : IMapper<List<Features>, List<PlacesEntity>> {
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
}