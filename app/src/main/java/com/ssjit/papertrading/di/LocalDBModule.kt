package com.ssjit.papertrading.di

import android.content.Context
import androidx.room.Room
import com.ssjit.papertrading.data.local.RoomDB
import com.ssjit.papertrading.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object LocalDBModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RoomDB::class.java,
        Constants.ROOM_DB_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesLocalStocksDao(db:RoomDB) = db.getLocalStockDao()

    @Singleton
    @Provides
    fun providesNSEDao(db: RoomDB) = db.getNSEDao()

    @Singleton
    @Provides
    fun providesBSEDao(db:RoomDB) = db.getBSEDao()

    @Singleton
    @Provides
    fun providesUserDao(db:RoomDB) = db.getUserDao()

    @Singleton
    @Provides
    fun providesOrdersDao(db: RoomDB) = db.getOrdersDao()

}