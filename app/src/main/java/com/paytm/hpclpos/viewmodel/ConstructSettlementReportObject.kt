package com.paytm.hpclpos.viewmodel

import android.content.Context
import android.util.Log
import com.example.apphpcldb.entity.repository.AppRepository
import com.google.gson.Gson
import com.paytm.hpclpos.livedatamodels.batchsettlement.BatchSettlementRequest
import com.paytm.hpclpos.models.SettlementTotals
import com.paytm.hpclpos.roomDatabase.entity.SettlementReportInfo

class ConstructSettlementReportObject(val context: Context) {

    fun constructSettlementReportObj(batchSettlementRequest: BatchSettlementRequest) {
        val settlementReportInfo = SettlementReportInfo()
        val gson = Gson()
        val jsonCartList = gson.toJson(batchSettlementRequest.objTranscationsForBatchSettlement)
        val appRepository = AppRepository(context)
        val productWiseAmountSum =
            gson.toJson(SettlementTotals(context).getProductSumForSettlement())
        Log.d("JsonSettlementPrintList", jsonCartList)
        settlementReportInfo.objTranscationsForBatchSettlement = jsonCartList
        settlementReportInfo.productWiseAmountSum = productWiseAmountSum
        appRepository.insertSettlementReportData(settlementReportInfo)
    }
}