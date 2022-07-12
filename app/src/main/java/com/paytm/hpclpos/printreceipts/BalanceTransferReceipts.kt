package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.posterminal.util.PrintUtils
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class BalanceTransferReceipts(val context: Context, val activity: Activity, val lastTransactionData: HpclTransaction) {

    fun displayReceipt() {
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
        val receiptFieldList: ArrayList<ReceiptDataFieldEntity> = ArrayList<ReceiptDataFieldEntity>()
        printViewContainer.findViewById<ImageView>(R.id.hpImage).setImageDrawable(context.getDrawable(
            R.drawable.hplogoprint))
        addReceiptHeader(receiptFieldList,
            ReceiptDataFieldEntity(context.getString(R.string.customer_copy),true,true,16)
        )
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(lastTransactionData.cardName))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("VEH NO: ",lastTransactionData.vehicleNumber))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",lastTransactionData.cardNumber))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("EXP DATE:",lastTransactionData.expDate))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
      //  addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE", GlobalMethods.getSaleType()))

        PrintUtils.getFooterData(context, receiptFieldList)
        PrintUtils.callPrinter(printViewContainer, activity, receiptFieldList, view, null)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}