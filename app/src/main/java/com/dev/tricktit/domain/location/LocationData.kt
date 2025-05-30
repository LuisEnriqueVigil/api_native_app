package com.dev.tricktit.domain.location

data class LocationData (
    val distanceMeters : Int = 0,
    val locations: List<List<LocationWithTimeStamp>> = emptyList(),
){
}