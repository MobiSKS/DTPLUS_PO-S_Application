package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.livedatamodels.CCMSBalance.Data
import com.paytm.hpclpos.posterminal.util.PrintUtils
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity

class CCMSBalanceEnquiryReceipts(val context: Context, val activity: Activity) {

    fun displayReceipt(data: Data) {
        val date = DateUtils.getCurrentDateTime()
        val recommendedWidthE500 = 576
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_print_receipt,null);
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
        printViewContainer.findViewById<ImageView>(R.id.hpImage).setImageDrawable(context.getDrawable(
                R.drawable.hplogoprint))
        PrintUtils.getHeaderData(context,receiptFieldList,printViewContainer)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(date.substring(0,10)
                ,date.substring(10,date.length))
        )
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID", GlobalMethods.getTerminalId(context).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(PrintUtils.gap, true, true, 16))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CONTROL ID",GlobalMethods.getControlCardNumber()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS BAL(CCMS CASH)","1".toFloat()))
      //  addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE",GlobalMethods.getSaleType()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS BALANCE",data.ccmsLimitBal.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("LOYALTY BALANCE",data.loyaltyBalance.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(PrintUtils.gap, true, true, 16))
        PrintUtils.getFooterData(context, receiptFieldList)
        PrintUtils.callPrinter(printViewContainer, activity, receiptFieldList, view,null)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}