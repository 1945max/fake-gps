package id.xxx.fake.gps.mapper

import id.xxx.fake.gps.history.domain.model.HistoryModel
import id.xxx.map.box.search.domain.model.AddressModel

fun AddressModel.toHistoryModel() = HistoryModel(
    address = address,
    latitude = latitude,
    longitude = longitude
)