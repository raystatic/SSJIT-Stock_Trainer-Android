package com.ssjit.papertrading.data.repositories

import com.ssjit.papertrading.data.local.UserDao
import com.ssjit.papertrading.data.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val userDao: UserDao
){

    suspend fun insertUser(user:User) = userDao.insertUser(user)

    fun getUsers() = userDao.getUsers()

    suspend fun deleteUser() = userDao.deleteUsers()

}