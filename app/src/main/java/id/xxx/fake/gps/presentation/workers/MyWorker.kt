package id.xxx.fake.gps.presentation.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import id.xxx.base.domain.model.ApiResponse
import id.xxx.fake.gps.data.helper.Address
import id.xxx.fake.gps.data.mapper.toHistoryModel
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import id.xxx.fake.gps.domain.history.usecase.IInteractor as IHistoryInteractor

class MyWorker(
    private val context: Context, workerParam: WorkerParameters
) : CoroutineWorker(context, workerParam), KoinComponent {

    companion object {
        const val DATA_EXTRA = "NETWORK_DATA_EXTRA"
    }

    private val history by inject<IHistoryInteractor>()

    override suspend fun doWork() = coroutineScope {
        val value = inputData.getString(DATA_EXTRA) ?: return@coroutineScope Result.failure()
        when (val response = Address.getInstance(context).geoCoder(value)) {
            is ApiResponse.Success -> {
                history.insert(response.data.toHistoryModel())
                Result.success(inputData)
            }
            is ApiResponse.Error -> {
                Result.failure()
            }
            is ApiResponse.Empty -> {
                Result.success()
            }
        }
    }
}