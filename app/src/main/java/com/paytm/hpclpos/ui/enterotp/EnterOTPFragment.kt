package com.paytm.hpclpos.ui.enterotp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.PrintReceipts.Store
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivityEnterOTPBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.Data
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSaleRequest
import com.paytm.hpclpos.viewmodel.MainActivityViewModel
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class EnterOTPFragment : BaseFragment() {

    lateinit var binding: ActivityEnterOTPBinding
    lateinit var mobileNumber: String
    private var settlementDialog: SettlementDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_enter_o_t_p, container, false)
        binding.gotoBack.setOnClickListener { /*gotoMainActivity()*/ }
        binding.title.setText(GlobalMethods.getSaleType())
        mobileNumber = GlobalMethods.getMobileNo()!!
        handleEnterKey()
        handleOnBackPressed()
        return binding.root
    }

    private fun odometerActivityCall() {
        val oTPNo = binding.oTPEditText.text.toString()
        if (!Validation.isvalidOTPNo(oTPNo.trim { it <= ' ' })) {
            Toast.makeText(context, "Please enter a valid OTP No.", Toast.LENGTH_SHORT).show()
        } else {
            authOtp(oTPNo)
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.oTPEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.oTPEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.oTPEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.oTPEditText)
                odometerActivityCall()
                true
            } else false
        })
    }

    fun authOtp(otpNumber: String) {
        GlobalMethods.setOtpNumber(otpNumber)
        checkTransTypeforNavigation()
    }

    fun checkTransTypeforNavigation() {
        val batchId = TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH)
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val merchantViewModel = ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        when (GlobalMethods.getSaleType()) {
            SaleTransactionDetails.CREDIT_TXN.saleName,
            SaleTransactionDetails.CARDSALE.saleName,
            SaleTransactionDetails.CCMSSALE.saleName -> {
                navController!!.navigate(R.id.action_enter_Odometer_Fragment)
                binding.oTPEditText.setText("")
            }
            SaleTransactionDetails.CCMS_CASHRECHARGE.saleName,
            SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName,
            SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructCCMSRechargeRequest()
                viewModel.makeApiCcmsRechargeSale(constructSaleRequest)
                viewModel.getliveDataCcmsRechargeSale().observe(viewLifecycleOwner, {
                    if (it != null) {
                        if (it.success) {
                            Store(requireActivity()).storeRechargeTransDetailsInDB(constructSaleRequest)
                            callSettlementOnSuccessDialog()
                        } else {
                            GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                            callSettlementOnFailureDialog(it.data[0].reason)
                        }
                    } else {
                        ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
                    }
                })
            }

            SaleTransactionDetails.CCMS_RELOAD.saleName,
            SaleTransactionDetails.CASH_RELOAD.saleName,
            SaleTransactionDetails.CHEQUE_RELOAD.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructReloadRequest()
                merchantViewModel.makeApiReload(constructSaleRequest)
                merchantViewModel.getliveDataReload().observe(viewLifecycleOwner, {
                    if (it != null) {
                        if (it.success) {
                            ToastMessages.customMsgToast(requireContext(),"Response Message ${it.message}")
                            Store(requireActivity()).storeReloadTransDetailsInDB(constructSaleRequest)
                            callSettlementOnSuccessDialog()
                        } else {
                            GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                            callSettlementOnFailureDialog(it.data[0].reason)
                        }
                    } else {
                        ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
                    }
                })
            }

            SaleTransactionDetails.BALANCE_TRANSFER.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructBalanceTransferRequest()
                merchantViewModel.makeApiBalanceTransfer(constructSaleRequest)
                merchantViewModel.getliveDataBalanceTransfer().observe(viewLifecycleOwner, {
                    if (it != null) {
                        if (it.success) {
                            ToastMessages.customMsgToast(requireContext(),"Response Message ${it.message}")
                            callSettlementOnSuccessDialog()
                        } else {
                            GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                            callSettlementOnFailureDialog(it.data[0].reason)
                        }
                    } else {
                        ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
                    }
                })
            }

            SaleTransactionDetails.BALANCE_ENQUIRY.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructBalanceEnquiryRequest()
                merchantViewModel.makeApiBalanceEnquiry(constructSaleRequest)
                merchantViewModel.getliveDataBalanceEnquiry().observe(viewLifecycleOwner, {
                    if (it != null) {
                        if (it.success) {
                            settlementDialog?.onSuccess()
                            Handler().postDelayed({ goToBalanceEnquiryTransactionSuccessActivity(it.data[0],constructSaleRequest)},1000)
                        } else {
                            callSettlementOnFailureDialog(it.message)
                        }
                    } else {
                        ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
                    }
                })
            }

            SaleTransactionDetails.VOID.saleName -> {
                ToastMessages.customMsgToast(requireContext(),"Not Implemented")
            }
        }
    }

    private fun callSettlementOnSuccessDialog() {
        settlementDialog!!.onSuccess()
        Handler().postDelayed({ goToTransactionSuccessActivity()},1000)
    }

    private fun goToTransactionSuccessActivity() {
        settlementDialog?.dismiss()
        navController!!.navigate(R.id.action_transactionSuccess)
    }

    private fun showProcessingDialog() {
        settlementDialog = SettlementDialog(requireActivity())
        binding.constraintLayout.visibility = View.INVISIBLE
        settlementDialog?.setTitle(AppUtils.getTitleName(GlobalMethods.getSaleType()!!))
        settlementDialog?.setProcessing()
        settlementDialog?.setpacketStatus("Recieving.....")
        settlementDialog?.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
        settlementDialog?.show()
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    private fun goToBalanceEnquiryTransactionSuccessActivity(data: Data, balanceEnquiryRequest: BalanceEnquiryRequest) {
        settlementDialog?.dismiss()
        val bundle = Bundle()
        bundle.putSerializable("transactionStatus", data)
        bundle.putSerializable("transactionRequest",balanceEnquiryRequest)
        navController!!.navigate(R.id.action_balance_enquiry_transactionSuccess,bundle)
    }

    private fun callSettlementOnFailureDialog(response: String) {
        settlementDialog!!.onFailure(response)
        Handler().postDelayed({
            settlementDialog!!.dismiss()
            val bundle = Bundle()
            bundle.putString(Constants.LIMITEXCEED, response)
            navController!!.navigate(R.id.action_transactionFailed, bundle)
        },1000)
    }

    private fun gotoMainActivity() {
        closesoftKeypad(binding.oTPEditText)
        navController!!.navigate(R.id.action_main_fragment)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    gotoMainActivity()
                }
            })
    }
}