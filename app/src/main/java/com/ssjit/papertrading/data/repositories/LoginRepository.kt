package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class LoginRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {

    suspend fun login(loginRequest: LoginRequest) = apiHelper.login(loginRequest)

}