package com.ssjit.papertrading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentSearchBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.DebounceQueryTextListener
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.ViewExtension.showSnack
import com.ssjit.papertrading.ui.activities.StockDetailsActivity
import com.ssjit.papertrading.ui.adapters.SearchItemAdapter
import com.ssjit.papertrading.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding?=null

    private val binding get() = _binding!!

    private val viewmodel by activityViewModels<SearchViewModel>()

    private lateinit var searchAdapter: SearchItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.apply {
            isIconified = false
            queryHint = "Search stocks eg. Reliance, TCS"
            setOnCloseListener {
                return@setOnCloseListener true
            }
            setOnQueryTextListener(
                DebounceQueryTextListener(
                   this@SearchFragment.lifecycle
                ){
                    it?.let {
                        if (it.isNotEmpty()){
                            viewmodel.searchStocks(it)
                        }
                    }
                }
            )
        }

        searchAdapter = SearchItemAdapter(onClick = {symbol->
            symbol?.let {
//                StockDetailsFragment.stockSymbol = symbol
//                binding.root.findNavController().navigate(R.id.action_searchFragment_to_stockDetailsFragment)
                val intent = Intent(requireContext(),StockDetailsActivity::class.java)
                intent.putExtra(Constants.STOCK_SYMBOL,symbol)
                startActivity(intent)
            }
        })
        binding.rvSearchItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewmodel.searchResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { res ->
                        if (!res.error) {
                            res.data?.let { it1 ->
                                searchAdapter.submitData(it1)
                            }
                        } else {
                            binding.root.showSnack(it.message ?: Constants.SOMETHING_WENT_WRONG)
                        }
                    }
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {
                    binding.root.showSnack(it.message.toString())
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}