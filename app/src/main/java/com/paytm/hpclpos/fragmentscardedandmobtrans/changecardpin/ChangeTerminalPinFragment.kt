package com.paytm.hpclpos.fragmentscardedandmobtrans.changecardpin

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.FragmentChangeTerminalPinBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty.CcmsSaleFragment
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSaleRequest
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class ChangeTerminalPinFragment : BaseFragment(), View.OnClickListener {

    lateinit var mobNokeyboard: EnterMobileNoKeyboard
    lateinit var binding: FragmentChangeTerminalPinBinding
    lateinit var batchId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_change_terminal_pin, container, false)
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        binding.gotoBack.setOnClickListener(this)
        binding.oldPINEditText.setTransformationMethod(PasswordTransformationMethod())
        binding.newPINEditText.setTransformationMethod(PasswordTransformationMethod())
        binding.reEnterPinEditText.setTransformationMethod(PasswordTransformationMethod())
        handleEnterKey()
    }

    private fun pinValidations() {
            if( binding.oldPINEditText.text.toString().equals("", ignoreCase = true) &&
                binding.newPINEditText.text.toString().equals("", ignoreCase = true) &&
                binding.reEnterPinEditText.text.toString().equals("", ignoreCase = true)) {
                binding.oldPINEditText.setError(getString(R.string.input_required))
                binding.newPINEditText.setError(getString(R.string.input_required))
                binding.reEnterPinEditText.setError(getString(R.string.input_required))
                ToastMessages.customMsgToast(context, "Should not be Empty")
            } else if(binding.oldPINEditText.text.toString().equals("", ignoreCase = true) &&
                binding.newPINEditText.text.toString().equals("", ignoreCase = true)) {
                binding.oldPINEditText.setError(getString(R.string.input_required))
                binding.newPINEditText.setError(getString(R.string.input_required))
                ToastMessages.customMsgToast(context, "Should not be Empty")
            } else if(binding.oldPINEditText.text.toString().equals("", ignoreCase = true) &&
                binding.reEnterPinEditText.text.toString().equals("", ignoreCase = true)) {
                binding.oldPINEditText.setError(getString(R.string.input_required))
                binding.reEnterPinEditText.setError(getString(R.string.input_required))
                ToastMessages.customMsgToast(context, "Should not be Empty")
            } else if(binding.newPINEditText.text.toString().equals("", ignoreCase = true) &&
                binding.reEnterPinEditText.text.toString().equals("", ignoreCase = true)) {
                binding.newPINEditText.setError(getString(R.string.input_required))
                binding.reEnterPinEditText.setError(getString(R.string.input_required))
                ToastMessages.customMsgToast(context, "Should not be Empty")
            } else if (binding.oldPINEditText.text.toString().equals("", ignoreCase = true)) {
                ToastMessages.customMsgToast(context, resources.getString(R.string.entervalidoldpin))
                binding.oldPINEditText.setError(resources.getString(R.string.entervalidoldpin))
            } else if (binding.newPINEditText.text.toString().equals("", ignoreCase = true)) {
                ToastMessages.customMsgToast(context, resources.getString(R.string.entervalidnewpin))
                binding.newPINEditText.setError(resources.getString(R.string.entervalidnewpin))
            } else if (binding.reEnterPinEditText.text.toString().equals("", ignoreCase = true)) {
                ToastMessages.customMsgToast(context, resources.getString(R.string.entervalidreintpin))
                binding.reEnterPinEditText.setError(resources.getString(R.string.entervalidreintpin))
            } else if (!binding.newPINEditText.text.toString().equals(binding.reEnterPinEditText.text.toString(), ignoreCase = true)) {
                binding.reEnterPinEditText.setError(resources.getString(R.string.mismatchnewpin))
                ToastMessages.customMsgToast(context, resources.getString(R.string.mismatchnewpin))
            } else {
                checkAndNavigate(binding.newPINEditText.text.toString(),binding.oldPINEditText.text.toString())
            }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> {
                hideKeypad()
                requireActivity().onBackPressed()
            }
        }
    }

    fun checkAndNavigate(pinNew: String,pinOld: String) {
        batchId = TransactionUtils.getCurrentBatch(requireContext(), Constants.BATCH)
        val merchantViewModel = ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        when(GlobalMethods.getTransType()) {
            SaleTransactionDetails.CHANGE_CARD_PIN.saleName -> {
                val constructSaleRequest =
                    ConstructSaleRequest(requireContext(), batchId.toInt(), "")
                        .constructChangeCardPinRequest(pinNew, pinOld)
                merchantViewModel.makeApiChangeCardPin(constructSaleRequest)
                merchantViewModel.getliveDataChangeCardPin().observe(viewLifecycleOwner, {
                    if (it != null) {
                        val bundle = Bundle()
                        bundle.putString(Constants.LIMITEXCEED, it.data[0].reason)
                        navController!!.navigate(R.id.action_transactionFailed, bundle)
                    } else {
                        ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
                    }
                })
            }

            SaleTransactionDetails.CONTROL_PIN_CHANGE.saleName -> {
                val constructSaleRequest =
                    ConstructSaleRequest(requireContext(), batchId.toInt(), "")
                        .constructControlPinChangeRequest(pinNew, pinOld)
                merchantViewModel.makeApiControlPinChange(constructSaleRequest)
                merchantViewModel.getliveDataControlPinChange().observe(viewLifecycleOwner, {
                    if (it != null) {
                        val bundle = Bundle()
                        bundle.putString(Constants.LIMITEXCEED, it.data[0].reason)
                        navController!!.navigate(R.id.action_transactionFailed, bundle)
                    } else {
                        ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
                    }
                })
            }
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.oldPINEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.reEnterPinEditText.setImeOptions(EditorInfo.IME_ACTION_DONE)
        binding.reEnterPinEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                pinValidations()
                true
            } else false
        })
    }
}