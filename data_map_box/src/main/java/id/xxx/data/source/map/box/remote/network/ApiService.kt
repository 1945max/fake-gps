package id.xxx.data.source.map.box.remote.network

import id.xxx.data.source.map.box.BuildConfig
import id.xxx.data.source.map.box.remote.response.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Suppress("SpellCheckingInspection")
    @GET("/geocoding/v5/mapbox.places/{query}.json")
    suspend fun fetchPlaces(
        @Path("query") query: String,
        @Query("access_token") accessToken: String? = BuildConfig.API_KEY,
        @Query("autocomplete") autoComplete: Boolean? = true
    ): PlacesResponse
}