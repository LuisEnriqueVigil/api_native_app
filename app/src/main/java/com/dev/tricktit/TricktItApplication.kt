package com.dev.tricktit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class TricktItApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
}