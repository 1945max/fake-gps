package id.xxx.data.source.map.box.remote.response

import com.google.gson.annotations.SerializedName

data class Features(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("place_type") val place_type: List<String>,
    @SerializedName("relevance") val relevance: String,
    @SerializedName("properties") val properties: Properties,
    @SerializedName("text") val text: String,
    @SerializedName("place_name") val place_name: String,
    @SerializedName("bbox") val b_box: List<Double> = listOf(),
    @SerializedName("center") val center: List<Double> = listOf(),
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("context") val context: List<Context>
)