package id.xxx.fake.gps.receiver

import android.content.ContextWrapper
import android.content.Intent
import id.xxx.base.BaseReceiver
import id.xxx.fake.gps.service.FakeLocationService

class FakeStopReceiver : BaseReceiver() {
    override fun onReceive(context: ContextWrapper, intent: Intent?) {
        if (intent?.action == "stop_fake_gps") {
            context.stopService(Intent(context, FakeLocationService::class.java))
        }
    }
}
