package id.xxx.data.source.firebase.firestore.history.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.xxx.data.source.firebase.firestore.history.model.HistoryFireStoreModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class RemoteDataSource {
    private val fireStore = FirebaseFirestore.getInstance()

    private fun collectionReference() = fireStore
        .collection(FirebaseAuth.getInstance().currentUser!!.uid)
        .document("data")
        .collection("history")

    fun addSnapshotListener() = callbackFlow {
        val listenerRegistration = collectionReference().addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { offer(it) }
        }; awaitClose { listenerRegistration.remove() }
    }

    fun insert(model: HistoryFireStoreModel) {
        collectionReference().add(model).addOnCompleteListener {
            it.result?.apply { update("id", id) }
        }
    }

    fun delete(id: String) {
        collectionReference().document(id).delete()
    }
}