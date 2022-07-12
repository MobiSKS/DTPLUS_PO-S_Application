package com.paytm.hpclpos.PrintReceipts

import android.content.Context
import android.util.Log
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.Reload.ReloadRequest
import com.paytm.hpclpos.livedatamodels.balancetransfer.BalanceTransferRequest
import com.paytm.hpclpos.livedatamodels.cardfee.CardFeeRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeRequest
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class Store(val context: Context) {
    fun storeTransDetailsInDB(ccmsSaleRequest: CCMSSaleRequest,balanceAmount: String,rsp: String) {
        val db = AppRepository(context)
        val hpclTrasaction = HpclTransaction().apply {
            TransactionType =
                SaleTransactionDetails.getSaleNameById(ccmsSaleRequest.transType!!.toInt()).toString()
            batch_Id = ccmsSaleRequest.batchId
            cardName = GlobalMethods.getCardInfoEntity()?.cardHolderName.toString()
            expDate = GlobalMethods.getCardInfoEntity()?.expiredDate.toString()
            mobileNo = ccmsSaleRequest.mobileNo.toString()
            vehicleNumber =
                GlobalMethods.getCardInfoEntity()?.vehicleNumber.toString()
            transaction_Id = ccmsSaleRequest.invoiceId.toString()
            transaction_Date = ccmsSaleRequest.invoiceDate.toString()
            transaction_Amount = String.format("%.2f",ccmsSaleRequest.invoiceAmount)
            productType = ccmsSaleRequest.productId.toString()
            odometerNumber = ccmsSaleRequest.odometerReading.toString()
            cardType = GlobalMethods.getCardInfoEntity()?.cardType.toString()
            cardNumber = GlobalMethods.getCardInfoEntity()?.cardNo
            category = SaleTransactionDetails.getTransCategoryById(ccmsSaleRequest.transType!!.toInt())
            operatorId = db.getLgoinOperator(true).operatorId
            this.balanceAmount = balanceAmount
            tokenNumber = ccmsSaleRequest.dcsTokenNumber
            this.rsp = rsp
            Log.d("Store","Transaction Category $category")
        }
        db.insertTransaction(hpclTrasaction)
    }

    fun storeRechargeTransDetailsInDB(ccmsRechargeRequest: CCMSRechargeRequest) {
        val db = AppRepository(context)
        val hpclTrasaction = HpclTransaction().apply {
            TransactionType =
                SaleTransactionDetails.getSaleNameById(ccmsRechargeRequest.transType.toInt())
                    .toString()
            batch_Id = ccmsRechargeRequest.batchId
            cardName = GlobalMethods.getCardInfoEntity()?.cardHolderName.toString()
            expDate = GlobalMethods.getCardInfoEntity()?.expiredDate.toString()
            mobileNo = ccmsRechargeRequest.mobileNo
            vehicleNumber =
                GlobalMethods.getCardInfoEntity()?.vehicleNumber.toString()
            transaction_Id = ccmsRechargeRequest.invoiceId.toString()
            transaction_Date = ccmsRechargeRequest.invoiceDate
            transaction_Amount = String.format("%.2f",ccmsRechargeRequest.invoiceAmount)
            cardType = GlobalMethods.getCardInfoEntity()?.cardType.toString()
            UTRNum = ccmsRechargeRequest.mUtrNo
            micrCode = ccmsRechargeRequest.micr
            chequeNum = ccmsRechargeRequest.chequeNo
            cardNumber = GlobalMethods.getCardInfoEntity()!!.cardNo
            controlCardNum = ccmsRechargeRequest.ccn
            category = SaleTransactionDetails.getTransCategoryById(ccmsRechargeRequest.transType.toInt())
            operatorId = db.getLgoinOperator(true).operatorId
            Log.d("Store","Transaction Category $category")
        }
        db.insertTransaction(hpclTrasaction)
    }

    fun storeReloadTransDetailsInDB(reloadRequest: ReloadRequest) {
        val db = AppRepository(context)
        val hpclTrasaction = HpclTransaction().apply {
            TransactionType =
                SaleTransactionDetails.getSaleNameById(reloadRequest.transType!!.toInt())
                    .toString()
            batch_Id = reloadRequest.batchId
            cardName = GlobalMethods.getCardInfoEntity()?.cardHolderName.toString()
            expDate = GlobalMethods.getCardInfoEntity()?.expiredDate.toString()
            mobileNo = reloadRequest.mobileNo
            vehicleNumber =
                GlobalMethods.getCardInfoEntity()?.vehicleNumber.toString()
            transaction_Id = reloadRequest.invoiceId.toString()
            transaction_Date = reloadRequest.invoiceDate
            transaction_Amount = String.format("%.2f",reloadRequest.invoiceAmount)
            cardType = GlobalMethods.getCardInfoEntity()?.cardType.toString()
            micrCode = reloadRequest.micr
            chequeNum = reloadRequest.chequeNo
            category = SaleTransactionDetails.getTransCategoryById(reloadRequest.transType!!.toInt())
            cardNumber = GlobalMethods.getCardInfoEntity()!!.cardNo
            operatorId = db.getLgoinOperator(true).operatorId
            Log.d("Store","Transaction Category $category")
        }

        db.insertTransaction(hpclTrasaction)
    }

    fun storeCardFeeTransDetailsInDB(cardFeeRequest: CardFeeRequest) {
        val db = AppRepository(context)
        val hpclTrasaction = HpclTransaction().apply {
            transaction_Id = cardFeeRequest.invoiceId.toString()
            transaction_Date = cardFeeRequest.invoiceDate
            batch_Id = cardFeeRequest.batchId
            formNum = cardFeeRequest.formNo
            transaction_Amount = String.format("%.2f",cardFeeRequest.invoiceAmount)
            numOfCards = cardFeeRequest.noofCards.toString()
            operatorId = db.getLgoinOperator(true).operatorId
            TransactionType =
                SaleTransactionDetails.getSaleNameById(cardFeeRequest.transType!!.toInt()).toString()
        }
        db.insertTransaction(hpclTrasaction)
    }

    fun storeBalanceTransferDetailsInDB(balanceTransferRequest: BalanceTransferRequest) {
        val db = AppRepository(context)
        val hpclTrasaction = HpclTransaction().apply {
            cardName = GlobalMethods.getCardInfoEntity()?.cardHolderName.toString()
            expDate = GlobalMethods.getCardInfoEntity()?.expiredDate.toString()
            mobileNo = balanceTransferRequest.mobileNo
            vehicleNumber =
                GlobalMethods.getCardInfoEntity()?.vehicleNumber.toString()
            transaction_Id = balanceTransferRequest.invoiceId.toString()
            transaction_Date = balanceTransferRequest.invoiceDate
            batch_Id = balanceTransferRequest.batchId
            transaction_Amount = String.format("%.2f",balanceTransferRequest.invoiceAmount)
            operatorId = db.getLgoinOperator(true).operatorId
            TransactionType =
                SaleTransactionDetails.getSaleNameById(balanceTransferRequest.transType!!.toInt()).toString()
            cardType = GlobalMethods.getCardInfoEntity()?.cardType.toString()
            cardNumber = GlobalMethods.getCardInfoEntity()?.cardNo
        }
        db.insertTransaction(hpclTrasaction)
    }
}
