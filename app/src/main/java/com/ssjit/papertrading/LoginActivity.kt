package com.ssjit.papertrading

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ssjit.papertrading.ViewExtension.showSnack
import com.ssjit.papertrading.databinding.ActivityLoginBinding
import timber.log.Timber


private const val RC_SIGN_IN = 100
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var gso : GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        account?.let { acc->
            updateUI(acc)
        }

        binding.cardLogin.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        binding.tvLogin.text = Constants.SIGNING_IN
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(acc: GoogleSignInAccount) {
        Timber.d("login_sucsess: ${acc.displayName}")
        binding.root.showSnack(message = "Login success: ${acc.displayName}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

            account?.let {
                updateUI(it)
                binding.tvLogin.text = Constants.LOGIN_SUCCESS
            } ?: kotlin.run {
                binding.root.showSnack(Constants.CANNOT_LOGIN)
                binding.tvLogin.text = getString(R.string.contiue_with_google)
            }

        } catch (e: ApiException) {
            Timber.d("signInResult:failed code=${e.statusCode}")
            binding.root.showSnack(Constants.LOGIN_FAILED)
            binding.tvLogin.text = getString(R.string.contiue_with_google)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}