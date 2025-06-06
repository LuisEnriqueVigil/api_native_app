package com.dev.tricktit.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.dev.tricktit.domain.location.Location
import com.dev.tricktit.domain.location.LocationObserver
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AndroidLocationObserver @Inject constructor(
    private val context : Context
):LocationObserver {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<Location> {
        //permite traducir callbacks de android a flujos de kotlin
        return callbackFlow {
            val locationManager = context.getSystemService<LocationManager>()!!
            var  isGpsEnabled = false
            var isNetworkEnabled = false

            while (!isGpsEnabled && !isNetworkEnabled){
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if(!isGpsEnabled && !isNetworkEnabled) {
                    delay(3000)
                }
            }

            if(ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED){
               close()
            }else{
                client.lastLocation.addOnSuccessListener {
                    it?.let { location ->
                         trySend(location.toLocation())
                        }
                    }
                val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                    .build()
                //permite que hacer que se actualice la ubicacion, emitiendo valores cada tiempo
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let{
                            location -> trySend(location.toLocation())
                        }
                    }

                }

                client.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )


                awaitClose{
                    client.removeLocationUpdates(locationCallback)
                }
            }

        }
    }

}