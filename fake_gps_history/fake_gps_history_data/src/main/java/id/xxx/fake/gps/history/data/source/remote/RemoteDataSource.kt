package id.xxx.fake.gps.history.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import id.xxx.base.domain.model.ApiResponse
import id.xxx.base.domain.model.getApiResponseAsFlow
import id.xxx.fake.gps.history.data.source.remote.response.HistoryFireStoreResponse
import id.xxx.fake.gps.history.data.source.remote.response.toHistoryFireStoreResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.toImmutableList

@ExperimentalCoroutinesApi
class RemoteDataSource(private val fireStore: FirebaseFirestore) {

    private fun collectionReference(userId: String) = fireStore
        .collection("fake-gps")
        .document("history")
        .collection(userId)

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
    }.catch { emit(ApiResponse.Error(error = it)) }

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