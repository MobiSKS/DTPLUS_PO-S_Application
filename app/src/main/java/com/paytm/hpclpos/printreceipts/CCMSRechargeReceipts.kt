package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.printer.PrintUtils
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class CCMSRechargeReceipts(val context: Context, val activity: Activity, val lastTransactionData: HpclTransaction) {

    val SERVICE = "SERVICE"
    val UTR = "UTR#"
    val MICR_CODE = "MICR CODE"
    val CHEQUE_NUM = "CHEQUE NUM"
    val CHEQUE_RECHARGE = "CHEQUE RECHARGE"

    fun displayReceiptCarded(printStatusListener: PrintStatusListener) {
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
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#:", lastTransactionData.mobileNo))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD#:", lastTransactionData.cardNumber))
        }
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        if (lastTransactionData.TransactionType!!.equals(SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE", lastTransactionData.TransactionType))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CH# ", lastTransactionData.chequeNum))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MICR CODE", lastTransactionData.micrCode))
        } else if (lastTransactionData.TransactionType!!.equals(SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE", lastTransactionData.TransactionType))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$UTR : ",lastTransactionData.UTRNum as String))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE", lastTransactionData.TransactionType))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:",""))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" TXN NO  :"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" ", "${GlobalMethods.getTerminalId(context)}/${lastTransactionData.batch_Id}/${lastTransactionData.transaction_Id}"))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    fun displayReceiptCardedMerchantCopy(printStatusListener: PrintStatusListener) {
        var bitmap : Bitmap? = null
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
        PrintUtils.getHeaderDataMerchantCopy(context,receiptFieldList,view)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPEARTOR",lastTransactionData.operatorId))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID",GlobalMethods.getTerminalId(context).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("ROC NO",lastTransactionData.transaction_Id))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH NO: ",lastTransactionData.batch_Id.toString()))
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        if (lastTransactionData.TransactionType!!.equals(SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",CHEQUE_RECHARGE))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$CHEQUE_NUM : ",lastTransactionData.chequeNum as String))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$MICR_CODE : ",lastTransactionData.micrCode as String))
        } else if(lastTransactionData.TransactionType!!.equals(SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName)) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt ,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",lastTransactionData.TransactionType)
            )
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$UTR# : ",lastTransactionData.UTRNum)
            )
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt ,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",lastTransactionData.TransactionType)
            )
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:"," "))
        if(!lastTransactionData.mobileNo.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#",lastTransactionData.mobileNo))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",lastTransactionData.cardNumber))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(lastTransactionData.cardName))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",lastTransactionData.cardNumber))
        }
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun displayReceiptNonCarded(printStatusListener: PrintStatusListener) {
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
        PrintUtils.getHeaderData(context,receiptFieldList, printViewContainer)
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CTRL ID:",lastTransactionData.controlCardNum))
        if (!(lastTransactionData.chequeNum.isNullOrBlank() && lastTransactionData.micrCode.isNullOrBlank())) {

            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt ,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",CHEQUE_RECHARGE))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$CHEQUE_NUM : ",lastTransactionData.chequeNum as String))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$MICR_CODE : ",lastTransactionData.micrCode as String))
        } else if(!lastTransactionData.UTRNum.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",lastTransactionData.TransactionType))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$UTR# : ",lastTransactionData.UTRNum))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",lastTransactionData.TransactionType))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:"," "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TXN "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" ", "${GlobalMethods.getTerminalId(context)}/${lastTransactionData.batch_Id}/${lastTransactionData.transaction_Id}"))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    fun displayReceiptNonCardedMerchantCopy(printStatusListener: PrintStatusListener) {
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

        PrintUtils.getHeaderDataMerchantCopy(context,receiptFieldList,view)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPEARTOR",lastTransactionData.operatorId))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID",GlobalMethods.getTerminalId(context).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("ROC NO",lastTransactionData.transaction_Id))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH NO: ",lastTransactionData.batch_Id.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CTRL ID:",lastTransactionData.controlCardNum))
        val dateAndTime = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",dateAndTime))
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
        if (!(lastTransactionData.chequeNum.isNullOrBlank() && lastTransactionData.micrCode.isNullOrBlank())) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt ,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",CHEQUE_RECHARGE))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$CHEQUE_NUM : ",lastTransactionData.chequeNum as String))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$MICR_CODE : ",lastTransactionData.micrCode as String))
        } else if(!lastTransactionData.UTRNum.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$SERVICE : ",lastTransactionData.TransactionType))
            addReceiptHeader(receiptFieldList,
                ReceiptDataFieldEntity("$UTR# : ",lastTransactionData.UTRNum))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("$SERVICE : ",lastTransactionData.TransactionType))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        }
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT:", lastTransactionData.transaction_Amount))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE:"," "))
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }
}