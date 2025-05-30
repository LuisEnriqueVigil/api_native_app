package com.dev.tricktit.data

import android.location.Location

//extension function to convert Location to LatLng
fun Location.toLocation() = com.dev.tricktit.domain.location.Location(
    lat = latitude,
    long =  longitude
)