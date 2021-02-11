package com.ssjit.papertrading.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RapisApiClient {


    companion object{

        private var retrofit: Retrofit? = null


        fun getApiClient(): Retrofit? {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl("https://apidojo-yahoo-finance-v1.p.rapidapi.com/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit
        }
    }

}