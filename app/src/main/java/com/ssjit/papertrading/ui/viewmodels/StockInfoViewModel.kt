package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import com.ssjit.papertrading.data.repositories.StockInfoRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class StockInfoViewModel @ViewModelInject constructor(
    private val repository: StockInfoRepository
):ViewModel() {

    private val _stockInfoResponse = MutableLiveData<Resource<StockDetailResponse>>()

    val stockInfoResponse get() = _stockInfoResponse

    fun getStockInfo(symbol:String) = viewModelScope.launch {
        try {
            repository.getStockInfo(symbol).also {
                if (it.isSuccessful){
                    _stockInfoResponse.postValue(Resource.success(it.body()))
                }else{
                    _stockInfoResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _stockInfoResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
        }
    }

    fun upsertStockData(stockData: StockData) = viewModelScope.launch {
        repository.upsertLocalStock(stockData)
    }

}