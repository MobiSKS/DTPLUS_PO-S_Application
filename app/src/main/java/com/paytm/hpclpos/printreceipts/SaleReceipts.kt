package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.printer.PrintUtils
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction
import java.math.RoundingMode
import java.text.DecimalFormat

class SaleReceipts(val context: Context,val activity: Activity,val lastTransactionData: HpclTransaction) {

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
        if(lastTransactionData.mobileNo.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(lastTransactionData.cardName))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("VEH NO: ",lastTransactionData.vehicleNumber))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD#:",lastTransactionData.cardNumber))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("EXP DATE:",lastTransactionData.expDate))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#:",lastTransactionData.mobileNo))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD#:",lastTransactionData.cardNumber))
        }
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("PRODUCT:", GlobalMethods.getProductType()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        calculateRspVolume(lastTransactionData,receiptFieldList)
        if(lastTransactionData.TransactionType.equals(SaleTransactionDetails.DEALER_CREDIT_SALE.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.token_number),lastTransactionData.tokenNumber))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:",lastTransactionData.balanceAmount))
        if(!lastTransactionData.odometerNumber.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("ODOMETER",lastTransactionData.odometerNumber))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" TXN NO  :"))
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
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("PRODUCT:", GlobalMethods.getProductType()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        calculateRspVolume(lastTransactionData,receiptFieldList)
        if(lastTransactionData.TransactionType.equals(SaleTransactionDetails.DEALER_CREDIT_SALE.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(context.getString(R.string.token_number),lastTransactionData.tokenNumber))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:",lastTransactionData.balanceAmount))
        if(!lastTransactionData.mobileNo.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#",lastTransactionData.mobileNo))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(lastTransactionData.cardName))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",lastTransactionData.cardNumber))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    private fun calculateRspVolume(lastTransactionData: HpclTransaction,receiptFieldList: ArrayList<ReceiptDataFieldEntity>) {
        if(lastTransactionData.rsp.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("RSP:","-"))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("VOLUME:","-"))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("RSP:", lastTransactionData.rsp))
            /*val volume = lastTransactionData.transaction_Amount!!.toFloat() / lastTransactionData.rsp!!.toFloat()
            val df = DecimalFormat("#.###")
            df.setRoundingMode(RoundingMode.CEILING)*/
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("VOLUME:", lastTransactionData.volume))
        }
    }
}