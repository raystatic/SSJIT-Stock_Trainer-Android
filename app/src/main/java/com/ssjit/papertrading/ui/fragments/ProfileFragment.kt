package com.ssjit.papertrading.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.ProfileItem
import com.ssjit.papertrading.databinding.FragmentProfileBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.activities.PaymentsActivity
import com.ssjit.papertrading.ui.adapters.ProfileItemAdapter
import com.ssjit.papertrading.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment: Fragment(){

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!

    private val viewmodel by activityViewModels<LoginViewModel>()

    private lateinit var profileItemAdapter: ProfileItemAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(
                requireContext(),
                R.color.logo_green
        )


        profileItemAdapter = ProfileItemAdapter(requireContext()) {
            it?.let {
                when(it){
                    Constants.PROFILE_MARKET_NEWS -> {
                        binding.root.findNavController().navigate(R.id.action_profileFragment_to_newsFragment)
                    }

                    Constants.PROFILE_WHATSAPP_SUPPORT -> {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_EMAIL, "rahul9650ray@gmail.com")

                        startActivity(Intent.createChooser(intent, "Send Email"))
                    }

                    Constants.PROFILE_PRO_VERSION -> {
                        startActivity(Intent(requireContext(),PaymentsActivity::class.java))
                    }

                }
            }
        }

        binding.rvProfileItems.apply {
//            layoutManager = GridLayoutManager(
//                requireContext(),
//                2,
//                GridLayoutManager.VERTICAL,
//                false
//            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = profileItemAdapter
        }

        viewmodel.user.observe(viewLifecycleOwner, {
            it?.let { user ->
                binding.apply {
                    Glide.with(requireContext())
                            .load(user.avatar)
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(imgProfile)

                    tvUserName.text = user.name
                    tvUserBalance.text = Utility.formatAmount("${user.balance.toFloat()}")

                    val entries = mutableListOf<PieEntry>()
                    entries.add(PieEntry(user.balance.replace(",", "").toFloat(), "Balance"))
                    entries.add(PieEntry(user.investment.replace(",", "").toFloat(), "Investment"))
                    entries.add(PieEntry(user.profit.replace(",", "").toFloat(), "Profit"))
                    entries.add(PieEntry(user.loss.replace(",", "").toFloat(), "Loss"))

                    val pieDataSet = PieDataSet(entries, "")
                    pieDataSet.colors = MATERIAL_COLORS.toMutableList()

                    val data = PieData(pieDataSet)
                    data.setValueTextSize(10f)
                    binding.chart1.apply {
                        setData(data)
                        invalidate()
                        setExtraOffsets(5f, 10f, 5f, 5f)
                        setDrawEntryLabels(false)
                        contentDescription = ""
                        setEntryLabelTextSize(12f)
                        description.isEnabled = false
                        holeRadius = 15f
                        setDrawEntryLabels(false)
                        setEntryLabelTextSize(0f)
                        val legend: Legend = legend
                        legend.form = Legend.LegendForm.CIRCLE
                        legend.textSize = 10f
                        legend.formSize = 12f
                        legend.formToTextSpace = 5f
                    }

                    val list = mutableListOf<ProfileItem>()
//                    list.add(
//                        ProfileItem(
//                            itemType = Constants.PROFILE_NET_PROFIT, text = Utility.formatAmount(
//                                "${user.profit.toFloat()}"
//                            ), caption = Constants.PROFILE_NET_PROFIT
//                        )
//                    )
//                    list.add(
//                        ProfileItem(
//                            itemType = Constants.PROFILE_NET_LOSS, text = Utility.formatAmount(
//                                "${user.loss.toFloat()}"
//                            ), caption = Constants.PROFILE_NET_LOSS
//                        )
//                    )
//                    list.add(
//                        ProfileItem(
//                            itemType = Constants.PROFILE_POSITIVE_TRANSACTIONS,
//                            text = "${user.positive_transactions}",
//                            caption = Constants.PROFILE_POSITIVE_TRANSACTIONS
//                        )
//                    )
//                    list.add(
//                        ProfileItem(
//                            itemType = Constants.PROFILE_NEGATIVE_TRANSACTIONS,
//                            text = "${user.negative_transactions}",
//                            caption = Constants.PROFILE_NEGATIVE_TRANSACTIONS
//                        )
//                    )

                    list.add(
                            ProfileItem(
                                    itemType = Constants.PROFILE_MARKET_NEWS,
                                    image = R.drawable.ic_icons8_news,
                                    caption = Constants.PROFILE_MARKET_NEWS
                            )
                    )

                    if (user.isProUser == 0){
                        list.add(
                            ProfileItem(
                                itemType = Constants.PROFILE_PRO_VERSION,
                                image = R.drawable.ic_high_quality,
                                caption = Constants.PROFILE_PRO_VERSION
                            )
                        )
                    }

                    list.add(
                            ProfileItem(
                                    itemType = Constants.PROFILE_INVITE_FRIENDS,
                                    image = R.drawable.ic_group,
                                    caption = Constants.PROFILE_INVITE_FRIENDS
                            )
                    )

                    list.add(
                            ProfileItem(
                                    itemType = Constants.PROFILE_PRIVACY_POLICY,
                                    image = R.drawable.ic_privacy_policy,
                                    caption = Constants.PROFILE_PRIVACY_POLICY
                            )
                    )
                    list.add(
                            ProfileItem(
                                    itemType = Constants.PROFILE_WHATSAPP_SUPPORT,
                                    image = R.drawable.ic_mail,
                                    caption = Constants.PROFILE_WHATSAPP_SUPPORT
                            )
                    )
                    profileItemAdapter.submitData(list)

                }

            }
        })
    }

    private fun initChart() {
        binding.chart1.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            rotationAngle=0f
            isHighlightPerTapEnabled = true

            val l: Legend = legend
            l.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                xEntrySpace = 7f
                yEntrySpace = 0f
                yOffset = 0f
            }


            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}