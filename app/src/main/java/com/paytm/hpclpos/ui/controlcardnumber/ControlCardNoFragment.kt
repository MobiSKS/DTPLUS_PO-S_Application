package com.paytm.hpclpos.ui.controlcardnumber

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.cardredoptions.CardInfoEntity
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.GlobalMethods.Companion.setCardInfoEntity
import com.paytm.hpclpos.constants.GlobalMethods.Companion.setControlCardNumber
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentCashEnterCntRLCardNoBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class ControlCardNoFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentCashEnterCntRLCardNoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash_enter_cnt_r_l_card_no, container, false)
        binding.lLNext.setOnClickListener(this)
        binding.lLcancl.setOnClickListener(this)
        binding.gotoBack.setOnClickListener(this)
        handleEnterKey()
        binding.tvTitle.setText(GlobalMethods.getSaleType())
        return binding.root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lLNext -> {
                hideKeypad()
                callEnterAmountFrag()
            }
            R.id.lLcancl -> {
                hideKeypad()
                requireActivity().onBackPressed()
            }
            R.id.gotoBack -> {
                hideKeypad()
                requireActivity().onBackPressed()
            }
        }
    }

    private fun callEnterAmountFrag() {
        val ctRLCardNo =  binding.cardNoEditText.text.toString()
        if (!Validation.isvalidCTRLCardNO(ctRLCardNo.trim { it <= ' ' })) {
            Toast.makeText(context, "Please enter a valid Control Card No", Toast.LENGTH_SHORT)
                .show()
        } else {
            checkTransTypeAndNavigate()
            setControlCardNumber(ctRLCardNo)
            val cardInfoEntity = CardInfoEntity()
            cardInfoEntity.controlCardNumber = ctRLCardNo
            cardInfoEntity.isControlCardNumber = true
            setCardInfoEntity(cardInfoEntity)
            binding.cardNoEditText.setText("")
        }
    }

    fun checkTransTypeAndNavigate() {
        when (GlobalMethods.getTransType()) {
            SaleTransactionDetails.CCMS_BALANCE.saleName -> {
                navController!!.navigate(R.id.action_card_pin_fragment)
            }
            SaleTransactionDetails.CONTROL_PIN_CHANGE.saleName -> {
                navController!!.navigate(R.id.action_change_card_pin_fragment)
            }
            else -> {
                navController!!.navigate(R.id.amountEntryFragment)
            }
        }
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.cardNoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.cardNoEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.cardNoEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.cardNoEditText)
                callEnterAmountFrag()
                true
            } else false
        })
    }
}