package com.dev.tricktit.presentation.maps

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberUpdatedMarkerState


val latLngArray =  listOf(
    LatLng(4.6547591408952185, -74.05578687079682),
    LatLng(4.656732, -74.057851),
    LatLng(4.668311, -74.074094),
)

@Composable
fun MapSection(
    modifier: Modifier = Modifier,
){
    val activity= LocalActivity.current
    //RememberUpdatedMarkerState is used to remember the state of the marker
    val marker = rememberUpdatedMarkerState()
    LaunchedEffect(true) {
        marker.position = LatLng(37.7749, -122.4194) // Example coordinates (San Francisco)
    }

    GoogleMap(
        modifier= modifier,
        uiSettings = MapUiSettings(
            zoomGesturesEnabled = true
        ),
        //This function Lambda is called when the map is loaded
        onMapLoaded = {
        Toast.makeText(activity,"MapLoaded",Toast.LENGTH_SHORT).show()
        },
    ){

        MapEffect(latLngArray) { map ->
            val bounderIsBuilder = LatLngBounds.Builder()
            latLngArray.forEach {
                latLng ->
                bounderIsBuilder.include(latLng)
            }
            map.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounderIsBuilder.build(),
                    100 // Padding around the bounds
                ),
            )
        }

        PolyLineSection(
            latLngArray = latLngArray
        )
        MarkerComposable(
            state = marker
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Current Location",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}