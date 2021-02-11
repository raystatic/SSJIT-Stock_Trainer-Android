package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.news.NewsResponse
import com.ssjit.papertrading.data.repositories.NewsRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.lang.Exception

class NewsViewModel @ViewModelInject constructor(
        private val repository:NewsRepository
):ViewModel() {

    private val _newsResponse = MutableLiveData<Resource<NewsResponse>>()

    val newsResponse:LiveData<Resource<NewsResponse>> get() = _newsResponse

    fun getNews() = viewModelScope.launch {
        _newsResponse.postValue(Resource.loading(null))
        try {

            repository.getNews().also {
                if (it.isSuccessful){
                    Timber.d("reponse ${it.body()}")
                    _newsResponse.postValue(Resource.success(it.body()))
                }else{
                    _newsResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _newsResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
        }
    }

}