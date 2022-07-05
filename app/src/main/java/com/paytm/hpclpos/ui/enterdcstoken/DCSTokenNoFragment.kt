package com.paytm.hpclpos.ui.enterdcstoken

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.paytm.hpclpos.R
import com.paytm.hpclpos.ui.amount.AmountEntryFragment
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.ActivityDCSTokenNoBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.ui.amount.AmountViewModel

class DCSTokenNoFragment : BaseFragment(), View.OnClickListener {
    var tokenNo_editText: EditText? = null
    var gotoBack: ImageView? = null
    private var titleName: String? = null
    lateinit var binding: ActivityDCSTokenNoBinding

    override fun updateTimerUi(l: Long) {
        // DO nothing
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_d_c_s_token_no, container, false)
        tokenNo_editText = binding.root.findViewById(R.id.tokenNo_editText)
        gotoBack = binding.root.findViewById(R.id.gotoBack)
        gotoBack!!.setOnClickListener(this)
        titleName = "Dealer Credit Sale"
        handleEnterKey()
        return binding.root
    }

    private fun callDCSEnterAmountActivity() {
        val tokenNo = tokenNo_editText!!.text.toString()
        if (!Validation.isvalidTokenNo(tokenNo.trim { it <= ' ' })) {
            Toast.makeText(context, "Please enter a valid Token No.", Toast.LENGTH_SHORT).show()
        } else {
            GlobalMethods.setTokenNumber(tokenNo)
            navController!!.navigate(R.id.amountEntryFragment)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> {
                hideKeypad()
                navController!!.navigate(R.id.action_main_fragment)
            }
            R.id.llayout_done -> {
                hideKeypad()
                callDCSEnterAmountActivity()
            }
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.tokenNoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.tokenNoEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.tokenNoEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.tokenNoEditText)
                callDCSEnterAmountActivity()
                true
            } else false
        })
    }
}