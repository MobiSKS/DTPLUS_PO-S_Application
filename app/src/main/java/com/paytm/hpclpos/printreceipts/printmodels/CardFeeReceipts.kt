package com.paytm.hpclpos.printreceipts.printmodels

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.posterminal.util.PrintUtils
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class CardFeeReceipts(val context: Context, val activity: Activity, val lastTransactionData: HpclTransaction) {

    fun displayReceipt(printStatusListener: PrintStatusListener) {
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
        PrintUtils.getHeaderData(context,receiptFieldList,printViewContainer)
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.form_number),lastTransactionData.formNum))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.card_count),lastTransactionData.numOfCards))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.amount).uppercase(),lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TXN#"," "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" ", "${GlobalMethods.getTerminalId(context)}/${lastTransactionData.batch_Id}/${lastTransactionData.transaction_Id}"))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }

    fun displayReceiptMerchantCopy(printStatusListener: PrintStatusListener) {
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
        PrintUtils.getHeaderDataMerchantCopy(context,receiptFieldList,printViewContainer)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPEARTOR",lastTransactionData.operatorId))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID",GlobalMethods.getTerminalId(context).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("ROC NO",lastTransactionData.transaction_Id))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH NO: ",lastTransactionData.batch_Id.toString()))
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.form_number),lastTransactionData.formNum))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.card_count),lastTransactionData.numOfCards))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.amount).uppercase(),lastTransactionData.transaction_Amount))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }
}