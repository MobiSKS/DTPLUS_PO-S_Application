package com.paytm.hpclpos.ui.paymerchant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import android.widget.Toast
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentPayMarchantMainBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class PayMerchantMainFragment : BaseFragment() {
    private var binding: FragmentPayMarchantMainBinding? = null
    override fun updateTimerUi(l: Long) {
      //
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_marchant_main, container, false)
        handleEnterKey()
        return binding!!.root
    }

    private fun transactionPageCall() {
        val payCode = binding!!.paycodeEditText.text.toString()
        if (payCode.trim { it <= ' ' }.length < 1) {
            Toast.makeText(activity, "Please Enter Paycode.", Toast.LENGTH_SHORT).show()
        } else if (!Validation.isvalidpaycode(payCode.trim { it <= ' ' })) {
            Toast.makeText(activity, "Please enter a valid Paycode no.", Toast.LENGTH_SHORT).show()
        } else {
            ToastMessages.customMsgToast(requireContext(),"Need to be Implemented")
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding?.paycodeEditText?.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding?.paycodeEditText?.setImeOptions(EditorInfo.IME_ACTION_DONE)
        binding?.paycodeEditText?.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding!!.paycodeEditText)
                transactionPageCall()
                true
            } else false
        })
    }
}