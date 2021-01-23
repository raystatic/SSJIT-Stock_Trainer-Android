package com.ssjit.papertrading.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssjit.papertrading.data.models.indices.BSE

@Dao
interface BSEDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBSE(bse: BSE)

    @Query("SELECT * FROM BSE")
    fun getAllBSE(): LiveData<List<BSE>>

    @Query("SELECT * FROM BSE WHERE securityCode=:name")
    fun getBSEBySecurityCode(name:String):LiveData<List<BSE>>

    @Query("DELETE FROM BSE")
    suspend fun deleteAllBSE()

}