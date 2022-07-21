package com.paytm.hpclpos.ui.odometerreading

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.PrintReceipts.Store
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivityOdometerReadingBinding
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.livedatamodels.generatetoken.ApiResponse
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSaleRequest
import com.paytm.hpclpos.viewmodel.MainActivityViewModel

class OdometerReadingFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding: ActivityOdometerReadingBinding
    private var settlementDialog: SettlementDialog? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_odometer_reading, container, false)
        binding.gotoBack.setOnClickListener(this)
        binding.lLskip.setOnClickListener(this)
        handleOnBackPressed()
        handleEnterKey()
        binding.title.setText(GlobalMethods.getSaleType())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun odometerdoneCall() {
        if (NetworkUtil.checkNetworkStatus(requireContext())) {
            showProcessingDialog()
            saleTransaction()
        }
        binding.odometerEditText.setText("")
    }

    private fun goToTransactionSuccessActivity() {
        settlementDialog?.dismiss()
        navController!!.navigate(R.id.action_transactionSuccess)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View) {
        closesoftKeypad(binding.odometerEditText)
        when (v.id) {
            R.id.gotoBack -> { /*goToMainFragment()*/ }
            R.id.lLskip -> odometerdoneCall()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saleTransaction() {
        val batchId = TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH)
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        var odometerReading = binding.odometerEditText.text.toString();
        if (odometerReading == null || odometerReading.equals("") || odometerReading.toLong() <= 0) {
            odometerReading = "0"
        }
        val ccmsSaleRequest = ConstructSaleRequest(requireContext(),
            batchId.toInt(),binding.odometerEditText.text.toString()).constructSaleRequest()

        viewModel.makeApiCcmsSale(ccmsSaleRequest)
        viewModel.getliveDataCcmsSale().observe(viewLifecycleOwner, {
            when (it) {
                is com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.Error -> {
                    ToastMessages.customMsgToast(requireContext(), it.error)
                }

                is com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.CCMSSaleResponse -> {
                    if (it.success) {
                        if (it.internelStatusCode == Constants.STATUS_SUCCESS) {
                            Store(requireContext()).storeTransDetailsInDB(ccmsSaleRequest, it.data[0].balance, it.data[0].rsp)
                            settlementDialog!!.onSuccess()
                            Handler().postDelayed({ goToTransactionSuccessActivity() }, 1000)
                        } else {
                            postFailure(ccmsSaleRequest,it.data[0].reason)
                        }
                    } else {
                        postFailure(ccmsSaleRequest,it.data[0].reason)
                    }
                }
            }
        })
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    private fun showProcessingDialog() {
        settlementDialog = SettlementDialog(requireActivity())
        binding.relativeLayout.visibility = View.GONE
        settlementDialog?.setTitle(AppUtils.getTitleName(GlobalMethods.getSaleType()!!))
        settlementDialog?.setProcessing()
        settlementDialog?.setpacketStatus("Recieving.....")
        settlementDialog?.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
        settlementDialog?.show()
    }

    fun goToMainFragment() {
        ToastMessages.customMsgToast(requireContext(),getString(R.string.transaction_cancelled))
        navController!!.navigate(R.id.action_main_fragment)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    closesoftKeypad(binding.odometerEditText)
                    goToMainFragment()
                }
            })
    }

    fun postFailure(ccmsSaleRequest: CCMSSaleRequest,errorMessage: String) {
        GlobalMethods.decrementTransactionIdByOne(requireContext(), ccmsSaleRequest.invoiceId.toString())
        settlementDialog!!.onFailure(errorMessage)
        Handler().postDelayed({
            settlementDialog!!.dismiss()
            val bundle = Bundle()
            bundle.putString(Constants.LIMITEXCEED, errorMessage)
            navController!!.navigate(R.id.action_transactionFailed, bundle)
        },1000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.odometerEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.odometerEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.odometerEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.odometerEditText)
                odometerdoneCall()
                true
            } else false
        })
    }
}