package com.paytm.hpclpos.ui.transactionsuccess

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.databinding.ActivityTransactionSucessBinding
import com.paytm.hpclpos.enums.ProductDetails
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class TransactionSuccessViewModel(val context: Context) : ViewModel() {

    fun setTransactionResultUi(binding: ActivityTransactionSucessBinding, lastTransactionData: HpclTransaction) {
        val receiptFieldList: ArrayList<ReceiptDataFieldEntity> = ArrayList()
        when(lastTransactionData.TransactionType) {
            SaleTransactionDetails.BALANCE_ENQUIRY.saleName -> {
                val linearLayout = binding.linearLayout
                addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity(" ",
                        GlobalMethods.getTerminalId(context).toString()))
            }
        }
        if (lastTransactionData.cardType.equals(Constants.ICC) ||
                lastTransactionData.cardType.equals(Constants.MAG_STRIPE)) {
            binding.rlMobileNo.visibility = View.GONE
            binding.tvCardNumber.visibility = View.VISIBLE

            binding.rlCardNo.visibility = View.VISIBLE
            binding.tvCardNumber.text = lastTransactionData.cardNumber
        } else {
            binding.rlMobileNo.visibility = View.VISIBLE
            binding.tvMobileNo.text = GlobalMethods.getMobileNo()
            binding.tvCardNumber.visibility = View.GONE
            binding.rlCardNo.visibility = View.GONE
        }
        binding.tvAmount.text = GlobalMethods.getAmountViewModel()!!.amountDisplayText
        binding.tvBatchNo.text = TransactionUtils.getCurrentBatch(context , Constants.BATCH)
        binding.tvDateTime.text = lastTransactionData.transaction_Date
        binding.tvTrasactionType.text = lastTransactionData.TransactionType
       /* if(lastTransactionData.productType!=null) {
            binding.tvProduct.text =
                    ProductDetails.getProductNameById(lastTransactionData.productType!!.toInt())!!
                            .toString()
        }*/
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }
}