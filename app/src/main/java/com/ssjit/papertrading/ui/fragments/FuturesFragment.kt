package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.databinding.FragmentFuturesBinding
import com.ssjit.papertrading.databinding.FragmentFuturesOptionsBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.ShowAlertDialog
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.ui.adapters.FutureItemAdapter
import com.ssjit.papertrading.ui.viewmodels.FutureOptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FuturesFragment: Fragment() {

    private var _binding: FragmentFuturesBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<FutureOptionsViewModel>()
    private lateinit var futureItemAdapter:FutureItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFuturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        futureItemAdapter = FutureItemAdapter()
        binding.rvFutures.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = futureItemAdapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewmodel.futures.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error){
                            res.futures?.let { fut ->
                                binding.progressBar.isVisible = false
                                binding.rvFutures.isVisible = fut.isNotEmpty()
                                if (fut.isEmpty()){
                                    ShowAlertDialog(
                                        context = requireContext(),
                                        title = "Data not found",
                                        message = "Futures of this stock is not available at the moment",
                                        positive = "Okay",
                                        negative = null,
                                        onPositiveButtonClicked = {
                                            requireActivity().finish()
                                        },
                                        onNegativeButtonClicked = {

                                        }
                                    )

                                    return@observe
                                }
                                futureItemAdapter.submitData(fut)
                            }
                        }else{
                            binding.progressBar.isVisible = false
                            binding.rvFutures.isVisible = false
                            Timber.d("Error in getting futures: ${res.message}")
                            ShowAlertDialog(
                                context = requireContext(),
                                title = "Data not found",
                                message = "No expiry dates of this stock are available at the moment",
                                positive = "Okay",
                                negative = null,
                                onPositiveButtonClicked = {
                                    requireActivity().finish()
                                },
                                onNegativeButtonClicked = {

                                }
                            )

                        }
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.rvFutures.isVisible = false
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.rvFutures.isVisible = false
                    binding.root.showSnack(it.message.toString())
                }
            }
        })

        viewmodel.currentSymbol.observe(viewLifecycleOwner,{
            it?.let {
                viewmodel.getFutures(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}