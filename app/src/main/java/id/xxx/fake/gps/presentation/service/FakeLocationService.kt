package id.xxx.fake.gps.presentation.service

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.work.Data
import com.google.android.gms.maps.model.LatLng
import id.xxx.base.domain.model.ApiResponse
import id.xxx.base.presentation.service.BaseService
import id.xxx.fake.gps.data.helper.Address
import id.xxx.fake.gps.data.helper.Network
import id.xxx.fake.gps.data.mapper.toHistoryModel
import id.xxx.fake.gps.presentation.workers.MyWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import id.xxx.fake.gps.domain.history.usecase.IInteractor as IHistoryInteractor

class FakeLocationService : BaseService(), FakeLocation.Callback {
    private val iHistoryRepo: IHistoryInteractor by inject()

    private lateinit var fakeLocation: FakeLocation
    private lateinit var fakeNotification: FakeLocationNotification

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate() {
        super.onCreate()
        fakeLocation = FakeLocation.getInstance(getSystemService(), this)
        fakeNotification = FakeLocationNotification(baseContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.apply {
            latitude = getDoubleExtra("latitude", 0.0)
            longitude = getDoubleExtra("longitude", 0.0)
            fakeLocation.run(latitude, longitude)

            CoroutineScope(Dispatchers.IO).launch {
                val address =
                    Address.getInstance(baseContext).geoCoder("$latitude,$longitude")
                if (address is ApiResponse.Success) {
                    val data = address.data
                    iHistoryRepo.insert(data.toHistoryModel())
                } else if (address is ApiResponse.Error) {
                    Network.onConnected(
                        baseContext, MyWorker::class.java, Data.Builder()
                            .putString(MyWorker.DATA_EXTRA, "$latitude,$longitude")
                            .build()
                    )
                }
            }
        } ?: stopSelf()
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        fakeLocation.stop()
    }

    override fun status(status: FakeLocation.Status) {
        when (status) {
            FakeLocation.Status.RUNNING -> {
                fakeNotification.setContentText(LatLng(latitude, longitude))
                if (VERSION.SDK_INT >= VERSION_CODES.O) {
                    startForeground(1, fakeNotification.getNotification())
                } else {
                    fakeNotification.show()
                }
            }
            FakeLocation.Status.STOP -> fakeNotification.cancel()
            FakeLocation.Status.ERROR_SECURITY_EXCEPTION -> {
                Toast.makeText(baseContext, "please enable mock location", Toast.LENGTH_LONG).show()
                stopSelf()
            }
            FakeLocation.Status.ERROR_LAT_LONG -> stopSelf()
            FakeLocation.Status.GPS_OFF -> {
                stopSelf()
                Toast.makeText(this, "please enable gps", Toast.LENGTH_LONG).show()
            }
        }
    }
}