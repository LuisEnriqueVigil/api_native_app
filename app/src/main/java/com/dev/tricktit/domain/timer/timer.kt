package com.dev.tricktit.domain.timer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Timer{
    fun timeAndEmit(): Flow<Duration> {
        //builder of flow
        return flow {
            //capture first interval of seconds
            var lastEmitTime = System.currentTimeMillis()
            //operations of modification flow
            while (true){
                delay(200L)
                val currentTime = System.currentTimeMillis()
                //calculate the elapsed time since the last emit 200 milliseconds
                val elapsedTime = currentTime - lastEmitTime
                emit(elapsedTime.milliseconds)
                lastEmitTime = currentTime
            }
        }
    }

    fun randomFlow():Flow<Int>{
        return flow {
            delay(1000L) // Simulate some delay
            emit((0..1000).random())
        }
    }
}