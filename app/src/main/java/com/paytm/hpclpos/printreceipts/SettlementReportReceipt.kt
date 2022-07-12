package com.paytm.hpclpos.printreceipts

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.batchsettlement.BatchSettlementRequest
import com.paytm.hpclpos.posterminal.util.PrintUtils
import kotlin.collections.ArrayList

class SettlementReportReceipt(val context: Context, val activity: Activity) {

    val date = DateUtils.getCurrentDateTime()
    val batchNum = TransactionUtils.getCurrentBatch(context, Constants.BATCH)
    fun displayReceipt(printStatusListener: PrintStatusListener,batchSettltReq: BatchSettlementRequest) {
        val recommendedWidthE500 = 576
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_print_receipt,null)
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
        val appRepository = AppRepository(context)
        val arrayListTrans = appRepository.getTransactionsForBatchNUmber(batchNum.toInt()-1)
        PrintUtils.getSettlementHeader(context, receiptFieldList,batchNum.toInt()-1)
            if (!arrayListTrans.isEmpty()) {
                arrayListTrans.forEach {
                   val transId = SaleTransactionDetails.getSaleIdByName(it!!.TransactionType)
                    addReceiptHeader(receiptFieldList,
                        ReceiptDataFieldEntity(PrintUtils.gap, true, true, 17))
                    if(transId == SaleTransactionDetails.CCMSSALE.saleType ||
                       transId == SaleTransactionDetails.CARDSALE.saleType ||
                       transId == SaleTransactionDetails.DEALER_CREDIT_SALE.saleType ||
                       transId == SaleTransactionDetails.CREDIT_TXN.saleType ||
                       transId == SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleType) {
                       getHeadersForSettlement(receiptFieldList,it.transaction_Id!!)
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(it.TransactionType,true,true,18))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("PRODUCT ","DIESEL"))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT ",it.transaction_Amount))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE "," "))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SCHEMEA",it.productType))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(if (it.cardNumber!=null) "CRD#" else "CTRL CRD NUM",
                           if (it.cardNumber!=null) it.cardNumber else it.controlCardNum ))

                   } else if (transId == SaleTransactionDetails.CASH_RELOAD.saleType ||
                       transId == SaleTransactionDetails.CASH_RELOAD.saleType ||
                       transId == SaleTransactionDetails.CASH_RELOAD.saleType ) {
                       getHeadersForSettlement(receiptFieldList,it.transaction_Id!!)
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(it.TransactionType,true,true,18))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SERVICE ",SaleTransactionDetails.getSaleNameById(it.TransactionType!!.toInt())))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT ",it.transaction_Amount))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE "," "))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SCHEMEA",it.productType))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(if (it.cardNumber!=null) "CRD#" else "CTRL CRD NUM",
                           if (it.cardNumber!=null) it.cardNumber else it.controlCardNum ))

                   } else if (transId == SaleTransactionDetails.DRIVER_LOYAL.saleType) {
                       getHeadersForSettlement(receiptFieldList,it.transaction_Id!!)
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(it.TransactionType,true,true,18))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("EARNED PTS"," "))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BAL PTS",it.numOfCards))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SCHEMA ",it.TransactionType))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CRD",it.cardNumber))

                   } else if (transId == SaleTransactionDetails.DRIVER_LOYAL.saleType) {
                    getHeadersForSettlement(receiptFieldList,it.transaction_Id!!)
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(it.TransactionType,true,true,18))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("EARNED PTS"," "))
                    addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BAL PTS",it.numOfCards))
                    addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("SCHEMA ",it.TransactionType))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(if (it.cardNumber!=null) "CRD#" else "CTRL CRD NUM",
                           if (it.cardNumber!=null) it.cardNumber else it.controlCardNum ))

                   } else if (transId == SaleTransactionDetails.CARD_FEE_PAYMENT.saleType) {
                       getHeadersForSettlement(receiptFieldList,it.transaction_Id!!)
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(it.TransactionType,true,true,18))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("FORM NO",it.formNum))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CARD COUNT",it.numOfCards))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT ",it.transaction_Amount))
                   } else if (transId == SaleTransactionDetails.CCMS_CASHRECHARGE.saleType ||
                       transId == SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleType ||
                       transId == SaleTransactionDetails.CCMS_NEFTRECHARGE.saleType) {
                       getHeadersForSettlement(receiptFieldList,it.transaction_Id!!)
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(it.TransactionType,true,true,18))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT ",it.transaction_Amount))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BALANCE "," "))
                       addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(if (it.cardNumber!=null) "CRD#" else "CTRL CRD NUM",
                           if (it.cardNumber!=null) it.cardNumber else it.controlCardNum ))
                   }
               }
            } else {
                addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Batch Empty"))
            }

        printBatchSummary(receiptFieldList,batchSettltReq)
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }

    fun printBatchSummary(receiptFieldList: ArrayList<ReceiptDataFieldEntity>,batchSettltReq: BatchSettlementRequest) {
        batchSummaryHeaders(receiptFieldList)
        if(batchSettltReq.objTranscationsForBatchSettlement!!.isEmpty()) {
            addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Batch Empty"))
        } else {
            batchSettltReq.objTranscationsForBatchSettlement!!.forEach {
                addReceiptHeader(receiptFieldList,ReceiptDataFieldEntity(PrintUtils.gap))
                addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TYPE", SaleTransactionDetails.getSaleNameById(it.tranType!!.toInt())))
                addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("COUNT", it.transCount.toString()))
                addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("AMOUNT", it.totalAmount.toString()))
            }
        }
    }

    private fun batchSummaryHeaders(receiptFieldList: ArrayList<ReceiptDataFieldEntity>) {
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH SUMMARY",true,true,18))
        val appRepository = AppRepository(context)
        val merchantDetails = appRepository.getMerchantDetails()
        addReceiptHeader(receiptFieldList,
            ReceiptDataFieldEntity(merchantDetails.Header1, true, true, 16))
        addReceiptHeader(receiptFieldList,
            ReceiptDataFieldEntity(merchantDetails.Header2, true, true, 16))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TID", GlobalMethods.getTerminalId(context).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("BATCH", (batchNum.toInt()-1).toString()))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(date.substring(0,10)
            ,date.substring(10,date.length)))
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }

    fun getHeadersForSettlement(receiptFieldList: ArrayList<ReceiptDataFieldEntity>,transId: String) {
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("DATE/TIME"
            ,date.substring(0,10) + date.substring(10,date.length)))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("OPERATOR", "1"))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("TXN", (batchNum.toInt() - 1).toString() + "/" + transId))
    }

    fun printNoDetailReport(printStatusListener: PrintStatusListener,batchSettltReq: BatchSettlementRequest) {
        val recommendedWidthE500 = 576
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_print_receipt,null)
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
        printBatchSummary(receiptFieldList,batchSettltReq)
        PrintUtils.getFooterData(context,receiptFieldList)
        PrintUtils.callPrinter(printViewContainer,activity,receiptFieldList,view,printStatusListener)
    }
}