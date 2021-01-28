package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ssjit.papertrading.data.repositories.UserRepository

class ProfileViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
):ViewModel(){

    val user =  userRepository.getUsers()
}