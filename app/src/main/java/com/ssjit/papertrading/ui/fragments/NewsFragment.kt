package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentHoldingBinding
import com.ssjit.papertrading.databinding.NewsFragmentBinding
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.adapters.NewsAdapter
import com.ssjit.papertrading.ui.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: Fragment(R.layout.news_fragment) {

    private var _binding: NewsFragmentBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<NewsViewModel>()

    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = NewsFragmentBinding.bind(view)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.logo_green)

        newsAdapter = NewsAdapter {
            Utility.openUrl(requireContext(),it)
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        viewmodel.getNews()

        viewmodel.newsResponse.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        binding.rvNews.isVisible = res.items.result.isNotEmpty()
                        binding.progressBar.isVisible = false
                        if (res.items.result.isNotEmpty()){
                            newsAdapter.submitData(res.items.result)
                        }else{
                            binding.root.showSnack("No news found!")
                        }
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.ERROR -> {
                    binding.root.showSnack(it.message.toString())
                    binding.progressBar.isVisible = false
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}