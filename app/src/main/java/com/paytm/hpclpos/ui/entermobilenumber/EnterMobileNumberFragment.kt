package com.paytm.hpclpos.ui.entermobilenumber

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.cardredoptions.CardInfoEntity
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivityEnterMobileNumberBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSettlementRequest
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class EnterMobileNumberFragment : BaseFragment(), View.OnClickListener {

    override var dialog: Dialog? = null
    lateinit var activityEnterMobileNumner: ActivityEnterMobileNumberBinding
    private var settlementDialog: SettlementDialog? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        activityEnterMobileNumner = DataBindingUtil.inflate(inflater,
            R.layout.activity_enter_mobile_number, container, false)
        activityEnterMobileNumner.title.setText(GlobalMethods.getSaleType())
        viewFinds()
        return activityEnterMobileNumner.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun viewFinds() {
        handleOnBackPressed()
        activityEnterMobileNumner.gotoBack.setOnClickListener(this)
        handleEnterKey()
    }

    private fun actionDone() {
        val mobNo = activityEnterMobileNumner.mobnoEditText.text.toString()
        if (!Validation.isvalidMobNo(mobNo.trim { it <= ' ' })) {
            Toast.makeText(context, "Please enter a valid Mobile No.", Toast.LENGTH_SHORT).show()
        } else {
            closesoftKeypad(activityEnterMobileNumner.mobnoEditText)
            GlobalMethods.setMobileNo(activityEnterMobileNumner.mobnoEditText.text.toString())
            GlobalMethods.setCardInfoEntity(CardInfoEntity().apply {
                isMobileTransaction = true
                mobileNumber = activityEnterMobileNumner.mobnoEditText.text.toString()
            })
            when(GlobalMethods.getSaleType()) {
                SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName -> {
                    navController!!.navigate(R.id.action_vehicleNumberFragment)
                }

                SaleTransactionDetails.PAYBACK_EARN.saleName -> {
                    navController?.navigate(R.id.action_amount_entry_fragment)
                }

                SaleTransactionDetails.PAYBACK_BURN.saleName -> {
                    navController?.navigate(R.id.action_payback_amount_point_fragment)
                }

                SaleTransactionDetails.PAYBACK_VOID.saleName,
                SaleTransactionDetails.VOID.saleName -> {
                    navController!!.navigate(R.id.action_enter_roc_fragment)
                }
                else -> sendOtp()
            }
        }
    }

    private fun otpActivityCall() {
        settlementDialog!!.dismiss()
        GlobalMethods.setMobileNo(activityEnterMobileNumner.mobnoEditText.text.toString())
        GlobalMethods.setCardInfoEntity(CardInfoEntity().apply {
            isMobileTransaction = true
            mobileNumber = activityEnterMobileNumner.mobnoEditText.text.toString()
        })
        navController!!.navigate(R.id.action_enterOtpFragment)
        activityEnterMobileNumner.mobnoEditText.setText("")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> {
               // goToMainFragment()
            }
        }
    }

    private fun dialogOpen() {
        dialog = Dialog(this.requireContext())
        dialog!!.setContentView(R.layout.item_select_dtplsfasttag)
        val window = dialog!!.window
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val btnConfirm = dialog!!.findViewById<Button>(R.id.btnConfirm)
        val radioGroup = dialog!!.findViewById<RadioGroup>(R.id.radioGroup)
        val mobNokeyboard: EnterMobileNoKeyboard = dialog!!.findViewById(R.id.mobileNokeyboard)
        val layoutDone = mobNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == R.id.rbdtplus) {
                    otpActivityCall()
                } else if (selectedId == R.id.rbfasttag) {
                    dialoghdfcidfc()
                }
            }
        })

        btnConfirm.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == R.id.rbdtplus) {
                    otpActivityCall()
                } else if (selectedId == R.id.rbfasttag) {
                    dialoghdfcidfc()
                }
            }
        })
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                // Do nothing
            }
        })
        dialog!!.show()
    }

    private fun dialoghdfcidfc() {
        dialog = Dialog(this.requireContext())
        dialog!!.setContentView(R.layout.item_dialog_hdfcidfc)
        val window = dialog!!.window
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val btnConfirm = dialog!!.findViewById<Button>(R.id.btnConfirm)
        val radioGroup = dialog!!.findViewById<RadioGroup>(R.id.radioGroup)
        btnConfirm.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == R.id.rbdtplus) {
                    otpActivityCall()
                } else if (selectedId == R.id.rbfasttag) {
                    otpActivityCall()
                }
            }
        })
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                // Do nothing
            }
        })
        dialog!!.show()
    }


    fun sendOtp() {
        showProcessingDialog()
        val viewModel =
            ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        val generateOtp =
            ConstructSettlementRequest(requireContext()).getOtpRequest(activityEnterMobileNumner.mobnoEditText.text.toString())
        viewModel.makeApiGenerateOTP(generateOtp)
        viewModel.getliveDataGenerateOTP().observe(viewLifecycleOwner, Observer {

            if (it != null) {
                if (it.success) {
                    settlementDialog!!.onSuccess()
                    ToastMessages.customMsgToast(context, "Your Generated OTP is " + it.data[0].otp)
                    if (TitleName.titleName.equals(resources.getString(R.string.ccmssale), ignoreCase = true) || TitleName.titleName.equals(
                            resources.getString(R.string.cardsale), ignoreCase = true)) {
                        dialogOpen()
                    } else {
                        Handler().postDelayed({ otpActivityCall()},1000)
                    }
                } else {
                    postFailure(it.message)
                }
            } else {
                postFailure("Internal Server Error")
            }
        })
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    fun goToMainFragment() {
        ToastMessages.customMsgToast(requireContext(),getString(R.string.transaction_cancelled))
        closesoftKeypad(activityEnterMobileNumner.mobnoEditText)
        navController!!.navigate(R.id.action_main_fragment)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { goToMainFragment() }
            })
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activityEnterMobileNumner.mobnoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        activityEnterMobileNumner.mobnoEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        activityEnterMobileNumner.mobnoEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                actionDone()
                true
            } else false
        })
    }

    private fun showProcessingDialog() {
        settlementDialog = SettlementDialog(requireActivity())
        activityEnterMobileNumner.cardView.visibility = View.GONE
        settlementDialog?.setTitle(AppUtils.getTitleName(GlobalMethods.getSaleType()!!))
        settlementDialog?.setProcessing()
        settlementDialog?.setpacketStatus("Recieving.....")
        settlementDialog?.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
        settlementDialog?.show()
    }

    private fun postFailure(errorMessage: String) {
        settlementDialog!!.onFailure(errorMessage)
        Handler().postDelayed({
            ToastMessages.customMsgToast(context, errorMessage)
            settlementDialog!!.dismiss()
        }, 1000)
    }
}
