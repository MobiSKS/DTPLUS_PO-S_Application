package com.paytm.hpclpos.activities.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.posterminal.base.DemoApp


class MyAlarm : BroadcastReceiver() {
    var settlementDialog: SettlementDialog? = null
    override fun onReceive(context: Context, intent: Intent) {
        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        Log.d("MyAlarmBelal", "Alarm just fired")
        Toast.makeText(context, "Alarm Invoked", Toast.LENGTH_SHORT).show()
        val intent = Intent("filter_string")
        intent.putExtra("settlement", "performSettlement")
        // put your all data using put extra
        LocalBroadcastManager.getInstance(DemoApp.appContext!!).sendBroadcast(intent)
    }
}