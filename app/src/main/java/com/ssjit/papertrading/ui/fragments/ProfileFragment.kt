package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.ProfileItem
import com.ssjit.papertrading.databinding.FragmentProfileBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.adapters.ProfileItemAdapter
import com.ssjit.papertrading.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!

    private val viewmodel by viewModels<ProfileViewModel>()

    private lateinit var profileItemAdapter: ProfileItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.app_puple)

        profileItemAdapter = ProfileItemAdapter {
            it?.let {

            }
        }

        binding.rvProfileItems.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter = profileItemAdapter
        }

        viewmodel.user.observe(viewLifecycleOwner, Observer {
            it?.let { user->
                binding.apply {
                    Glide.with(requireContext())
                            .load(user.avatar)
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(imgProfile)

                    tvUserName.text = user.name
                    tvUserBalance.text = "${Constants.RUPEE_SYMBOL} ${Utility.formatAmount(user.balance)}"

                    val profileItem = ProfileItem(itemType = "demo",text = user.profit,caption = "Total net profit")
                    val list = mutableListOf<ProfileItem>()
                    for (i in 0 until 10){
                        list.add(profileItem)
                    }

                    profileItemAdapter.submitData(list)

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}