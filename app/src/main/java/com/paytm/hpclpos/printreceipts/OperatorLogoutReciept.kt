package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.posterminal.printer.PrintUtils
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity

class OperatorLogoutReciept(val activity: Activity) {

    fun printReceipt(operatorId: String,loginDate: String, loginTime: String) {
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
        operatorSummaryHeaders(receiptFieldList)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
         addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPERATOR",operatorId))
         addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID", GlobalMethods.getTerminalId(activity).toString()))
         val batchId = TransactionUtils.getCurrentBatch(activity, Constants.BATCH)
         addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH NO", batchId))
         val date = DateUtils.getCurrentDateTime()
         addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME"
            ,date.substring(0,10) + date.substring(10,date.length)))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("LOGIN", " $loginDate $loginTime"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(PrintUtils.gap,true,true))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(PrintUtils.gap,true,true))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(Gravity.CENTER,"SOFTWARE VER: "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(PrintUtils.gap,true,true))
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,null)
    }

    private fun operatorSummaryHeaders(receiptFieldList: ArrayList<ReceiptDataFieldEntity>) {
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPERATOR SUMMARY",true,true))
        val appRepository = AppRepository(activity)
        val merchantDetails = appRepository.getMerchantDetails()
        addReceiptHeader(receiptFieldList,
            ReceiptDataFieldEntity(merchantDetails.Header1, true, true, 16))
        addReceiptHeader(receiptFieldList,
            ReceiptDataFieldEntity(merchantDetails.Header2, true, true, 16))
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}