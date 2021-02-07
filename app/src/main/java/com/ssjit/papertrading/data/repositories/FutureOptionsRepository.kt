package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class FutureOptionsRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {

    suspend fun getOptions(symbol:String) = apiHelper.getOptions(symbol)

    suspend fun getFutures(symbol: String) = apiHelper.getFutures(symbol)

}