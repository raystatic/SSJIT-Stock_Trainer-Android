package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.data.models.LoginResponse
import com.ssjit.papertrading.data.repositories.LoginRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
        private val repository: LoginRepository
):ViewModel() {

    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()

    val loginResponse :LiveData<Resource<LoginResponse>> get() = _loginResponse

    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        try {
            _loginResponse.postValue(Resource.loading(null))
            repository.login(loginRequest).also {
                if (it.isSuccessful){
                    _loginResponse.postValue(Resource.success(it.body()))
                }else{
                    _loginResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _loginResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
        }
    }

}