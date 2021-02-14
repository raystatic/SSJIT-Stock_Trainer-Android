package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.payment.PaymentIntent
import com.ssjit.papertrading.data.repositories.PaymentRepository
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch

class PaymentsViewModel @ViewModelInject constructor(
    private val repository: PaymentRepository
):ViewModel() {

    private val _paymentIntent = MutableLiveData<Resource<PaymentIntent>>()

    val paymentIntent:LiveData<Resource<PaymentIntent>> get() = _paymentIntent

    fun getPaymentIntent() = viewModelScope.launch {
        _paymentIntent.postValue(Resource.loading(null))
        try {
            repository.getPayment().also {
                if (it.isSuccessful){
                    _paymentIntent.postValue(Resource.success(it.body()))
                }else{
                    _paymentIntent.postValue(Resource.error(it.message().toString(),null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}