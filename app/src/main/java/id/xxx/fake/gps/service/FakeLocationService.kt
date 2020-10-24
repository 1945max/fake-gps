package id.xxx.fake.gps.service

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.work.Data
import com.google.android.gms.maps.model.LatLng
import id.xxx.base.BaseLifecycleService
import id.xxx.base.utils.Executors
import id.xxx.base.utils.Network
import id.xxx.fake.gps.domain.history.usecase.IHistoryUseCase
import id.xxx.fake.gps.domain.search.usecase.ISearchUseCase
import id.xxx.fake.gps.utils.DataMapper
import id.xxx.fake.gps.worker.MyWorker
import id.xxx.data.source.map.box.Resource
import org.koin.android.ext.android.inject

class FakeLocationService : BaseLifecycleService(), FakeLocation.Callback {
    private val iHistoryRepo: IHistoryUseCase by inject()
    private val iSearchRepo: ISearchUseCase by inject()
    private val executors: Executors by inject()

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
            executors.networkIO().execute {
                val resource = iSearchRepo.getAddress(baseContext, "$latitude,$longitude")
                if (resource is Resource.Success) {
                    iHistoryRepo.insert(DataMapper.searchModelToHistoryModel.map(resource.data))
                } else if (resource is Resource.Error) {
                    Network.onConnected(
                        baseContext, MyWorker::class.java, Data.Builder()
                            .putString(Network.DATA_EXTRA, "$latitude,$longitude")
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