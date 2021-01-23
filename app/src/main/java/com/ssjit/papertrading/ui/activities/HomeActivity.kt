package com.ssjit.papertrading.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssjit.papertrading.databinding.ActivityHomeBinding
import com.ssjit.papertrading.other.PaperWebSocketListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("before try")

        initWebSocket()

        initUI()

    }

    private fun initWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.0.105:5000").build()
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

    override fun onDestroy() {
        super.onDestroy()
    }
}