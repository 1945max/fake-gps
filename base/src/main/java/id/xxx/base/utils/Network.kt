package id.xxx.base.utils

import android.content.Context
import androidx.work.*

object Network {
    const val DATA_EXTRA = "NETWORK_DATA_EXTRA"

    fun <T : ListenableWorker> onConnected(context: Context, workerClass: Class<T>, data: Data) {
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest
            .Builder(workerClass)
            .setInputData(data)
            .setConstraints(myConstraints)
            .build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }
}