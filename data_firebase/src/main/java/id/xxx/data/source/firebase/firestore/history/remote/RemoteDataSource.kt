package id.xxx.data.source.firebase.firestore.history.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import id.xxx.data.source.firebase.firestore.history.model.HistoryFireStoreModel
import id.xxx.data.source.firebase.firestore.history.model.Type
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
            value?.documentChanges?.forEach {
                val dataHistoryModel = it.document.toObject(HistoryFireStoreModel::class.java)
                val type = when (it.type) {
                    DocumentChange.Type.ADDED -> Type.Added(dataHistoryModel)
                    DocumentChange.Type.MODIFIED -> Type.Modified(dataHistoryModel)
                    DocumentChange.Type.REMOVED -> Type.Removed(dataHistoryModel)
                }; offer(type)
            }
        }; awaitClose { listenerRegistration.remove() }
    }

    fun insert(model: HistoryFireStoreModel) {
        collectionReference().add(model).addOnCompleteListener {
            it.result?.apply { update("id", id) }
        }
    }

    fun delete(model: HistoryFireStoreModel) {
        collectionReference().document(model.id).delete()
    }
}