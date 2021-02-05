package com.ssjit.papertrading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.indices.BSEIndex
import com.ssjit.papertrading.data.models.indices.NSEIndex
import com.ssjit.papertrading.databinding.FragmentWatchlistBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.ui.activities.StockDetailsActivity
import com.ssjit.papertrading.ui.adapters.WatchlistAdapter
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import okio.ByteString
import timber.log.Timber

@AndroidEntryPoint
class WatchlistFragment: Fragment() {

    private var _binding:FragmentWatchlistBinding?=null

    private val binding get() = _binding!!

    private val viewModel by activityViewModels<StockInfoViewModel>()

    private lateinit var watchlistAdapter:WatchlistAdapter
    private lateinit var listener: PaperWebSocketListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        initWebSocket()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.primaryblue)
        binding.btnFindStocks.setOnClickListener { v->
            v.findNavController().navigate(R.id.action_watchlistFragment_to_searchFragment)
        }

        binding.imgSearch.setOnClickListener { v->
            v.findNavController().navigate(R.id.action_watchlistFragment_to_searchFragment)
        }

        watchlistAdapter = WatchlistAdapter {
            it?.let {
                val intent = Intent(requireContext(), StockDetailsActivity::class.java)
                intent.putExtra(Constants.STOCK_SYMBOL,it)
                startActivity(intent)
            }
        }

        binding.rvWatchlist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = watchlistAdapter
        }

        viewModel.watchlistResponse.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS ->{
                    it.data?.let { res->
                        if (!res.error){
                            watchlistAdapter.submitData(res.watchlist)
                        }
                    }
                    binding.rvWatchlist.isVisible = true
                    binding.progressBar.isVisible = false
                }
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.rvWatchlist.isVisible = false

                }
                Status.ERROR -> {
                    Timber.d("Error in getting watchlist ${it.message}")
                    binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
                    binding.progressBar.isVisible = false
                    binding.rvWatchlist.isVisible = false
                }
            }
        })

        viewModel.watchList.observe(viewLifecycleOwner,{
            it?.let {
                binding.btnFindStocks.isVisible = it.isEmpty()
                binding.tvFindText.isVisible = it.isEmpty()
                binding.rvWatchlist.isVisible = false
                val list = it.map {
                    it.symbol
                }
                Timber.d("list_symbols: $list")
                val symbols=list.joinToString(separator = ",")
                viewModel.getWatchlist(symbols)
                viewModel.updateCurrentWatchList(it)
            }
        })

        viewModel.currentNSE.observe(viewLifecycleOwner,{
            it?.let {
                if (it.isNotEmpty()){
                    val nse = it[0]
                    binding.tvIndexNifty.text = "NIFTY ${nse.previousClose}"
                    binding.tvIndexNiftyChange.text = "${nse.percChange}%"
                }
            }
        })

        viewModel.currentBSE.observe(viewLifecycleOwner,{
            it?.let {
                if (it.isNotEmpty()){
                    val bse = it[0]
                    binding.tvIndexSensex.text = "SENSEX ${bse.todayClose}"
                    binding.tvIndexSensexChange.text = "${bse.pointPercent}%"
                }

            }
        })

    }

    private fun initWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.0.105:5000").build()
        listener = PaperWebSocketListener()
        val ws = client.newWebSocket(request, listener)
        Timber.d("web_socket: $ws")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class PaperWebSocketListener: WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            webSocket.send("{data:start}")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Timber.d("onMessage: text $text")
            if (text.endsWith("\"type\":\"BSE\"}")){
                val bse = BSEIndex.fromString(text)
                if (!bse.error){
                    bse.data.forEach {
                        viewModel.upsertBSE(bse = it)
                    }
                }
            }else if (text.endsWith("\"type\":\"NSE\"}")){
                val nse = NSEIndex.fromString(text)
                if (!nse.error){
                    nse.data.data.forEach {
                        viewModel.upsertNSE(nse = it)
                    }
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Timber.d("onMessage: bytes $bytes")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            webSocket.close(1000, null)
            Timber.d("onClosing: $code, $reason")

        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Timber.d("onFailure: ${t.message}, $response")
        }

    }

}