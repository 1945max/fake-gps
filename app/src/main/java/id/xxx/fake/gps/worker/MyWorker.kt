package id.xxx.fake.gps.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import id.xxx.base.utils.Network
import id.xxx.data.source.map.box.Resource
import id.xxx.fake.gps.domain.history.usecase.IHistoryUseCase
import id.xxx.fake.gps.domain.search.usecase.ISearchUseCase
import id.xxx.fake.gps.utils.DataMapper
import org.koin.core.KoinComponent
import org.koin.core.inject

class MyWorker(
    private val context: Context,
    workerParam: WorkerParameters
) : Worker(context, workerParam), KoinComponent {

    private val history by inject<IHistoryUseCase>()
    private val search by inject<ISearchUseCase>()

    override fun doWork(): Result {
        val value = inputData.getString(Network.DATA_EXTRA) ?: ""
        val resource = search.getAddress(context, value)
        if (resource is Resource.Success) {
            history.insert(DataMapper.searchModelToHistoryModel.map(resource.data))
        } else if (resource is Resource.Error) {
            return if (runAttemptCount >= 5) Result.failure() else Result.retry()
        }
        return Result.success()
    }
}