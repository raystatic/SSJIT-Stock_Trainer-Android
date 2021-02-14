package com.ssjit.papertrading.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.gson.GsonBuilder
import com.ssjit.papertrading.databinding.ActivityPaymentsBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.ShowAlertDialog
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.viewmodels.PaymentsViewModel
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.ref.WeakReference


const val UPI_PAYMENT = 123

@AndroidEntryPoint
class PaymentsActivity : AppCompatActivity() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private val viewmodel by viewModels<PaymentsViewModel>()

    private lateinit var binding:ActivityPaymentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        stripe = Stripe(applicationContext,Constants.STRIPE_API_KEY)

        viewmodel.getPaymentIntent()

        binding.btnPay.setOnClickListener {

            binding.cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
                stripe.confirmPayment(this, confirmParams)
            }
        }


//        binding.apply {
//            btnPay.setOnClickListener {
//                val name = etName.text.toString()
//                if (name.isEmpty()){
//                    inputName.error = "Name cannot be empty"
//                    return@setOnClickListener
//                }
//
//                inputName.error = null
//
////                val upi = etUPIID.text.toString()
////                if (upi.isEmpty()){
////                    inputUPIID.error = "UPI Id cannot be empty"
////                    return@setOnClickListener
////                }
////
////                if (!upi.matches(Regex("^[\\w.-]+@[\\w.-]+\$"))){
////                    inputUPIID.error = "Invalid UPI ID"
////                    return@setOnClickListener
////                }
//
//                inputUPIID.error = null
//
//                val amount = "499"
//
//                val uri: Uri = Uri.Builder()
//                    .scheme("upi")
//                    .authority("pay")
//                    .appendQueryParameter("pa", Constants.GPAY_MERCHANT_UPI)
//                    .appendQueryParameter("pn", Constants.GPAY_MERCHANT_NAME)
//                    .appendQueryParameter("mc", "123456")
//                    .appendQueryParameter("tr", "9650124756")
//                    .appendQueryParameter("tn", "test transaction by user")
//                    .appendQueryParameter("am", "5")
//                    .appendQueryParameter("cu", "INR")
//                   // .appendQueryParameter("url", "your-transaction-url")
//                    .build()
//
//                val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = uri
//              //  intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
//       //         startActivityForResult(intent, UPI_PAYMENT)
//                val chooser = Intent.createChooser(intent, "Pay with")
//
//                if (chooser.resolveActivity(packageManager) != null){
//                    startActivityForResult(chooser, UPI_PAYMENT)
//                }else{
//                    binding.root.showSnack("No Upi App found")
//                }
//
//
//            }
//
//
//        }

        subscribeToObservers()


    }

    private fun subscribeToObservers() {
        viewmodel.paymentIntent.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let {res->
                        if (!res.error){
                            paymentIntentClientSecret = res.client_secret
                            binding.btnPay.isEnabled = true
                        }
                        binding.progressBar.isVisible = false
                    }
                }

                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.btnPay.isEnabled = false
                }

                Status.ERROR -> {
                    Timber.d("Error in payment: ${it.message}")
                    binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
                    binding.progressBar.isVisible = false
                    binding.btnPay.isEnabled = false
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val weakActivity = WeakReference<Activity>(this)
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                val status = paymentIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    weakActivity.get()?.let { activity ->


                        ShowAlertDialog(
                            context = activity,
                            title = "Payment Status",
                            message = "Payment successfully done",
                            positive = "Okay",
                            negative = null,
                            onPositiveButtonClicked = {
                                finish()
                            },
                            onNegativeButtonClicked = {

                            }
                        )

                    }
                } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                    weakActivity.get()?.let { activity ->

                        Timber.d("stripe_failed: ${paymentIntent.lastPaymentError?.message.orEmpty()}")

                        ShowAlertDialog(
                            context = activity,
                            title = "Payment Status",
                            message = "Payment Failed",
                            positive = "Okay",
                            negative = null,
                            onPositiveButtonClicked = {
                                finish()
                            },
                            onNegativeButtonClicked = {

                            }
                        )
                    }
                }
            }
            override fun onError(e: Exception) {
                weakActivity.get()?.let { activity ->

                    Timber.d("stripe_failed: ${e.toString()}")


                    ShowAlertDialog(
                        context = activity,
                        title = "Payment Status",
                        message =  "Payment failed",
                        positive = "Okay",
                        negative = null,
                        onPositiveButtonClicked = {
                            finish()
                        },
                        onNegativeButtonClicked = {

                        }
                    )
                }
            }
        })

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
////        when(requestCode){
////            UPI_PAYMENT -> {
////                if ((resultCode == RESULT_OK) || (resultCode == 11)) {
////                    data?.let {
////                        val trxn = it.getStringExtra("response")
////                        Timber.d("Payment success: $trxn")
////                        binding.root.showSnack("Payment done successfully")
////                    } ?: kotlin.run {
////                        Timber.d("Payment failed: data is null")
////                        binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
////                    }
////                } else {
////                    Timber.d("Payment failed: result code not ok")
////                    binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
////                }
////            }
////        }
//        if (requestCode == UPI_PAYMENT) {
//            // Process based on the data in response.
//            Timber.d("gpay_status: ${data?.getStringExtra("Status")}")
//        }
//    }

}