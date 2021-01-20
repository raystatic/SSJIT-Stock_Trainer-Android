package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.search.SearchResponse
import com.ssjit.papertrading.data.repositories.SearchRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
        private val repository: SearchRepository
):ViewModel() {

    private val _searchResponse = MutableLiveData<Resource<SearchResponse>>()

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val searchResponse get() = _searchResponse


    fun searchStocks(keyword:String) = viewModelScope.launch {
        try {
            repository.searchStock(keyword).also {
                if (it.isSuccessful){
                    _searchResponse.postValue(Resource.success(it.body()))
                }else{
                    _searchResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _searchResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
        }
    }

    companion object{
        private const val DEFAULT_QUERY = ""
    }


}