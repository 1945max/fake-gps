package id.xxx.data.source.firebase.firestore.history.model

data class HistoryFireStoreModel(
    var id: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var date: Long = System.currentTimeMillis()
)