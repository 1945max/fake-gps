package id.xxx.fake.gps.utils

import android.content.Context
import android.location.Geocoder
import androidx.annotation.WorkerThread
import java.io.IOException
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
    private lateinit var apiResponse: Result<android.location.Address>

    @WorkerThread
    fun getData(value: String): Result<android.location.Address> {
        try {
            if (isLatLong(value)) {
                val stringTokenizer = StringTokenizer(value.replace("\\s".toRegex(), ""), ",")
                val data = doubleArrayOf(
                    stringTokenizer.nextToken().toDouble(),
                    stringTokenizer.nextToken().toDouble()
                )
                val latitude = data[0]
                val longitude = data[1]
                val listAddress = geoCoder.getFromLocation(latitude, longitude, 1)
                apiResponse =
                    if (listAddress.isNotEmpty()) Result.Success(listAddress[0]) else Result.Empty()
            } else {
                val listAddress = geoCoder.getFromLocationName(value, 1)
                apiResponse =
                    if (listAddress.isNotEmpty()) Result.Success(listAddress[0]) else Result.Empty()
            }
        } catch (e: IOException) {
            apiResponse = Result.Error(e.message)
        }
        return apiResponse
    }
}

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Empty<out T>(val data: T? = null) : Result<T>()
    data class Error<out T>(val message: String? = null) : Result<T>()
}