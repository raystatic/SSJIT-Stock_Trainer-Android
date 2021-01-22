package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.repositories.StockInfoRepository

class WatchlistViewModel @ViewModelInject constructor(
    private val repository: StockInfoRepository
):ViewModel() {

    val watchList:LiveData<List<StockData>> = repository.getWatchlist()

}