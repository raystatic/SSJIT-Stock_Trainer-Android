package com.ssjit.papertrading.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssjit.papertrading.data.models.indices.BSE
import com.ssjit.papertrading.data.models.indices.NSE
import com.ssjit.papertrading.data.models.orders.OrderRequest
import com.ssjit.papertrading.data.models.orders.OrderResponse
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.models.stockdetail.StockDetailResponse
import com.ssjit.papertrading.data.models.transaction.CreateOrderRequest
import com.ssjit.papertrading.data.models.transaction.CreateOrderResponse
import com.ssjit.papertrading.data.models.watchlist.WatchlistResponse
import com.ssjit.papertrading.data.repositories.StockInfoRepository
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class StockInfoViewModel @ViewModelInject constructor(
    private val repository: StockInfoRepository
):ViewModel() {


    private val _orderResponse = MutableLiveData<Resource<OrderResponse>>()
    val orderResponse:LiveData<Resource<OrderResponse>> get() = _orderResponse

    fun order(orderRequest: OrderRequest) = viewModelScope.launch {
        _orderResponse.postValue(Resource.loading(null))
        try {
            repository.order(orderRequest).also {
                if (it.isSuccessful){
                    _orderResponse.postValue(Resource.success(it.body()))
                }else{
                    _orderResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _orderResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
        }
    }

    private val _currrentOrder = MutableLiveData<String>()
    val currentOrder:LiveData<String> get() = _currrentOrder

    private val _currrentProduct = MutableLiveData<String>()
    val currentProduct:LiveData<String> get() = _currrentProduct

    private val _currrentVariety = MutableLiveData<String>()
    val currentVariety:LiveData<String> get() = _currrentVariety

    fun setCurrentProduct(str:String){
        _currrentProduct.postValue(str)
    }

    fun setCurrentOrder(str: String){
        _currrentOrder.postValue(str)
    }

    fun setCurrentVariety(str:String){
        _currrentVariety.postValue(str)
    }


    private val _stockDetailsLoading = MutableLiveData<Boolean>()

    val stockDetailLoading:LiveData<Boolean> get() = _stockDetailsLoading

    fun isStockDataLoading(b:Boolean)=viewModelScope.launch {
        _stockDetailsLoading.postValue(b)
    }

    private val _stockInfoResponse = MutableLiveData<Resource<StockDetailResponse>>()

    val stockInfoResponse get() = _stockInfoResponse

    private val _watchlistResponse = MutableLiveData<Resource<WatchlistResponse>>()

    val watchlistResponse get() = _watchlistResponse

    fun getWatchlist(symbols:String) = viewModelScope.launch {
        _watchlistResponse.postValue(Resource.loading(null))
        try {
            repository.getWatchlists(symbols).also {
                if (it.isSuccessful){
                    _watchlistResponse.postValue(Resource.success(it.body()))
                }else{
                    _watchlistResponse.postValue(Resource.error(it.message(),null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _watchlistResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG, null))
        }
    }

    val user = repository.getUser()

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
        _stockInfoResponse.postValue(Resource.loading(null))
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

    private val _createOrderResponse = MutableLiveData<Resource<CreateOrderResponse>>()

    val createOrderResponse:LiveData<Resource<CreateOrderResponse>> get() = _createOrderResponse

    fun createOrderRequest(createOrderRequest: CreateOrderRequest) = viewModelScope.launch {
        try {
            _createOrderResponse.postValue(Resource.loading(null))
            repository.createOrder(createOrderRequest).also {
                if (it.isSuccessful){
                    _createOrderResponse.postValue(Resource.success(it.body()))
                }else{
                    _createOrderResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            _createOrderResponse.postValue(Resource.error(Constants.SOMETHING_WENT_WRONG,null))
        }
    }

    private val _ordered = MutableLiveData<Boolean>()

    val ordered:LiveData<Boolean> get() = _ordered

    fun setOrdered(b: Boolean) {
        _ordered.postValue(b)
    }


}