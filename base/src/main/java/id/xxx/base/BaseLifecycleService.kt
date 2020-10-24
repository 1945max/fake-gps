package id.xxx.base

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService

abstract class BaseLifecycleService : LifecycleService() {

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return ServiceBinder()
    }

    inner class ServiceBinder : Binder() {
        fun getService() = this@BaseLifecycleService
    }
}