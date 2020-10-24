package id.xxx.data.source.map.box.remote.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    const val TIME_OUT: Long = 60

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    fun <T> service(
        baseUrl: String, apiServiceClass: Class<T>, okHttpClient: OkHttpClient = okHttpClient()
    ): T = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(apiServiceClass)
}