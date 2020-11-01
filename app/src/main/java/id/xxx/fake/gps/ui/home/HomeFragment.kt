package id.xxx.fake.gps.ui.home

import android.content.Intent
import android.location.Location
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentHomeBinding
import id.xxx.fake.gps.domain.auth.usecase.IAuthUseCase
import id.xxx.fake.gps.map.Map
import id.xxx.fake.gps.service.FakeLocation
import id.xxx.fake.gps.service.FakeLocationService
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.history.HistoryActivity
import id.xxx.fake.gps.ui.search.SearchActivity
import id.xxx.fake.gps.utils.formatDouble
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

open class HomeFragment :
    BaseFragment<FragmentHomeBinding>(),
    Map.Callback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener,
    View.OnClickListener {

    private lateinit var map: Map

    private var googleMap: GoogleMap? = null
    private var location: Location = Location(GPS_PROVIDER)
    private var markerPosition: Marker? = null
    private var markerOptions: MarkerOptions? = null

    override val layoutFragment: Int = R.layout.fragment_home

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
            R.id.btn_logout -> {
                inject<IAuthUseCase>().value.signOut()
                startActivity(Intent(requireContext(), AuthActivity::class.java).apply {
                    requireActivity().finish()
                })
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