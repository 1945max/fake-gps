package id.xxx.fake.gps.data.helper

import android.content.Context
import android.location.Geocoder
import id.xxx.base.domain.model.getApiResponse
import java.lang.ref.WeakReference
import java.util.*

class Address private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: Address? = null

        fun getInstance(context: Context): Address {
            instance ?: synchronized(this) { instance = Address(context) }
            return instance as Address
        }

        fun isLatLong(value: String): Boolean =
            value.trim().matches(Regex("[-]?[0-9]*[.]?[0-9]*[,][-]?[0-9]*[.]?[0-9]*"))
    }

    private val geoCoder by lazy { Geocoder(WeakReference(context).get()) }

    suspend fun geoCoder(value: String) = getApiResponse(
        blockFetch = {
            if (isLatLong(value)) {
                val stringTokenizer = StringTokenizer(value.replace("\\s".toRegex(), ""), ",")
                val data = doubleArrayOf(
                    stringTokenizer.nextToken().toDouble(),
                    stringTokenizer.nextToken().toDouble()
                )
                val latitude = data[0]
                val longitude = data[1]
                val listAddress = geoCoder.getFromLocation(latitude, longitude, 1)
                if (listAddress.isNotEmpty()) listAddress[0] else null
            } else {
                val listAddress = geoCoder.getFromLocationName(value, 1)
                if (listAddress.isNotEmpty()) listAddress[0] else null
            }
        }
    )
}