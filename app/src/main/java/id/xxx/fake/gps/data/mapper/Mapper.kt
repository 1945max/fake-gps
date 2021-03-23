package id.xxx.fake.gps.data.mapper

import android.location.Address
import id.xxx.fake.gps.domain.history.model.HistoryModel

fun Address.toHistoryModel() = HistoryModel(
    address = getAddressLine(0),
    latitude = latitude,
    longitude = longitude
)