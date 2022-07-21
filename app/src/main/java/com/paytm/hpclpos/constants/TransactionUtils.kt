package com.paytm.hpclpos.constants

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationErrorResponse
import com.paytm.hpclpos.posterminal.base.DemoApp
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class TransactionUtils {
    companion object {
        private val PREFS = "cposprefs"
        private val BATCH_FORMAT = "%03d"

        fun incrementBatch(context: Context, batchName: String?): String {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            var batch = pref.getInt(batchName, 1)
            batch++
            if (batch == 999) {
                batch = 1
            }
            val editor = pref.edit()
            editor.putInt(batchName, batch)
            editor.apply()
            return String.format(BATCH_FORMAT, batch)
        }

        fun getCurrentBatch(context: Context, batchName: String?): String {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val currentBatchNumber = pref.getInt(batchName, 1)
            return String.format(BATCH_FORMAT, currentBatchNumber)
        }

        fun setBatchNumber(context: Context, batchName: String?, batchNumber: String) {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val editor = pref.edit()
            editor.putInt(batchName, batchNumber.toInt())
            editor.apply()
        }

        fun isTerminalRegistered(context: Context, prefName: String): Boolean {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val currentBatchNumber = pref.getBoolean(prefName, false)
            return currentBatchNumber
        }

        fun setTerminalRegistrationStatus(context: Context, prefName: String) {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val editor = pref.edit()
            editor.putBoolean(prefName, true)
            editor.apply()
        }

        fun setTerminalPin(context: Context, terminalPin: String, prefName: String) {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val editor = pref.edit()
            editor.putString(prefName, terminalPin)
            editor.apply()
        }

        fun getTerminalPin(context: Context, prefName: String): String {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            return pref.getString(prefName, "")!!
        }

        fun handleExceptions(t: Throwable): String {
            when (t) {
                is UnknownHostException -> {
                    return if (NetworkUtil.checkNetworkStatus(DemoApp.appContext!!))
                    { "Please enter a valid Server Ip" } else { "Please check Your Internet Connection" }
                }
                else -> { return t.localizedMessage?.toString()!! }
            }
        }

        fun convertErrorBody(errorBody: ResponseBody?) : RegistrationErrorResponse? {
            val gson: Gson = GsonBuilder().create()
            return try {
                errorBody?.let { gson.fromJson(errorBody.string(), RegistrationErrorResponse::class.java)  }
            } catch (e: IOException) { null }
        }

        fun<T> getStringFromError(registrationErrorResponse: RegistrationErrorResponse?, response: Response<T>) : String {
            return if (registrationErrorResponse != null) {
                HttpError(registrationErrorResponse.Status_Code.toString(), registrationErrorResponse.Message.toString()).toString()
            } else {
                HttpError(response.code().toString(), response.message()).toString()
            }
        }
    }
}