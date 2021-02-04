package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import com.ssjit.papertrading.data.models.transaction.GetOrdersResponse
import com.ssjit.papertrading.data.models.transaction.Order
import com.ssjit.papertrading.data.repositories.OrdersRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class OrdersViewModel @ViewModelInject constructor(
    private val repository:OrdersRepository
):ViewModel() {

    private val _ordersResponse = MutableLiveData<Resource<GetOrdersResponse>>()

    val ordersResponse:LiveData<Resource<GetOrdersResponse>> get() = _ordersResponse

    private val _dataLoading = MutableLiveData<Boolean>()

    val dataLoading:LiveData<Boolean> get() = _dataLoading

    fun isDataLoading(b:Boolean) = viewModelScope.launch {
        _dataLoading.postValue(b)
    }

    fun getOrders(userId:String) = viewModelScope.launch {
        _ordersResponse.postValue(Resource.loading(null))

        try {
            repository.getOrders(userId).also {
                if (it.isSuccessful){
                    _ordersResponse.postValue(Resource.success(it.body()))
                }else{
                    _ordersResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _ordersResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
        }

    }

    fun insertOrder(order:Order) = viewModelScope.launch {
        repository.insertOrders(order)
    }

    val allOrders = repository.getAllOrders()

    val pendingOrders = repository.getPendingOrders()

    val executedOrders = repository.getExecutedOrders()

    val user = repository.getUser()

    val holdings = repository.getHoldings()

    val positions = repository.getPositions()

    fun deleteAllOrders() = viewModelScope.launch {
        repository.deleteAllOrders()
    }

    private val _pricesData = MutableLiveData<MutableMap<String,Int>>()

    val pricesData:LiveData<MutableMap<String,Int>> get() = _pricesData

    fun appendOrder(evaluatePrice: String) {
        val map = _pricesData.value
        map?.put(evaluatePrice, map.size - 1)
        _pricesData.postValue(map)
    }

}