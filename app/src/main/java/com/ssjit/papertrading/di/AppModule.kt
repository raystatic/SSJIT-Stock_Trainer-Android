package com.ssjit.papertrading.di

import android.content.Context
import com.ssjit.papertrading.other.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePrefManager(
            @ApplicationContext context: Context
    ) = PrefManager(context)

//    @Singleton
//    @Provides
//    fun providesSocket() = IO.socket("http://192.168.0.105:5000")

}