package com.paytm.hpclpos.ui.getcardpin

import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.PrintReceipts.Store
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.cardredoptions.*
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivityEnterCardPinBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.Data
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSaleRequest
import com.paytm.hpclpos.viewmodel.MainActivityViewModel
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class EnterCardPinFragment : BaseFragment() {
    lateinit var binding: ActivityEnterCardPinBinding
    lateinit var batchId: String
    lateinit var viewModel: MainActivityViewModel
    private var settlementDialog: SettlementDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_enter_card_pin, container, false)

        binding.lLconfirm.setOnClickListener { odometerActivityCall() }
        binding.title.setText(GlobalMethods.getSaleType())
        val keyboard = binding.root.findViewById<View>(R.id.keyboard) as MyKeyboard
        binding.enterPinText.setOnTouchListener { v, event ->
            val inType = binding.enterPinText.inputType // backup the input type
            binding.enterPinText.inputType = InputType.TYPE_NULL // disable soft input
            binding.enterPinText.onTouchEvent(event) // call native handler
            binding.enterPinText.inputType = inType // restore input type
            true // consume touch even
        }
        sharedPreferencesData = SharedPreferencesData(requireActivity())
        batchId = TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH)
        // prevent system keyboard from appearing when EditText is tapped
        binding.enterPinText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.enterPinText.setTextIsSelectable(true)
        if(GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
            binding.pinType.setText(getString(R.string.enter_control_pin))
        }
        handleOnBackPressed()
        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.enterPinText.onCreateInputConnection(EditorInfo())
        keyboard.setInputConnection(ic)
        return binding.root
    }

    private fun odometerActivityCall() {
        val cardPin = binding.enterPinText.text.toString()
        if (!Validation.isvalidCardPin(cardPin.trim { it <= ' ' })) {
            ToastMessages.customMsgToast(this.requireContext(), "Enter 4 digit PIN")
        } else {
            GlobalMethods.setPinData(binding.enterPinText.text.toString())
            Log.d("Enter Card Pin","Card Pin ${GlobalMethods.getPinData()}")
            callPinVerifyThreadInit()
        }
    }

    fun checkTransTypeforNavigation() {
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val merchantViewModel = ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        Log.d("EnterCardPinFragment","Sale Name ${GlobalMethods.getSaleType()}")
        when (GlobalMethods.getSaleType()) {
            SaleTransactionDetails.CARDSALE.saleName,
            SaleTransactionDetails.CCMSSALE.saleName,
            SaleTransactionDetails.CREDIT_TXN.saleName,
            SaleTransactionDetails.DEALER_CREDIT_SALE.saleName -> {
                navController!!.navigate(R.id.action_odometerReadingFragment)
                binding.enterPinText.setText("")
            }

            SaleTransactionDetails.CCMS_CASHRECHARGE.saleName,
            SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName,
            SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructCCMSRechargeRequest()
                viewModel.makeApiCcmsRechargeSale(constructSaleRequest)
                viewModel.getliveDataCcmsRechargeSale().observe(viewLifecycleOwner, {

                    when (it) {
                        is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.Error -> {
                            ToastMessages.customMsgToast(requireContext(), it.error)
                        }

                        is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse -> {
                            if (it.success) {
                                if (it.internelStatusCode == Constants.STATUS_SUCCESS) {
                                    Store(requireActivity()).storeRechargeTransDetailsInDB(constructSaleRequest)
                                    callSettlementOnSuccessDialog()
                                } else {
                                    GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                                    callSettlementOnFailureDialog(it.data[0].reason)
                                }
                            } else {
                                GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                                callSettlementOnFailureDialog(it.data[0].reason)
                            }
                        }
                    }
                })
            }

            SaleTransactionDetails.BALANCE_TRANSFER.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructBalanceTransferRequest()
                merchantViewModel.makeApiBalanceTransfer(constructSaleRequest)
                merchantViewModel.getliveDataBalanceTransfer().observe(viewLifecycleOwner, {

                    when (it) {
                        is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.Error -> {
                            ToastMessages.customMsgToast(requireContext(), it.error)
                        }

                        is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse -> {
                            if (it.success) {
                                if (it.internelStatusCode == Constants.STATUS_SUCCESS) {
                                    Store(requireContext()).storeBalanceTransferDetailsInDB(constructSaleRequest)
                                    ToastMessages.customMsgToast(requireContext(),"Response Message ${it.message}")
                                    callSettlementOnSuccessDialog()
                                } else {
                                    GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                                    callSettlementOnFailureDialog(it.data[0].reason)
                                }
                            } else {
                                GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                                callSettlementOnFailureDialog(it.data[0].reason)
                            }
                        }
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
                       when (it) {
                           is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.Error -> {
                               ToastMessages.customMsgToast(requireContext(), it.error)
                           }

                           is com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse -> {
                               if (it.success) {
                                   if (it.internelStatusCode == Constants.STATUS_SUCCESS) {
                                       ToastMessages.customMsgToast(requireContext(),"Response Message ${it.message}")
                                       Store(requireActivity()).storeReloadTransDetailsInDB(constructSaleRequest)
                                       callSettlementOnSuccessDialog()
                                   } else {
                                       GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                                       callSettlementOnFailureDialog(it.data[0].reason)
                                   }
                               } else {
                                   GlobalMethods.decrementTransactionIdByOne(requireContext(),constructSaleRequest.invoiceId.toString())
                                   callSettlementOnFailureDialog(it.data[0].reason)
                               }
                           }
                       }
                   })
            }

            SaleTransactionDetails.BALANCE_ENQUIRY.saleName -> {
                showProcessingDialog()
                val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructBalanceEnquiryRequest()
                merchantViewModel.makeApiBalanceEnquiry(constructSaleRequest)
                merchantViewModel.getliveDataBalanceEnquiry().observe(viewLifecycleOwner, {

                    when (it) {
                        is ApiResponse.Error -> { ToastMessages.customMsgToast(requireContext(), it.error) }

                        is ApiResponse.BalanceEnquiryResponse -> {
                            if (it.success) {
                                if (it.internelStatusCode == Constants.STATUS_SUCCESS) {
                                    settlementDialog?.onSuccess()
                                    Handler().postDelayed({ goToBalanceEnquiryTransactionSuccessActivity(it.data[0],constructSaleRequest)},1000)
                                } else {
                                    callSettlementOnFailureDialog(it.message)
                                }
                            } else {
                                callSettlementOnFailureDialog(it.message)
                            }
                        }
                    }
                })
            }

            SaleTransactionDetails.CCMS_BALANCE.saleName -> {
                    showProcessingDialog()
                    val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "").constructCCMSBalanceRequest()
                    merchantViewModel.makeApiCCMSBalanceEnquiry(constructSaleRequest)
                    merchantViewModel.getliveDataCCMSBalanceEnquiry().observe(viewLifecycleOwner, {

                        when (it) {
                            is com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.Error -> {
                                ToastMessages.customMsgToast(requireContext(), it.error)
                            }

                            is com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.CCMSBalanceResponse -> {
                                if (it.success) {
                                    if (it.internelStatusCode == Constants.STATUS_SUCCESS) {
                                        settlementDialog?.onSuccess()
                                        Handler().postDelayed({ goToCCMSBalanceEnquiryTransactionSuccessActivity(it.data[0])},1000)
                                    } else {
                                        callSettlementOnFailureDialog(it.message)
                                    }
                                } else {
                                    callSettlementOnFailureDialog(it.message)
                                }
                            }
                        }
                    })
                }

            SaleTransactionDetails.VOID.saleName -> {
                DialogUtil.showTerminalPinDialog(requireContext(), dialogListener)
            }
        }
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    private fun showProcessingDialog() {
        settlementDialog = SettlementDialog(requireActivity())
        binding.relativeLayout.visibility = View.GONE
        binding.keyboard.visibility = View.GONE
        binding.confirmButton.visibility = View.GONE
        settlementDialog?.setTitle(AppUtils.getTitleName(GlobalMethods.getSaleType()!!))
        settlementDialog?.setProcessing()
        settlementDialog?.setpacketStatus("Recieving.....")
        settlementDialog?.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
        settlementDialog?.show()
    }

    private fun goToTransactionSuccessActivity() {
        settlementDialog?.dismiss()
        navController!!.navigate(R.id.action_transactionSuccess)
    }

    private fun callSettlementOnSuccessDialog() {
        settlementDialog!!.onSuccess()
        Handler().postDelayed({ goToTransactionSuccessActivity()},1000)
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

    private fun goToBalanceEnquiryTransactionSuccessActivity(data: Data,balanceEnquiryRequest: BalanceEnquiryRequest) {
        settlementDialog?.dismiss()
        val bundle = Bundle()
        bundle.putSerializable("transactionStatus", data)
        bundle.putSerializable("transactionRequest",balanceEnquiryRequest)
        navController!!.navigate(R.id.action_balance_enquiry_transactionSuccess,bundle)
    }

    private fun goToCCMSBalanceEnquiryTransactionSuccessActivity(data: com.paytm.hpclpos.livedatamodels.CCMSBalance.Data) {
        settlementDialog?.dismiss()
        val bundle = Bundle()
        bundle.putSerializable("ccmsBalanceenquirytransactionStatus", data)
        navController!!.navigate(R.id.action_balance_enquiry_transactionSuccess,bundle)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    gotoMainFragment()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterCardPinCancel.setOnClickListener({ gotoMainFragment() })
        binding.gotoBack.setOnClickListener({ /*gotoMainFragment()*/ })
    }

    private fun gotoMainFragment() {
        ToastMessages.customMsgToastShort(requireContext(),getString(R.string.transaction_cancelled))
        navController!!.navigate(R.id.action_main_fragment)
    }

    fun handleCardReadError(state: CardEventState?) {
        when (state!!.state) {
            CardEventState.CARD_PROFILE_READ_ERROR ->   requireActivity().runOnUiThread({
                ToastMessages.customMsgToast(requireActivity(), "Card Profile Read Failed")
                navController!!.navigate(R.id.action_main_fragment)
            })

            CardEventState.INCORRECT_PIN ->  requireActivity().runOnUiThread {
                val bundle = Bundle()
                bundle.putString(Constants.LIMITEXCEED, "Incorrect Pin")
                navController!!.navigate(R.id.action_transactionFailed, bundle)
                CardOptions.closeIccAndMag()
            }

            CardEventState.CARD_PIN_READ_ERROR -> requireActivity().runOnUiThread {
                ToastMessages.customMsgToast(requireActivity(), "Card Pin Read Error")
                navController!!.navigate(R.id.action_main_fragment)
            }

            CardEventState.CHIP_CARD_NOT_DETECTED -> requireActivity().runOnUiThread {
                ToastMessages.customMsgToast(requireActivity(), "Chip Card not detected,Please Insert card")
                navController!!.navigate(R.id.action_main_fragment)
            }
        }
    }

    fun callPinVerifyThreadInit() {
        if (GlobalMethods.getCardInfoEntity()?.cardType.equals(Constants.ICC)) {
            IcCardCommand(requireActivity(), cardEventListener).verifyPinData(GlobalMethods.getPinData()!!)
        } else {
            checkTransTypeforNavigation()
        }
    }

    val dialogListener = object : DialogUtil.TerminalPinOnClickListener {
        override fun onConfirm(pinPassword: String) {
            if (TransactionUtils.getTerminalPin(requireContext(), Constants.TERMINAL_PIN).equals(pinPassword)) {
                ToastMessages.customMsgToast(requireContext(),"Need to be Implemented")
            } else {
                ToastMessages.customMsgToast(requireContext(),"Incorrect Terminal Password")
            }
        }

        override fun onCancel() {
            // Do nothing
        }
    }

    val cardEventListener = object : CardEventListener {
        override fun onCardEvent(state: CardEventState?) {
            handleCardReadError(state)
        }

        override fun onCardReadSuccess() {
            requireActivity().runOnUiThread({
                ToastMessages.customMsgToast(requireActivity(), "Card Read Success")
                checkTransTypeforNavigation()
            })
        }
    }
}