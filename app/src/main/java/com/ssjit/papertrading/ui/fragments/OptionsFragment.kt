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
import com.ssjit.papertrading.data.models.FNO.MyDate
import com.ssjit.papertrading.data.models.FNO.MyOption
import com.ssjit.papertrading.databinding.FragmentOptionsBinding
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.ShowAlertDialog
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.ui.adapters.OptionAdapter
import com.ssjit.papertrading.ui.viewmodels.FutureOptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OptionsFragment: Fragment() {

    private var _binding: FragmentOptionsBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<FutureOptionsViewModel>()
    private lateinit var optionAdapter: OptionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        optionAdapter = OptionAdapter(requireContext())
        binding.rvOptions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = optionAdapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }


        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewmodel.currentSymbol.observe(viewLifecycleOwner,{
            it?.let {
                viewmodel.getOptions(it)
            }
        })

        viewmodel.options.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error){
                            res.options?.let { opt ->
                                binding.progressBar.isVisible = false
                                binding.rvOptions.isVisible = opt.isNotEmpty()
                                if (opt.isEmpty()){
                                    ShowAlertDialog(
                                            context = requireContext(),
                                            title = "Data not found",
                                            message = "Options of this stock is not available at the moment",
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
                               // futureItemAdapter.submitData(opt)
                                val dates = mutableListOf<MyDate>()
                                opt.forEach { date->
                                    val options = mutableListOf<MyOption>()
                                    val size = if (date.data.call.size >= date.data.put.size) date.data.call.size
                                    else date.data.put.size


                                    for (i in 0 until size){

                                        Timber.d("size: ${date.data.call.size} : ${date.data.put.size} : $i ")

                                        val call = if (date.data.call.size > i)
                                            date.data.call[i]
                                        else
                                            " - "

                                        val put = if (date.data.put.size > i)
                                            date.data.put[i]
                                        else
                                            " - "

                                        val myOption = MyOption(call,put)
                                        options.add(myOption)
                                    }

                                    val myDat = MyDate(date = date.date,options = options)
                                    dates.add(myDat)

                                }

                                optionAdapter.submitData(dates)

                            }
                        }else{
                            binding.progressBar.isVisible = false
                            binding.rvOptions.isVisible = false
                            Timber.d("Error in getting options: ${res.message}")
                            ShowAlertDialog(
                                    context = requireContext(),
                                    title = "",
                                    message = "No expiry dates present",
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
                    binding.rvOptions.isVisible = false
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.rvOptions.isVisible = false
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