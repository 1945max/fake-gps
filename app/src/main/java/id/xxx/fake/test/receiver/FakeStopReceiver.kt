package id.xxx.fake.test.receiver

import android.content.ContextWrapper
import android.content.Intent
import com.base.reciver.BaseReceiver
import id.xxx.fake.test.service.FakeLocationService

class FakeStopReceiver : BaseReceiver() {
    override fun onReceive(context: ContextWrapper, intent: Intent?) {
        if (intent?.action == "stop_fake_gps") {
            context.stopService(Intent(context, FakeLocationService::class.java))
        }
    }
}
