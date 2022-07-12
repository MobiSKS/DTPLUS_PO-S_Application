package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.cardredoptions.CardInfoEntity
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.Data
import com.paytm.hpclpos.posterminal.util.PrintUtils
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity

class BalanceEnquiryReceipts(val context: Context, val activity: Activity,val cardInfoEntity: CardInfoEntity) {

    fun displayReceipt(data: Data,balanceEnquiryRequest: BalanceEnquiryRequest) {
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
        val date = DateUtils.getCurrentDateTimeForSettlement()
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" "))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME",date))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID",GlobalMethods.getTerminalId(context).toString()))
        if(balanceEnquiryRequest.mobileNo.isNullOrBlank()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(cardInfoEntity.cardHolderName))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",balanceEnquiryRequest.cardNo))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("EXP DATE:",cardInfoEntity.expiredDate))
        } else {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("MOBILE#:",balanceEnquiryRequest.mobileNo))
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD:",balanceEnquiryRequest.cardNo))
        }
        val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(SaleTransactionDetails.BALANCE_ENQUIRY.saleName)
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(transNameForPrintReceipt,"1".toFloat()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Monthly Limit",data.monthlyLimit.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Monthly Spent",data.monthlySpent.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Monthly Limit Bal",data.monthlyLimitBal.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Daily Limit",data.dailyLimit.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Daily Spent",data.dailySpent.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Daily Limit Bal",data.dailyLimitBal.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS Limit",data.cCMSLimit.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS Limit Bal",data.cCMSLimitBal.toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Card Balance",data.cardBalance.toString()))

        PrintUtils.getFooterData(context, receiptFieldList)
        PrintUtils.callPrinter(printViewContainer, activity, receiptFieldList, view,null)
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}