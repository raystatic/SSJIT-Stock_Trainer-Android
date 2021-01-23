package com.ssjit.papertrading.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssjit.papertrading.data.models.indices.NSE

@Dao
interface NSEDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNSE(nse: NSE)

    @Query("SELECT * FROM NSE")
    fun getAllNSE():LiveData<List<NSE>>

    @Query("SELECT * FROM NSE WHERE indexName=:name")
    fun getIndexByName(name:String):LiveData<List<NSE>>

    @Query("DELETE FROM NSE")
    suspend fun deleteAllNSE()

}