package com.paytm.hpclpos.models

import android.content.Context
import android.util.Log
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.batchsettlement.ObjTranscationsForBatchSettlement
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction
import java.math.BigDecimal

class SettlementTotals(val context: Context) {

    fun calculateSettlement(type: String) : ArrayList<ObjTranscationsForBatchSettlement> {
        val appRepository = AppRepository(context)
        // Getting TransType Locally
        val allTransList = getAllTransList(type)
        val arrayList : ArrayList<ObjTranscationsForBatchSettlement> = ArrayList()
        val batchNum = TransactionUtils.getCurrentBatch(context,Constants.BATCH)
        for (trans in allTransList) {
            val getTrans = appRepository.getActiveTransactions(trans, batchNum.toInt())
            if (!getTrans!!.isEmpty())
                arrayList.add(getSum(getTrans))
        }
        return arrayList
    }

    fun getProductSumForSettlement() : ArrayList<HashMap<String,String>> {
        val appRepository = AppRepository(context)
        val batchNum = TransactionUtils.getCurrentBatch(context,Constants.BATCH)
        val allProdList = Constants.getAllProductsList()
        val arrayList : ArrayList<HashMap<String,String>> = ArrayList()
        val hashMap : HashMap<String,String> = HashMap()
        allProdList.forEach {
            val getProd = appRepository.getTransactionsFromProductId(it,batchNum.toInt())
            if (!getProd!!.isEmpty()) {
                arrayList.add(getSumForProductId(getProd,it,hashMap))
            }
        }
        return arrayList
    }

    fun getAllTransList(type: String): ArrayList<String> {
        return Constants.getAllTransactionsList()
    }

    private fun getSum(activeCardSaleTrans: List<HpclTransaction?>?) : ObjTranscationsForBatchSettlement {
        var cardSaleTxnTotalSum : BigDecimal = BigDecimal.ZERO
        var cardSaleTxnTotalCount = 0
        var transType: String? = null
        for (trans in activeCardSaleTrans!!) {
            transType = trans!!.TransactionType.toString()
            cardSaleTxnTotalCount++
            cardSaleTxnTotalSum = cardSaleTxnTotalSum + trans.transaction_Amount!!.toBigDecimal()
            Log.d(trans.TransactionType,trans.transaction_Amount.toString())
        }
       val v = Math.floor(cardSaleTxnTotalSum.toDouble() * 100) / 100.0
       return ObjTranscationsForBatchSettlement(SaleTransactionDetails.getSaleIdByName(transType).toString(),cardSaleTxnTotalCount,
           v.toFloat())
    }

    private fun getSumForProductId(
        activeCardSaleTrans: List<HpclTransaction?>?, product: String, hashMap: HashMap<String, String>
    ): HashMap<String, String> {
        var cardSaleTxnTotalSum = 0f
        for (trans in activeCardSaleTrans!!) {
            cardSaleTxnTotalSum = cardSaleTxnTotalSum + trans!!.transaction_Amount!!.toFloat()
        }
        hashMap.put(product, cardSaleTxnTotalSum.toString())
        return hashMap
    }
}