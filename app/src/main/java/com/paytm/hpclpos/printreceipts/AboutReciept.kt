package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.posterminal.util.PrintUtils
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.ui.displayparameters.DisplayParameterViewModel

class AboutReciept(val activity: Activity) {

    fun printReceipt() {
        val recommendedWidthE500 = 576
        val view: View = LayoutInflater.from(activity).inflate(R.layout.activity_print_receipt,null);
        val printViewContainer = view.findViewById<View>(R.id.relativeLayout)
        var layoutParams : ViewGroup.LayoutParams? = printViewContainer.layoutParams
        if (layoutParams == null) {
            layoutParams = ViewGroup.LayoutParams(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
        }
        layoutParams.width = recommendedWidthE500
        printViewContainer.layoutParams = layoutParams
        val receiptFieldList: ArrayList<ReceiptDataFieldEntity> = ArrayList()
        val date = DateUtils.getCurrentDateTime()
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME"
            ,date.substring(0,10) + date.substring(10,date.length)))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID", GlobalMethods.getTerminalId(activity).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Build Version", "1"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("App Version","1.0.0"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Build Type","release"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Build Date"," "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Rom "," "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Software Ver: ",""))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Model Name : ",Build.MODEL))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Serial Number : ",Build.SERIAL))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OS Version :", Build.VERSION.SDK_INT.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Sim status :",DisplayParameterViewModel.getSimStatus(activity)))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,null)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}