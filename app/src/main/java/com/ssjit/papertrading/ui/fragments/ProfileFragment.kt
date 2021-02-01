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

        profileItemAdapter = ProfileItemAdapter(requireContext()) {
            it?.let {

            }
        }

        binding.rvProfileItems.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter = profileItemAdapter
        }

        viewmodel.user.observe(viewLifecycleOwner, {
            it?.let { user->
                binding.apply {
                    Glide.with(requireContext())
                            .load(user.avatar)
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(imgProfile)

                    tvUserName.text = user.name
                    tvUserBalance.text = Utility.formatAmount("${user.balance.toFloat()}")

                    val list = mutableListOf<ProfileItem>()
                    list.add(ProfileItem(itemType = Constants.PROFILE_NET_PROFIT,text = Utility.formatAmount("${user.profit.toFloat()}"), caption = Constants.PROFILE_NET_PROFIT))
                    list.add(ProfileItem(itemType = Constants.PROFILE_NET_LOSS,text = Utility.formatAmount("${user.loss.toFloat()}"), caption = Constants.PROFILE_NET_LOSS))
                    list.add(ProfileItem(itemType = Constants.PROFILE_POSITIVE_TRANSACTIONS,text = "${user.positive_transactions}", caption = Constants.PROFILE_POSITIVE_TRANSACTIONS))
                    list.add(ProfileItem(itemType = Constants.PROFILE_NEGATIVE_TRANSACTIONS,text = "${user.negative_transactions}", caption = Constants.PROFILE_NEGATIVE_TRANSACTIONS))
                    list.add(ProfileItem(itemType = Constants.PROFILE_INVITE_FRIENDS,image = R.drawable.ic_group, caption = Constants.PROFILE_INVITE_FRIENDS))
                    list.add(ProfileItem(itemType = Constants.PROFILE_MARKET_NEWS,image = R.drawable.ic_icons8_news, caption = Constants.PROFILE_MARKET_NEWS))
                    list.add(ProfileItem(itemType = Constants.PROFILE_PRIVACY_POLICY,image = R.drawable.ic_privacy_policy, caption = Constants.PROFILE_PRIVACY_POLICY))
                    list.add(ProfileItem(itemType = Constants.PROFILE_WHATSAPP_SUPPORT,image = R.drawable.ic_whatsapp, caption = Constants.PROFILE_WHATSAPP_SUPPORT))
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