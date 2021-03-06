package id.xxx.fake.gps.presentation.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import id.xxx.base.domain.model.Resource
import id.xxx.fake.gps.mapper.toHistoryModel
import id.xxx.map.box.search.domain.usecase.IInteractor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import org.koin.core.KoinComponent
import org.koin.core.inject
import id.xxx.fake.gps.history.domain.usecase.IInteractor as IHistoryInteractor

class MyWorker(
    private val context: Context, workerParam: WorkerParameters
) : CoroutineWorker(context, workerParam), KoinComponent {

    companion object {
        const val DATA_EXTRA = "NETWORK_DATA_EXTRA"
    }

    private val history by inject<IHistoryInteractor>()

    private val search by inject<IInteractor>()

    override suspend fun doWork() = coroutineScope {
        val value = inputData.getString(DATA_EXTRA) ?: return@coroutineScope Result.failure()

        val resource = search.getAddress(value).drop(1).first()
        if (resource is Resource.Success) {
            history.insert(resource.data.toHistoryModel())
            Result.success(inputData)
        } else {
            Result.failure()
        }
    }
}