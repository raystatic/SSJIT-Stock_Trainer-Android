package com.ssjit.papertrading.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.futured.donut.DonutSection
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.ActivityStockDetailsBinding

class StockDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStockDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(R.color.primary_green)


        val section1 = DonutSection(
            name = "section_1",
            color = resources.getColor(R.color.green),
            amount = 0.81f
        )

        binding.donutView.cap = 1f
        binding.donutView.submitData(listOf(section1))

    }
}