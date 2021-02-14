package com.ssjit.papertrading.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.ActivityHomeBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.PaperWebSocketListener
import com.ssjit.papertrading.other.ShowAlertDialog
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding

    private lateinit var socket: Socket
    private lateinit var listener: PaperWebSocketListener

    private val viewmodel by viewModels<StockInfoViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToTransaction = intent.getBooleanExtra("TRANSACTION", false)
        futureOptionsSymbol = intent.getStringExtra("FUTUREOPTIONSSYMBOL") ?: ""

        initWebSocket()

        initUI()

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewmodel.user.observe(this){
            it?.let {
                if (Utility.shouldShowProVersionDialog(it.user_created_at, it.isProUser))
                    ShowAlertDialog(
                            context = this,
                            title = "Upgrade to Pro Version",
                            message = "Upgrade to pro version to get access all the exclusive features",
                            positive = "Okay",
                            negative = "Skip",
                            onPositiveButtonClicked = {
                                startActivity(Intent(this,PaymentsActivity::class.java))
                            },
                            onNegativeButtonClicked = {

                            }
                    )
            }
        }
    }

    private fun initWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url(Constants.WEB_SOCKET_URL).build()
        listener = PaperWebSocketListener()
        val ws = client.newWebSocket(request, listener)
        Timber.d("web_socket: $ws")
    }

    private fun initUI() {
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            /* NO OP*/
        }
    }

    companion object{
        var goToTransaction = false
        var futureOptionsSymbol = ""
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}