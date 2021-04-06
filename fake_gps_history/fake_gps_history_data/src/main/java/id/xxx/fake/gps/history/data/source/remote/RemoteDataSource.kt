package id.xxx.fake.gps.history.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.xxx.base.domain.model.ApiResponse
import id.xxx.base.domain.model.getApiResponseAsFlow
import id.xxx.fake.gps.history.data.source.remote.response.HistoryFireStoreResponse
import id.xxx.fake.gps.history.data.source.remote.response.toHistoryFireStoreResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteDataSource(private val fireStore: FirebaseFirestore) {

    private fun collectionReference(userId: String) = fireStore
        .collection("fake-gps")
        .document("history")
        .collection(userId)

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun streamData(userId: String) = callbackFlow {
        val listenerRegistration = collectionReference(userId)
            .addSnapshotListener { value, _ ->
                val data = value?.documentChanges
                val apiResponse =
                    if (!data.isNullOrEmpty()) {
                        ApiResponse.Success(data.map { it.document.toHistoryFireStoreResponse() })
                    } else {
                        ApiResponse.Empty
                    }
                offer(apiResponse)
            }; awaitClose { listenerRegistration.remove() }
    }

    suspend fun getPage(
        userId: String,
        startAfterByDate: Long? = null,
        limit: Long = 10
    ) = getApiResponseAsFlow(
        blockFetch = {
            val col = collectionReference(userId)
                .orderBy(HistoryFireStoreResponse.DATE, Query.Direction.DESCENDING)
            if (startAfterByDate != null) {
                col.startAfter(startAfterByDate).limit(limit)
            } else {
                col.limit(limit)
            }.get().await().map { it.toHistoryFireStoreResponse() }
        },
        blockOnFetch = { it.isNotEmpty() }
    )

    fun insert(data: HistoryFireStoreResponse) {
        collectionReference(data.userId).add(data).addOnCompleteListener {
            it.result?.apply { update("id", id) }
        }
    }

    fun update(data: HistoryFireStoreResponse) {
        val dataMap = mapOf(
            HistoryFireStoreResponse.ADDRESS to data.address,
            HistoryFireStoreResponse.LATITUDE to data.latitude,
            HistoryFireStoreResponse.LONGITUDE to data.longitude
        )
        collectionReference(data.userId).document(data.id).update(dataMap)
    }

    fun delete(userId: String, historyId: String) {
        collectionReference(userId).document(historyId).delete()
    }
}