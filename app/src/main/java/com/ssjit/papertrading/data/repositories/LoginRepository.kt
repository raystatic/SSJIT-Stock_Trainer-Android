package com.ssjit.papertrading.data.repositories

import androidx.room.PrimaryKey
import com.ssjit.papertrading.data.local.UserDao
import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.User
import com.ssjit.papertrading.data.remote.ApiHelper
import javax.inject.Inject

class LoginRepository @Inject constructor(
        private val apiHelper: ApiHelper,
        private val userDao: UserDao
) {

    suspend fun login(loginRequest: LoginRequest) = apiHelper.login(loginRequest)

    suspend fun insertUser(user: User) = userDao.insertUser(user)

}