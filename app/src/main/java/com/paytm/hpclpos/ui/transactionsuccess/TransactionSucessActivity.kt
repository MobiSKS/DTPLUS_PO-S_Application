package com.paytm.hpclpos.ui.transactionsuccess

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.constants.GlobalMethods.Companion.setCardNo
import com.paytm.hpclpos.databinding.ActivityTransactionSucessBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.posterminal.util.PrintUtils
import com.paytm.hpclpos.printreceipts.BalanceTransferReceipts
import com.paytm.hpclpos.printreceipts.CCMSRechargeReceipts
import com.paytm.hpclpos.printreceipts.ReloadReciepts
import com.paytm.hpclpos.printreceipts.SaleReceipts
import com.paytm.hpclpos.printreceipts.printmodels.CardFeeReceipts
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction
import com.paytm.hpclpos.viewmodel.ConstructSettlementRequest
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

open class TransactionSucessActivity : BaseFragment() {
    var dialog1: Dialog? = null
    lateinit var binding: ActivityTransactionSucessBinding
    var bitmap : Bitmap? = null
    lateinit var settlementDialog: SettlementDialog

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_transaction_sucess, container, false)
        val appRepository = AppRepository(requireContext())
        val lastTransactionData = appRepository.getLastTransaction()
        val lLgoToMain = binding.root.findViewById<View>(R.id.lLgoToMain) as LinearLayout
        lLgoToMain.setOnClickListener {
            transResultTimer.cancel()
            goToMainActivity()
        }
        // For Mobile Transactions
        if (lastTransactionData!!.mobileNo.isNullOrBlank()) {
            binding.rlMobileNo.visibility = View.GONE
            binding.tvCardNumber.visibility = View.VISIBLE

            binding.rlCardNo.visibility = View.VISIBLE
            binding.tvCardNumber.text = lastTransactionData.cardNumber
        } else {
            // For Card Transactions
            binding.rlMobileNo.visibility = View.VISIBLE
            binding.tvMobileNo.text = lastTransactionData.mobileNo
            binding.tvCardNumber.visibility = View.VISIBLE
            binding.tvCardNumber.text = lastTransactionData.cardNumber
        }
        // For Non Carded Transactions control card number will be displayed
        if(!lastTransactionData.controlCardNum.isNullOrBlank()) {
            binding.tvCardNumber.visibility = View.VISIBLE
            binding.tvCardNumber.text = lastTransactionData.controlCardNum
        }
        binding.tvAmount.text = GlobalMethods.getAmountViewModel()!!.amountDisplayText
        binding.tvBatchNo.text = TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH)
        binding.tvDateTime.text = DateUtils.getFormatedDateTime(lastTransactionData.transaction_Date!!)
        binding.tvTrasactionType.text = lastTransactionData.TransactionType
        binding.tvTransactionId.text = lastTransactionData.transaction_Id
        if (lastTransactionData.TransactionType.equals(SaleTransactionDetails.CARD_FEE_PAYMENT.saleName)) {
            binding.tvCardNumber.visibility = View.GONE
            binding.formNumberLayout.visibility = View.VISIBLE
            binding.numberOfCardLayout.visibility = View.VISIBLE
            binding.numberOfCardEdittext.setText(lastTransactionData.numOfCards)
            binding.formNoEditText.setText(lastTransactionData.formNum)
            val transNameForPrintReceipt = PrintUtils().displayTransNameForPrintReciepts(lastTransactionData.TransactionType!!)
            binding.tvTrasactionType.text = transNameForPrintReceipt
        }
        checkPrintReceipts(lastTransactionData)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkPrintReceipts(lastTransactionData : HpclTransaction) {
        showGifDialog()
        when (GlobalMethods.getSaleType()) {
            SaleTransactionDetails.CARDSALE.saleName,
            SaleTransactionDetails.CCMSSALE.saleName,
            SaleTransactionDetails.CREDIT_TXN.saleName-> {
                SaleReceipts(requireContext(), requireActivity(), lastTransactionData).displayReceipt(printStatusListener)
            }
            SaleTransactionDetails.CCMS_CASHRECHARGE.saleName,
            SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName,
            SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName -> {
                if (!lastTransactionData.controlCardNum.isNullOrBlank()) {
                    CCMSRechargeReceipts(requireContext(), requireActivity(), lastTransactionData).displayReceiptNonCarded(printStatusListener)
                } else {
                    CCMSRechargeReceipts(requireContext(), requireActivity(), lastTransactionData).displayReceiptCarded(printStatusListener)
                }
            }
            SaleTransactionDetails.BALANCE_TRANSFER.saleName -> {
                    BalanceTransferReceipts(requireContext(),requireActivity(),lastTransactionData).displayReceipt()
            }

            SaleTransactionDetails.CARD_FEE_PAYMENT.saleName -> {
                CardFeeReceipts(requireContext(), requireActivity(), lastTransactionData).displayReceipt(printStatusListener)
            }

            SaleTransactionDetails.CASH_RELOAD.saleName,
            SaleTransactionDetails.CHEQUE_RELOAD.saleName,
            SaleTransactionDetails.CCMS_RELOAD.saleName -> {
                ReloadReciepts(requireContext(), requireActivity(), lastTransactionData).displayReceipt(printStatusListener)
            }
        }
    }

    protected fun showGifDialog() {
        dialog1 = Dialog(requireContext())
        dialog1!!.setContentView(R.layout.custom_print_gif_layout)
        val imageView = dialog1!!.findViewById<ImageView>(R.id.gifImageview)
        Glide.with(this).load(R.drawable.printergif).into(imageView)
        val window = dialog1!!.window
        dialog1!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        dialog1!!.show()
    }

    protected fun goToMainActivity() {
        setCardNo("")
        navController!!.navigate(R.id.action_main_fragment)
    }

    val printStatusListener = object : PrintStatusListener {
        override fun onSuccess() {
            val appRepository = AppRepository(requireContext())
            val lastTransactionData = appRepository.getLastTransaction()
            dialog1!!.dismiss()
            when(SaleTransactionDetails.getTransCategoryById(
                SaleTransactionDetails.getSaleIdByName(lastTransactionData!!.TransactionType))) {
                SaleTransactionDetails.CCMS_CASHRECHARGE.category,
                SaleTransactionDetails.CARDSALE.category,
                SaleTransactionDetails.CARD_FEE_PAYMENT.category,
                SaleTransactionDetails.CASH_RELOAD.category -> {
                    showDialogPrint("Remind","Click Ok to Print Merchant Copy")
                }
            }
        }

        override fun onError(error: Int) {
            dialog1!!.dismiss()
            DialogUtil.showDialog(requireContext(),"Printer Error","Unable to print, Terminal is Out of Paper",object :DialogUtil.OnClickListener {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onConfirm() {
                    val lastTransactionData = AppRepository(requireContext()).getLastTransaction()
                    checkPrintReceipts(lastTransactionData!!)
                }

                override fun onCancel() {
                    // Do nothing
                }
            })
        }
    }

    fun checkForBatchSettlement() {
        var sumOfAmount : Long = 0
        val appRepository = AppRepository(requireContext())
        val batchSaleLimit = appRepository.getMerchantDetails().BatchSaleLimit
        for(sale in Constants.getAllProductsList()) {
            val transactionList = appRepository.getActiveTransactions(sale,TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH).toInt())
            if (transactionList != null) {
                for (trans in transactionList) {
                    sumOfAmount += trans!!.transaction_Amount!!.toInt()
                }
            }
        }
        if(sumOfAmount.toDouble() >= batchSaleLimit!!.toDouble()) {
            settlementDialog = SettlementDialog(requireActivity())
            settlementDialog.setTitle(getString(R.string.title_settlement))
            settlementDialog.setProcessing()
            settlementDialog.show()
            batchSettlement()
        }
    }

    fun batchSettlement() {
        val viewModel: MerchantActivityViewModel =
            ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        val constructSettlementRequest = ConstructSettlementRequest(requireContext()).getSettlementRequest(Constants.SALE)
        viewModel.makeApiBatchSettlement(constructSettlementRequest)
        viewModel.getliveDataBatchSettlement().observe(this, {
            when (it) {
                is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.Error -> { ToastMessages.customMsgToast(requireContext(), it.error) }

                is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse -> {
                    if (it.success) {
                        if(it.internelStatusCode.equals(Constants.STATUS_SUCCESS)) {
                            // to start Invoice id by 1
                            GlobalMethods.decrementTransactionIdByOne(requireContext(), "000001")
                            ToastMessages.customMsgToast(requireContext(),
                                "Response Message ${it.internelStatusCode} Message ${it.message}")
                            TransactionUtils.incrementBatch(requireContext(),Constants.BATCH)
                            settlementDialog.dismiss()
                            Log.d("MerchantService", "Incremented to" + TransactionUtils.getCurrentBatch( requireContext(),
                                Constants.BATCH))
                        } else {
                            ToastMessages.customMsgToast(
                                requireContext(),
                                "Response Message ${it.internelStatusCode} Message ${it.message}")
                        }
                    } else {
                        ToastMessages.customMsgToast(
                            requireContext(),
                            "Response Message ${it.internelStatusCode} Message ${it.message}")
                    }
                }
            }
        })
    }

    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    val transResultTimer = object : CountDownTimer(10000, 1000) {

        // Callback function, fired on regular interval
        override fun onTick(millisUntilFinished: Long) {
            requireActivity().runOnUiThread({
                binding.backToMainMenu.setText("${getString(R.string.back_to_main_menu)} ${millisUntilFinished.toString()
                    .substring(0, millisUntilFinished.toString().length - 3)}")
            })
        }

        // Callback function, fired
        // when the time is up
        override fun onFinish() { requireActivity().runOnUiThread{ goToMainActivity() } }
    }

    fun printMerchantCopy() {
        val appRepository = AppRepository(requireActivity())
        val lastTransactionData = appRepository.getLastTransaction()
        when(SaleTransactionDetails.getTransCategoryById(
            SaleTransactionDetails.getSaleIdByName(lastTransactionData!!.TransactionType))) {
            SaleTransactionDetails.CCMS_CASHRECHARGE.category -> {
                if (lastTransactionData.controlCardNum.isNullOrBlank()) {
                    CCMSRechargeReceipts(requireContext(), requireActivity(), lastTransactionData)
                        .displayReceiptCardedMerchantCopy(printMerchantCopyListner)
                } else {
                    CCMSRechargeReceipts(requireContext(), requireActivity(), lastTransactionData)
                        .displayReceiptNonCardedMerchantCopy(printMerchantCopyListner)
                }
            }
            SaleTransactionDetails.CARDSALE.category -> {
                SaleReceipts(requireContext(),requireActivity(),lastTransactionData).displayReceiptMerchantCopy(printMerchantCopyListner)
            }
            SaleTransactionDetails.CASH_RELOAD.category -> {
                ReloadReciepts(requireContext(),requireActivity(),lastTransactionData).displayRecieptMerchantCopy(printMerchantCopyListner)
            }
            SaleTransactionDetails.CARD_FEE_PAYMENT.category -> {
                CardFeeReceipts(requireContext(),requireActivity(),lastTransactionData).displayReceiptMerchantCopy(printMerchantCopyListner)
            }
        }
    }

    val printMerchantCopyListner = object : PrintStatusListener {
        override fun onSuccess() {
            requireActivity().runOnUiThread({
                dialog1?.dismiss()
                transResultTimer.start()
                checkForBatchSettlement()
            })
        }

        override fun onError(error: Int) {
            requireActivity().runOnUiThread({
                dialog1?.dismiss()
                ToastMessages.customMsgToast(requireContext(),"Printer Issues $error")
            })
        }
    }

    fun showDialogPrint(title: String,message: String) {
        DialogUtil.showDialog(requireContext(),title,message,object :DialogUtil.OnClickListener {
            override fun onConfirm() {
                showGifDialog()
                printMerchantCopy()
            }

            override fun onCancel() {
                // Do nothing
            }
        })
    }
}

