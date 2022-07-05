package com.paytm.hpclpos.ui.displayparameters

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.TransactionUtils


class DisplayParameterViewModel : ViewModel() {

    companion object {
        fun getDisplayParameterData(context: Context): HashMap<String, String> {
            val appRepository = AppRepository(context)
            val merchant = appRepository.getMerchantDetails()
            val hashMap = HashMap<String, String>()
            hashMap.put("Merchant Name", merchant.MerchantName.toString())
            hashMap.put("Merchant City", merchant.MerchantAddress.toString())
            hashMap.put("Merchant Id", merchant.MerchantId.toString())
            hashMap.put("Terminal Id", merchant.TerminalId.toString())
            hashMap.put("Receipt Header 1", merchant.Header1.toString())
            hashMap.put("Receipt Header 2", merchant.Header2.toString())
            hashMap.put("Footer 1", merchant.Footer1.toString())
            hashMap.put("Footer 2", merchant.Footer2.toString())
            hashMap.put("Batch size", merchant.BatchSize.toString())
            hashMap.put("Batch Sale Limit", merchant.BatchSaleLimit.toString())
            hashMap.put("Batch Reload Limit", merchant.BatchReloadLimit.toString())
            return hashMap
        }

        fun getTerminalSettingData(context: Context) : HashMap<String,String> {
            val hashMap = LinkedHashMap<String, String>()
            hashMap.put("TERMINAL ID", GlobalMethods.getTerminalId(context).toString())
            hashMap.put(context.getString(R.string.server_ip), GlobalMethods.getServerIp(context))
            hashMap.put(context.getString(R.string.server_port), " ")
            hashMap.put(context.getString(R.string.second_server_ip), GlobalMethods.getSeocndServerIp(context))
            hashMap.put(context.getString(R.string.second_server_port), " ")
            hashMap.put(context.getString(R.string.communication_timeout_sec), " ")
            hashMap.put(context.getString(R.string.apn), " ")
            hashMap.put(context.getString(R.string.userName), " ")
            hashMap.put(context.getString(R.string.password), " ")
            hashMap.put(context.getString(R.string.batch_id),TransactionUtils.getCurrentBatch(context,Constants.BATCH))
            hashMap.put(context.getString(R.string.transaction_id), GlobalMethods.displayTransId(context))
            return hashMap
        }

        fun getAboutFragmentDetails(context: Context) : HashMap<String,String> {
            val hashMap = LinkedHashMap<String, String>()
            hashMap.put("TERMINAL ID", GlobalMethods.getTerminalId(context).toString())
            hashMap.put("Software Version", " ")
            hashMap.put("Build Number"," ")
            hashMap.put("Build type", "release")
            hashMap.put("Build Date", " ")
            hashMap.put("Rom Version", "")
            hashMap.put("Firmware Version", Build.VERSION.SDK_INT.toString())
            hashMap.put("Service Version", " ")
            hashMap.put("Model Name", Build.MODEL)
            hashMap.put("Serial Number", Build.SERIAL)
            hashMap.put("OS Version", Build.VERSION.SDK_INT.toString())
            hashMap.put("Sim status", getSimStatus(context))
            return hashMap
        }

        fun getSimStatus(context: Context) : String {
            val telMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            val simState = telMgr!!.simState
            var status : String? = null
            when (simState) {
                TelephonyManager.SIM_STATE_ABSENT -> { status = "absent"}
                TelephonyManager.SIM_STATE_NETWORK_LOCKED -> { status = "network locked" }
                TelephonyManager.SIM_STATE_PIN_REQUIRED -> { status = "pin required" }
                TelephonyManager.SIM_STATE_PUK_REQUIRED -> { status = "puk required" }
                TelephonyManager.SIM_STATE_READY -> { status = "ready" }
                TelephonyManager.SIM_STATE_UNKNOWN -> { status = "unknown" }
            }
            return status!!
        }
    }
}