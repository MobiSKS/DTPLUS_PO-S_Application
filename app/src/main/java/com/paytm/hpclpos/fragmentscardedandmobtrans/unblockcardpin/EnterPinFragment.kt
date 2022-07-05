package com.paytm.hpclpos.fragmentscardedandmobtrans.unblockcardpin

import android.content.Context
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.FragmentEnterPinBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSaleRequest
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel


class EnterPinFragment : BaseFragment(), View.OnClickListener {
    var gotoBack: LinearLayout? = null
    lateinit var batchId: String
    lateinit var fragmentEnterPinBinding: FragmentEnterPinBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentEnterPinBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_enter_pin,
            container,
            false
        )
        gotoBack = fragmentEnterPinBinding.gotoBack
        fragmentEnterPinBinding.rocNoEditText.setTransformationMethod(PasswordTransformationMethod())
        fragmentEnterPinBinding.reEnterrocNoEditText.setTransformationMethod(PasswordTransformationMethod())
        return fragmentEnterPinBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEnterKey()
        viewFinds(view)
    }

    private fun callApi() {
        if(fragmentEnterPinBinding.rocNoEditText.text.toString().equals("", ignoreCase = true) &&
            fragmentEnterPinBinding.reEnterrocNoEditText.text.toString().equals("", ignoreCase = true)) {
            ToastMessages.customMsgToast(requireContext(), "Should not be Empty")
            fragmentEnterPinBinding.rocNoEditText.setError( resources.getString(R.string.input_required))
            fragmentEnterPinBinding.reEnterrocNoEditText.setError( resources.getString(R.string.input_required))
        } else if (fragmentEnterPinBinding.rocNoEditText.text.toString().equals("", ignoreCase = true)) {
            ToastMessages.customMsgToast(requireContext(), resources.getString(R.string.entervalidnewpin))
            fragmentEnterPinBinding.rocNoEditText.setError( resources.getString(R.string.input_required))
        } else if (fragmentEnterPinBinding.reEnterrocNoEditText.text.toString()
                .equals("", ignoreCase = true)
        ) {
            ToastMessages.customMsgToast(requireContext(), resources.getString(R.string.entervalidreintpin))
            fragmentEnterPinBinding.reEnterrocNoEditText.setError(resources.getString(R.string.input_required))
        } else if (!fragmentEnterPinBinding.rocNoEditText.text.toString()
                .equals(
                    fragmentEnterPinBinding.reEnterrocNoEditText.text.toString(),
                    ignoreCase = true
                )
        ) {
            ToastMessages.customMsgToast(requireContext(), resources.getString(R.string.mismatchnewpin))
            fragmentEnterPinBinding.reEnterrocNoEditText.setError(resources.getString(R.string.mismatchnewpin))

        } else {
            checkTransTypeforNavigation(fragmentEnterPinBinding.rocNoEditText.text.toString())
        }
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view1: View?, motionEvent: MotionEvent? -> true }
        gotoBack!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.gotoBack) {
            hideKeypad()
            requireActivity().onBackPressed()
        }
    }

    override fun updateTimerUi(l: Long) {
        //
    }

    private fun checkTransTypeforNavigation(pinNew: String) {
        batchId = TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH)
        val merchantViewModel = ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        val constructSaleRequest = ConstructSaleRequest(requireContext(), batchId.toInt(), "")
            .constructUnblockCardPinRequest(pinNew)
        merchantViewModel.makeApiUnblockCardPin(constructSaleRequest)
        merchantViewModel.getliveDataUnblockCardPin().observe(viewLifecycleOwner, {
            if (it != null) {
                val bundle = Bundle()
                bundle.putString(Constants.LIMITEXCEED, it.data[0].reason)
                navController!!.navigate(R.id.action_transactionFailed, bundle)
            } else {
                ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
            }
        })
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        fragmentEnterPinBinding.rocNoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        fragmentEnterPinBinding.reEnterrocNoEditText.setImeOptions(EditorInfo.IME_ACTION_DONE)
        fragmentEnterPinBinding.reEnterrocNoEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(fragmentEnterPinBinding.reEnterrocNoEditText)
                callApi()
                true
            } else false
        })
    }
}