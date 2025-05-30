package com.dev.tricktit.domain.location

import java.io.File
import kotlin.time.Duration

data class LocationWithTimeStamp (
    val location: Location,
    val timeStamp: Duration,
    val listPhotos:List<File> = emptyList()
){
}