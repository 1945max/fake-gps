package id.xxx.fake.gps.history.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import id.xxx.fake.gps.history.data.source.remote.response.HistoryFireStoreResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoteDataSourceTest {

    private val fireStore = FirebaseFirestore.getInstance()

    @ExperimentalCoroutinesApi
    private val remoteDataSource = RemoteDataSource(fireStore)


    @Test
    fun streamDataTest() = runBlocking<Unit> {
        val data = remoteDataSource.streamData("user_id_1").firstOrNull()

        data?.forEach {
            println("data___ $it")
        }
    }

    @Test
    fun insertTest() {
        remoteDataSource.insert(
            HistoryFireStoreResponse(
                userId = "user_id_3",
                address = "address_3",
                longitude = 1.1,
                latitude = 1.1
            )
        )
        Thread.sleep(2000)
    }

    @Test
    fun updateTest() {
        remoteDataSource.update(
            HistoryFireStoreResponse(
                userId = "user_id_1",
                id = "cLb0SF1AmuWt40fOnG2w",
                address = "address_update",
                longitude = 1.31313,
                latitude = 1.31131
            )
        )
        Thread.sleep(2000)
    }

    @Test
    fun deleteTest() {
        remoteDataSource.delete(userId = "user_id_1", "wWua84AcFRgqVh9olVx2")
        Thread.sleep(2000)
    }
}