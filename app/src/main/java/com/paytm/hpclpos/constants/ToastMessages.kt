package com.paytm.hpclpos.constants

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.paytm.hpclpos.R

object ToastMessages {
    @JvmStatic
    fun noInternetConnectionToast(context: Context) {
        customMsgToastShort(context,context.resources.getString(R.string.nointernetconnection))
    }

    @JvmStatic
    fun customMsgToast(context: Context?, toastMsg: String?) {
        val myView: View = LayoutInflater.from(context).inflate(R.layout.toast_custom, null)
        val text = myView.findViewById<View>(R.id.txtvw) as TextView
        text.text = toastMsg
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(myView)
        toast.show()
    }

    @JvmStatic
    fun customMsgToastShort(context: Context?, toastMsg: String?) {
        val myView: View = LayoutInflater.from(context).inflate(R.layout.toast_custom, null)
        val text = myView.findViewById<View>(R.id.txtvw) as TextView
        text.text = toastMsg
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.setView(myView)
        toast.show()
    }
}