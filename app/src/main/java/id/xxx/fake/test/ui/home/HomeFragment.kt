package id.xxx.fake.test.ui.home

import android.content.Intent
import android.location.Location
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.base.binding.delegate.viewBinding
import com.base.extension.openActivityAndFinis
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.FragmentHomeBinding
import id.xxx.fake.test.domain.auth.usecase.IInteractor
import id.xxx.fake.test.service.FakeLocation
import id.xxx.fake.test.service.FakeLocationService
import id.xxx.fake.test.ui.MainActivity
import id.xxx.fake.test.ui.history.HistoryActivity
import id.xxx.fake.test.ui.home.map.Map
import id.xxx.fake.test.ui.search.SearchActivity
import id.xxx.fake.test.utils.formatDouble
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@FlowPreview
@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home),
    Map.Callback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener,
    View.OnClickListener {

    private val binding by viewBinding<FragmentHomeBinding>()

    private lateinit var map: Map

    private var googleMap: GoogleMap? = null
    private var location: Location = Location(GPS_PROVIDER)
    private var markerPosition: Marker? = null
    private var markerOptions: MarkerOptions? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val smf = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
        map = Map(smf, this).apply { binding.onClick = this@HomeFragment }

        FakeLocation.isRunning.observe(viewLifecycleOwner, {
            btn_stop_fake.visibility = if (it) VISIBLE else GONE
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        when (requestCode) {
            Map.REQUEST_CODE_ENABLE_GPS -> googleMap?.apply {
                map.enableMyPosition(requireActivity(), this)
            }
            HistoryActivity.REQUEST_CODE, SearchActivity.REQUEST_CODE -> {
                intent?.apply {
                    LatLng(
                            getDoubleExtra("latitude", 0.0), getDoubleExtra("longitude", 0.0)
                    ).apply { addSingleMaker(this) }
                }
            }
        }
    }

    override fun onMapLongClick(latLng: LatLng) {
        fakeStart(latLng.latitude, latLng.longitude)
    }

    override fun onMapClick(latLng: LatLng) {
        addSingleMaker(latLng)
    }

    override fun onCameraMove() {
        googleMap?.apply {
            val cameraPosition = cameraPosition
            cameraPosition.apply {
                val a =
                        formatDouble(target.latitude) == formatDouble(location.latitude)
                val b =
                        formatDouble(target.longitude) == formatDouble(location.longitude)
                aci_my_position?.apply { visibility = if (a && b) GONE else VISIBLE }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    override fun onLocationChanged(location: Location) {
        this.location = location
        moveCamera(location.latitude, location.longitude)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        removeSingleMarker()
        return true
    }

    override fun onCameraIdle() {
        googleMap?.let { googleMap ->
            markerOptions?.let { markerOptions ->
                markerPosition = googleMap.addMarker(markerOptions).apply {
                    btn_start_fake.visibility = VISIBLE
                    showInfoWindow()
                    googleMap.setOnMarkerClickListener(this@HomeFragment)
//                    googleMap.setOnInfoWindowClickListener(this@HomeFragment)
                }
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_logout -> lifecycleScope.launch {
                inject<IInteractor>().value.signOut()
                openActivityAndFinis<MainActivity>()
            }

            R.id.toolbar -> {
                val intent = Intent(requireContext(), SearchActivity::class.java)
                requireActivity().startActivityForResult(intent, SearchActivity.REQUEST_CODE)
            }
            R.id.aci_my_position -> googleMap?.apply {
                map.enableMyPosition(requireActivity(), this)
            }
            R.id.btn_move_to_history -> {
                val intent = Intent(requireActivity(), HistoryActivity::class.java)
                requireActivity().startActivityForResult(intent, HistoryActivity.REQUEST_CODE)
            }
            R.id.btn_start_fake -> {
                markerPosition?.apply {
                    fakeStart(position.latitude, position.longitude)
                    removeSingleMarker()
                }
            }
            R.id.btn_stop_fake -> {
                requireActivity().stopService(Intent(context, FakeLocationService::class.java))
            }
        }
    }

    private fun fakeStart(latitude: Double, longitude: Double) {
        Intent(requireContext(), FakeLocationService::class.java).apply {
            putExtra("latitude", latitude)
            putExtra("longitude", longitude)
            requireActivity().startService(this)
        }
    }

    private fun moveCamera(latitude: Double, longitude: Double) {
        googleMap?.apply {
            animateCamera(
                    CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), cameraPosition.zoom)
            )
        }
    }

    private fun addSingleMaker(latLng: LatLng) {
        btn_start_fake.visibility = GONE
        googleMap?.clear()
        moveCamera(latLng.latitude, latLng.longitude)
        markerOptions = MarkerOptions().title("show location").position(latLng)
    }

    private fun removeSingleMarker() {
        btn_start_fake.visibility = GONE
        googleMap?.clear()
        markerPosition = null
        markerOptions = null
    }
}