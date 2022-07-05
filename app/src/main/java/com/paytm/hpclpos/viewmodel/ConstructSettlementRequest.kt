package com.paytm.hpclpos.viewmodel

import android.content.Context
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.batchsettlement.BatchSettlementRequest
import com.paytm.hpclpos.livedatamodels.cardfee.CardFeeRequest
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPRequest
import com.paytm.hpclpos.models.SettlementTotals

class ConstructSettlementRequest(val context: Context) {

    val date = DateUtils.getCurrentDateTime()
    fun getSettlementRequest(type: String): BatchSettlementRequest {
        val batchId = TransactionUtils.getCurrentBatch(context, Constants.BATCH)
        val objTranscationsForBatchSettlement = SettlementTotals(context).calculateSettlement(type)
        val batchSettlementRequest = BatchSettlementRequest(Constants.UserId, Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context), GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),batchId.toInt(),objTranscationsForBatchSettlement,"123456")
        return batchSettlementRequest
    }

    fun getCardFeeRequest(formNum: String,noOfCards: Int,invoiceAmount: Int) : CardFeeRequest {
        val batchId = TransactionUtils.getCurrentBatch(context, Constants.BATCH)
        val cardFeeRequest = CardFeeRequest(Constants.UserId, Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context), GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),formNum ,batchId.toInt(), noOfCards, invoiceAmount,
            SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString(),
            GlobalMethods.getTransactionId(context)!!.toInt().toString(), date, 1, "123456"
        )
        return cardFeeRequest
    }

    fun getOtpRequest(mobileNumber: String) : GenerateOTPRequest {
       return GenerateOTPRequest(
            "string",
            Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context),
            GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),
            mobileNumber,
            1,
            "123456"
        )
    }
}