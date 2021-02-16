package id.xxx.fake.test.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import id.xxx.base.utils.Network
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.utils.DataMapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import org.koin.core.KoinComponent
import org.koin.core.inject
import id.xxx.fake.test.domain.history.usecase.IInteractor as IHistoryInteractor
import id.xxx.fake.test.domain.search.usecase.IInteractor as ISearchInteractor

class MyWorker(
    private val context: Context, workerParam: WorkerParameters
) : CoroutineWorker(context, workerParam), KoinComponent {

    private val history by inject<IHistoryInteractor>()
    private val search by inject<ISearchInteractor>()

    override suspend fun doWork() = coroutineScope {
        val value = inputData.getString(Network.DATA_EXTRA) ?: ""

        val resource = search.getAddress(context, value)
            .first { it !is Resource.Loading }

        return@coroutineScope when (resource) {
            is Resource.Loading -> TODO()
            is Resource.Empty -> Result.success()
            is Resource.Error -> if (runAttemptCount >= 5) Result.failure() else Result.retry()
            is Resource.Success -> {
                history.insert(DataMapper.searchModelToHistoryModel.map(resource.data))
                Result.success()
            }
        }
    }
}