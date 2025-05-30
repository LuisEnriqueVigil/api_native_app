package com.dev.tricktit.presentation.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline


@Composable
fun PolyLineSection(
    latLngArray: List<LatLng>
) {

    val newArray = latLngArray.zipWithNext()
    newArray.forEach {
        pair->
       Polyline(
               points = listOf(
                   pair.first,
                   pair.second
               ),
               width = 10f,
               color = Color.Red,
               geodesic = true,
               visible = true,
               jointType = JointType.BEVEL,
           )
    }



}