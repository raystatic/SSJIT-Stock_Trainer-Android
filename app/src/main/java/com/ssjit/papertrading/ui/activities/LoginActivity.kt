package com.ssjit.papertrading.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.LoginRequest
import com.ssjit.papertrading.databinding.ActivityLoginBinding
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.PrefManager
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


private const val RC_SIGN_IN = 100

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var gso : GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val vm by viewModels<LoginViewModel>()

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        startActivity(Intent(this,HomeActivity::class.java))
//        finish()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)

//        account?.let { acc->
//            updateUI(acc)
//        }

        binding.cardLogin.setOnClickListener {
            signIn()
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        vm.loginResponse.observe(this, Observer {
            when(it.status){
                Status.ERROR -> binding.root.showSnack(it.message ?: Constants.SOMETHING_WENT_WRONG)
                Status.LOADING -> binding.tvLogin.text = Constants.SIGNING_IN
                Status.SUCCESS -> {

                    it.data?.let { res->
                        if (!res.error){
                            res.user?.let { user ->
                                prefManager.saveString(Constants.KEY_USER_NAME, user.name)
                                prefManager.saveString(Constants.KEY_USER_EMAIL, user.email)
                                prefManager.saveString(Constants.KEY_USER_AVATAR, user.avatar)
                                prefManager.saveString(Constants.KEY_USER_ID, user.id)

                                vm.insertUser(user)

                                startActivity(Intent(this,HomeActivity::class.java))
                                finish()
                            }
                        }else{
                            binding.root.showSnack(res.message ?: Constants.SOMETHING_WENT_WRONG)
//                            startActivity(Intent(this,HomeActivity::class.java))
//                            finish()
                        }
                    }
                }
            }
        })
    }

    private fun signIn() {
        binding.tvLogin.text = Constants.SIGNING_IN
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(acc: GoogleSignInAccount) {

        val userName = acc.displayName ?: ""
        val avatar:String = acc.photoUrl.toString()
        val email = acc.email ?: ""

        if (userName.isEmpty() || email.isEmpty()){
            binding.root.showSnack(Constants.CANNOT_LOGIN)
            return
        }

        val loginRequest = LoginRequest(name = userName, email = email, avatar = avatar)
        vm.login(loginRequest)

//        if (prefManager.getString(Constants.KEY_USER_ID).isNullOrEmpty()){
//            val loginRequest = LoginRequest(name = userName, email = email, avatar = avatar)
//            vm.login(loginRequest)
//        }else{
////            startActivity(Intent(this,HomeActivity::class.java))
////            finish()
//        }

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