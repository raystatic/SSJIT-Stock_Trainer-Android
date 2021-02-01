package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.indices.BSE
import com.ssjit.papertrading.data.models.indices.NSE
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import com.ssjit.papertrading.data.repositories.StockInfoRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class StockInfoViewModel @ViewModelInject constructor(
    private val repository: StockInfoRepository
):ViewModel() {

    private val _stockInfoResponse = MutableLiveData<Resource<StockDetailResponse>>()

    val stockInfoResponse get() = _stockInfoResponse


    private val _currentStock = MutableLiveData<StockData>()

    val currentStockData:LiveData<StockData> get() = _currentStock

    fun setCurrentStock(stockData: StockData) = viewModelScope.launch {
        _currentStock.postValue(stockData)
    }

    val currentWatchList = ArrayList<StockData>()

    fun updateCurrentWatchList(list: List<StockData>){
        currentWatchList.clear()
        currentWatchList.addAll(list)
    }

    val watchList: LiveData<List<StockData>> = repository.getWatchlist()

    fun addedStocks(stockData: StockData): LiveData<StockData> {
        return repository.isStockAddedToWatchlist(stockData.symbol)
    }

    val currentNSE = repository.getNSEByIndexName("NIFTY 50")

    val currentBSE = repository.getBSEBySecurityCode("S&P BSE SENSEX Next 50")

    fun upsertNSE(nse: NSE) = viewModelScope.launch {
        repository.upsertNSE(nse)
    }

    fun upsertBSE(bse: BSE) = viewModelScope.launch {
        repository.upsertBSE(bse)
    }


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

    fun deleteStockBySymbol(stockData:StockData) = viewModelScope.launch {
        repository.deleteStockBySymbol(stockData.symbol)
    }


}