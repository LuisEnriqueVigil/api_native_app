package com.dev.tricktit.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(
        interval: Long,
    ):Flow<Location>
}