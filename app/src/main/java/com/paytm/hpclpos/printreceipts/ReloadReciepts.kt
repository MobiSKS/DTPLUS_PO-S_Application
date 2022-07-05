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
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.printer.PrintUtils
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class ReloadReciepts(val context: Context, val activity: Activity, val lastTransactionData: HpclTransaction) {

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
        val receiptFieldList: java.util.ArrayList<ReceiptDataFieldEntity> =
            java.util.ArrayList<ReceiptDataFieldEntity>()
        PrintUtils.getHeaderData(context,receiptFieldList,printViewContainer)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        if(lastTransactionData.mobileNo.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(lastTransactionData.cardName))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("VEH NO: ",lastTransactionData.vehicleNumber))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",lastTransactionData.cardNumber))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("EXP DATE:",lastTransactionData.expDate))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#:",lastTransactionData.mobileNo))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD#:",lastTransactionData.cardNumber))
        }
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE", lastTransactionData.TransactionType!!))
        if(lastTransactionData.TransactionType!!.equals(SaleTransactionDetails.CHEQUE_RELOAD.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CH# ", lastTransactionData.chequeNum))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MICR CODE", lastTransactionData.micrCode))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:",""))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" TXN NO  :"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" ", "${GlobalMethods.getTerminalId(context)}/${lastTransactionData.batch_Id}/${lastTransactionData.transaction_Id}"))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    fun displayRecieptMerchantCopy(printStatusListener: PrintStatusListener) {
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
        PrintUtils.getHeaderDataMerchantCopy(context,receiptFieldList,printViewContainer)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPERATOR",lastTransactionData.operatorId))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID", GlobalMethods.getTerminalId(context).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("ROC NO", lastTransactionData.transaction_Id))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH NO", lastTransactionData.batch_Id.toString()))

        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE", lastTransactionData.TransactionType!!))
        if(lastTransactionData.TransactionType!!.equals(SaleTransactionDetails.CHEQUE_RELOAD.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CH# ", lastTransactionData.chequeNum))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MICR CODE", lastTransactionData.micrCode))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT", lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:",""))
        if(!lastTransactionData.mobileNo.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#",lastTransactionData.mobileNo))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(lastTransactionData.cardName))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD#",lastTransactionData.cardNumber))
        PrintUtils.getFooterData(context, receiptFieldList)
        PrintUtils.callPrinter(printViewContainer, activity, receiptFieldList, view,printStatusListener)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}