package com.dev.tricktit.domain.location

import kotlin.math.roundToInt

class LocationCalculations {

    //Nos ayuda a calcular la suma de todas las sumas de distancias entre las ubicaciones
    fun getTotalDistanceMeters(locations:List<List<LocationWithTimeStamp>>):Int{
        return locations.sumOf { timestampLocation ->
            timestampLocation.zipWithNext { location1, location2 ->
                location1.location.distanceTo(location2.location)
            }.sum().roundToInt()
        }
    }
}