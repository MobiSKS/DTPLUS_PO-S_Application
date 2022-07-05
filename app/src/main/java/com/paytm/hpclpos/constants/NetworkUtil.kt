package com.paytm.hpclpos.constants

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {
    @JvmStatic
    fun checkNetworkStatus(context: Context): Boolean {
        var status = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (tempNetworkInfo in networkInfos) {
            if (tempNetworkInfo.isConnected) {
                status = true
                break
            }
        }
        return status
    }
}