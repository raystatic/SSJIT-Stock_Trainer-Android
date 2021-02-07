package com.ssjit.papertrading.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.ActivityFutureOptionsBinding
import com.ssjit.papertrading.ui.adapters.FutureOptionsPagerAdapter
import com.ssjit.papertrading.ui.fragments.WatchlistFragment
import com.ssjit.papertrading.ui.viewmodels.FutureOptionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FutureOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFutureOptionsBinding
    private val viewmodel by viewModels<FutureOptionsViewModel>()
    private lateinit var futureOptionsPagerAdapter:FutureOptionsPagerAdapter
    private var symbol = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFutureOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        symbol = intent.getStringExtra("FUTUREOPTIONSSYMBOL") ?: ""

        if (symbol.isEmpty()) finish()

        window?.statusBarColor = ContextCompat.getColor(this, R.color.primary_red)
        binding.foTabLayout.setupWithViewPager(binding.foPager)
        futureOptionsPagerAdapter = FutureOptionsPagerAdapter(supportFragmentManager)
        binding.foPager.adapter = futureOptionsPagerAdapter

        if (symbol.isNotEmpty()){
            viewmodel.setCurrentSymbol(symbol)
        }

    }
}