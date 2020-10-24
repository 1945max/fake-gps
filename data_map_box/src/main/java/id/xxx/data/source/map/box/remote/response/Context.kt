package id.xxx.data.source.map.box.remote.response

import com.google.gson.annotations.SerializedName

data class Context(
	@SerializedName("id") val id: String,
	@SerializedName("wikidata") val wiki_data: String,
	@SerializedName("text") val text: String
)