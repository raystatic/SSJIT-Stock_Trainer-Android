package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.indices.BSE
import com.ssjit.papertrading.data.models.indices.NSE
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.repositories.StockInfoRepository
import kotlinx.coroutines.launch

class WatchlistViewModel @ViewModelInject constructor(
    private val repository: StockInfoRepository
):ViewModel() {


    val watchList:LiveData<List<StockData>> = repository.getWatchlist()

    val currentNSE = repository.getNSEByIndexName("NIFTY 50")

    val currentBSE = repository.getBSEBySecurityCode("S&P BSE SENSEX Next 50")

    fun upsertNSE(nse: NSE) = viewModelScope.launch {
        repository.upsertNSE(nse)
    }

    fun upsertBSE(bse:BSE) = viewModelScope.launch {
        repository.upsertBSE(bse)
    }


}