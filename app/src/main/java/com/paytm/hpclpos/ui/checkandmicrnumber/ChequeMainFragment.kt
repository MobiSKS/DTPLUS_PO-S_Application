package com.paytm.hpclpos.ui.checkandmicrnumber

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentChequeMainBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class ChequeMainFragment : BaseFragment() {

    var mobNokeyboard: EnterMobileNoKeyboard? = null
    lateinit var binding: FragmentChequeMainBinding
    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cheque_main, container, false)
        binding.root.setOnTouchListener { view, motionEvent -> true }
        viewFinds()
        return binding.root
    }

    private fun viewFinds() {
        handleEnterKey()
        binding.gotoBack.setOnClickListener {
            hideKeypad()
            requireActivity().onBackPressed()
        }
    }

    private fun callControlCardNoFrag() {
        if(binding.etChqNo.text.toString().isBlank() && binding.etMicrCode.text.toString().isBlank()) {
            binding.etChqNo.setError( resources.getString(R.string.input_required))
            binding.etMicrCode.setError( resources.getString(R.string.input_required))
        } else if(binding.etChqNo.text.toString().isBlank()) {
            binding.etChqNo.setError( resources.getString(R.string.input_required))
        } else if(binding.etMicrCode.text.toString().isBlank()) {
            binding.etMicrCode.setError( resources.getString(R.string.input_required))
        } else if(!Validation.isvalidChequeNo(binding.etChqNo.text.toString())) {
            Toast.makeText(context, resources.getString(R.string.entervalidcheqno), Toast.LENGTH_LONG).show()
            binding.etChqNo.setError( resources.getString(R.string.entervalidcheqno))
        } else if(!Validation.isvalidMICRCode(binding.etMicrCode.text.toString())) {
            Toast.makeText(context, resources.getString(R.string.entervalidmicrno), Toast.LENGTH_LONG).show()
            binding.etMicrCode.setError( resources.getString(R.string.entervalidmicrno))
        } else {
            GlobalMethods.setChequeNumber(binding.etChqNo.text.toString())
            GlobalMethods.setMicrCode(binding.etMicrCode.text.toString())
            if (GlobalMethods.getCardInfoEntity() != null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber)
                navController!!.navigate(R.id.controlCardNumber)
            else {
                navController!!.navigate(R.id.action_amountEntryFragment)
            }
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.etChqNo.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.etMicrCode.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.etMicrCode.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.etMicrCode)
                callControlCardNoFrag()
                true
            } else false
        })
    }
}