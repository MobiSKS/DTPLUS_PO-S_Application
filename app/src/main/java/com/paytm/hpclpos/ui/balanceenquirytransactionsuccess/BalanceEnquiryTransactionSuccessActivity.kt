package com.paytm.hpclpos.ui.balanceenquirytransactionsuccess

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.ActivityBalanceEnquiryTransactionSucessBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.Data
import com.paytm.hpclpos.printreceipts.BalanceEnquiryReceipts
import com.paytm.hpclpos.printreceipts.CCMSBalanceEnquiryReceipts
import com.paytm.hpclpos.printreceipts.printmodels.KeyValueItem
import com.paytm.hpclpos.printreceipts.printmodels.NameItemView
import com.paytm.hpclpos.printreceipts.printmodels.ReceiptDataFieldEntity
import com.paytm.hpclpos.ui.transactionsuccess.TransactionSucessActivity

class BalanceEnquiryTransactionSuccessActivity : TransactionSucessActivity() {

    lateinit var binding1: ActivityBalanceEnquiryTransactionSucessBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding1 = DataBindingUtil.inflate(inflater, R.layout.activity_balance_enquiry_transaction_sucess, container, false)
        binding1.lLgoToMain.setOnClickListener {
            transResultTimer.cancel()
            goToMainActivity()
        }
        showGifDialog()
        val transactionStatusBalanceEnquiry = arguments?.getSerializable("transactionStatus")
        val transactionRequest = arguments?.getSerializable("transactionRequest")
        var data: Data? = null
        var balanceEnquiryRequest : BalanceEnquiryRequest? = null
        if (transactionStatusBalanceEnquiry != null) {
            data = transactionStatusBalanceEnquiry as Data
        }

        if (transactionRequest != null) {
            balanceEnquiryRequest = transactionRequest as BalanceEnquiryRequest
        }
        var data1: com.paytm.hpclpos.livedatamodels.CCMSBalance.Data? = null
        val transactionStatusCCMSBalanceEnquiry = (requireArguments().getSerializable("ccmsBalanceenquirytransactionStatus"))
        if(transactionStatusCCMSBalanceEnquiry !=null) {
            data1 = transactionStatusCCMSBalanceEnquiry as com.paytm.hpclpos.livedatamodels.CCMSBalance.Data
        }
        Log.d("Output Data", data?.cardBalance.toString())
        Handler().postDelayed({ dialog1!!.dismiss() }, 3000)
        checkPrintReceipts(data,data1,balanceEnquiryRequest)
        handleOnBackPressed()
        return binding1.root
    }

    private fun checkPrintReceipts(data: Data?,data1: com.paytm.hpclpos.livedatamodels.CCMSBalance.Data?
                                   ,balanceEnquiryRequest: BalanceEnquiryRequest?) {
        when (GlobalMethods.getSaleType()) {
            SaleTransactionDetails.BALANCE_ENQUIRY.saleName -> {
                renderDataBalanceEnquiry(data!!)
                BalanceEnquiryReceipts(requireContext(),requireActivity(),GlobalMethods.getCardInfoEntity()!!).displayReceipt(data,balanceEnquiryRequest!!)
            }

            SaleTransactionDetails.CCMS_BALANCE.saleName -> {
                renderDataCCMSBalanceEnquiry(data1!!)
                CCMSBalanceEnquiryReceipts(requireContext(),requireActivity()).displayReceipt(data1)
            }
        }
    }

    fun renderDataBalanceEnquiry(data : Data) {
        val receiptFieldList: ArrayList<ReceiptDataFieldEntity> = ArrayList()
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Monthly Limit",data.monthlyLimit.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Monthly Spent",data.monthlySpent.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Monthly Limit Bal",data.monthlyLimitBal.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Daily Limit",data.dailyLimit.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Daily Spent",data.dailySpent.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Daily Limit Bal",data.dailyLimitBal.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS Limit",data.cCMSLimit.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS Limit Bal",data.cCMSLimitBal.toString(),15))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Card Balance",data.cardBalance.toString(),15))
        addViewToParent(receiptFieldList)
    }

    fun renderDataCCMSBalanceEnquiry(data : com.paytm.hpclpos.livedatamodels.CCMSBalance.Data) {
        val receiptFieldList: ArrayList<ReceiptDataFieldEntity> = ArrayList()
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("CCMS Limit Bal",data.ccmsLimitBal.toString(),20))
        addReceiptHeader(receiptFieldList, ReceiptDataFieldEntity("Loyalty Balance",data.loyaltyBalance.toString(),20))
        addViewToParent(receiptFieldList)
    }

    fun addViewToParent(receiptFieldList: ArrayList<ReceiptDataFieldEntity>) {
        for (dataFieldEntity in receiptFieldList) {
            if (dataFieldEntity.header != null) {
                val itemView: NameItemView = dataFieldEntity.createView(binding1.linearLayout)
                itemView.render(dataFieldEntity)
                binding1.linearLayout.addView(itemView.rootView)
            } else if (dataFieldEntity.key != null) {
                val itemView: KeyValueItem = dataFieldEntity.createBoldView(binding1.linearLayout)
                itemView.render(dataFieldEntity)
                binding1.linearLayout.addView(itemView.rootView)
            }
        }
    }

    protected fun addReceiptHeader(receiptFieldList: ArrayList<ReceiptDataFieldEntity>, receiptDataFieldEntity: ReceiptDataFieldEntity) {
        receiptFieldList.add(receiptDataFieldEntity)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goToMainActivity()
                }
            })
    }
}