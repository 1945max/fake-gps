package id.xxx.fake.gps.history.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import id.xxx.fake.gps.history.data.source.remote.response.HistoryFireStoreResponse
import id.xxx.fake.gps.history.data.source.remote.response.toHistoryFireStoreResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class RemoteDataSource(private val fireStore: FirebaseFirestore) {

    private fun collectionReference(uid: String) = fireStore
        .collection("fake-gps")
        .document("history")
        .collection(uid)

    suspend fun streamData(uid: String) = callbackFlow {
        val listenerRegistration = collectionReference(uid)
            .addSnapshotListener { value, _ ->
                val data = value?.documentChanges
                offer(data?.map {
                    it.document.toHistoryFireStoreResponse()
                } ?: listOf<HistoryFireStoreResponse>())
            }; awaitClose { listenerRegistration.remove() }
    }

    fun insert(model: HistoryFireStoreResponse) {
        collectionReference(model.userId).add(model).addOnCompleteListener {
            it.result?.apply { update("id", id) }
        }
    }

    fun update(model: HistoryFireStoreResponse) {
        val data = mapOf(
            HistoryFireStoreResponse.ADDRESS to model.address,
            HistoryFireStoreResponse.LATITUDE to model.latitude,
            HistoryFireStoreResponse.LONGITUDE to model.longitude
        )
        collectionReference(model.userId).document(model.id).update(data)
    }

    fun delete(userId: String, historyId: String) {
        collectionReference(userId).document(historyId).delete()
    }
}