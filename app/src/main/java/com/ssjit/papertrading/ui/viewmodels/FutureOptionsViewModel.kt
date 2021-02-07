package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.FNO.FuturesResponse
import com.ssjit.papertrading.data.models.FNO.OptionsResponse
import com.ssjit.papertrading.data.repositories.FutureOptionsRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class FutureOptionsViewModel @ViewModelInject constructor(
        private val repository:FutureOptionsRepository
):ViewModel() {

    private val _currentSymbol = MutableLiveData<String>()

    val currentSymbol:LiveData<String> get() = _currentSymbol

    fun setCurrentSymbol(symbol: String) = viewModelScope.launch {
        _currentSymbol.postValue(symbol)
    }

    private val _futures = MutableLiveData<Resource<FuturesResponse>>()
    val futures:LiveData<Resource<FuturesResponse>> get() = _futures

    private val _options = MutableLiveData<Resource<OptionsResponse>>()
    val options:LiveData<Resource<OptionsResponse>> get() = _options

    fun getFutures(symbol:String) = viewModelScope.launch {
        _futures.postValue(Resource.loading(null))

        try {
            repository.getFutures(symbol).also {
                if (it.isSuccessful){
                    _futures.postValue(Resource.success(it.body()))
                }else{
                    _futures.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _futures.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
        }
    }

    fun getOptions(symbol:String) = viewModelScope.launch {
        _options.postValue(Resource.loading(null))

        try {
            repository.getOptions(symbol).also {
                if (it.isSuccessful){
                    _options.postValue(Resource.success(it.body()))
                }else{
                    _options.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _options.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
        }
    }



}