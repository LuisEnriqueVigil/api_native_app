package com.dev.tricktit.di

import android.content.Context
import com.dev.tricktit.TricktItApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providedAppScope(
        @ApplicationContext context: Context,
    ): CoroutineScope {
        return (context as TricktItApplication).applicationScope
    }
}