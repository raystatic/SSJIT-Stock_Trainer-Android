package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun deleteAllOrders() = viewModelScope.launch {
        repository.deleteAllOrders()
    }

}