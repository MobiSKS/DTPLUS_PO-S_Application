package com.paytm.hppay.api
import android.app.Application
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.SharedPreferencesData
import com.paytm.hpclpos.posterminal.base.DemoApp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    lateinit var retrofit: Retrofit
    lateinit var client: OkHttpClient
    private lateinit var application: Application


    val getClient: APIService
        get() {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            @Suppress("MagicNumber")
            val chuckerInterceptor = ChuckerInterceptor.Builder(DemoApp.appContext!!)
                .maxContentLength(650_000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()

            val sharedPrefData = SharedPreferencesData(DemoApp.appContext)
            val tokenId =
                sharedPrefData.getSharedPreferenceData(Constants.TOKENIDPREF, Constants.TOKENID)
                    .toString()

            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (Constants.MainUrlChanged.forToken.equals("1")) {
                client = OkHttpClient.Builder().addInterceptor(interceptor)
                    .addInterceptor(chuckerInterceptor).addInterceptor { chain ->
                    val newRequest: Request = chain.request().newBuilder()

                        //.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IiIsIm5iZiI6MTYzMjI5NDM2MywiZXhwIjoxNjMyMjk2MTYzLCJpYXQiOjE2MzIyOTQzNjN9.qB0P93BaW2FFG5INfnMdW2JyQZdD5FL7_5FPl4hERyo")
                        .addHeader("API_Key", "3C25F265-F86D-419D-9A04-EA74A503C197")
                        .addHeader("Secret_Key", "PVmMSclp834KBIUa9O-XxpBsDJhsi1dsds74CiGaoo5")
                        .build()
                    chain.proceed(newRequest)
                }.build()
            } else {
                client = OkHttpClient.Builder().addInterceptor(interceptor)
                    .addInterceptor(chuckerInterceptor).addInterceptor { chain ->
                    val newRequest: Request = chain.request().newBuilder()

                        .addHeader("Authorization", "Bearer " + tokenId)
                        //.addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IiIsIm5iZiI6MTYzMjM3Mjc4NSwiZXhwIjoxNjMyMzc0NTg1LCJpYXQiOjE2MzIzNzI3ODV9.ycuw_il0zfanVQoI2xLt4QZ54_HcDTKFIamJSLxb1iM")
                        .addHeader("API_Key", "3C25F265-F86D-419D-9A04-EA74A503C197")
                        .addHeader("Secret_Key", "PVmMSclp834KBIUa9O-XxpBsDJhsi1dsds74CiGaoo5")
                        .build()
                    chain.proceed(newRequest)
                }.build()
                Log.e("TOKENIDFROMAPI=",
                    sharedPrefData.getSharedPreferenceData(Constants.TOKENIDPREF, Constants.TOKENID).toString())
            }

            if (Constants.MainUrlChanged.urlChanged.equals("1")) {
                var Url = "${GlobalMethods.getServerIp(DemoApp.appContext!!)}/"
                Log.e("HPPAY", "NEWHPPAY" + Url)
                retrofit = Retrofit.Builder()
                    .baseUrl(Url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            } else {
                Log.e("HPPAY", "OLDHPPAY")
                var Url = Utils.MAIN_URL
                retrofit = Retrofit.Builder()
                    .baseUrl(Url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit.create(APIService::class.java)

        }
}