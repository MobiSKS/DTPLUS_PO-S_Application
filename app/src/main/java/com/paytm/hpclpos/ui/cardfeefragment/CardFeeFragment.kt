package com.paytm.hpclpos.ui.cardfeefragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.databinding.FragmentCardFeeBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.ui.amount.AmountViewModel
import java.math.BigDecimal


class CardFeeFragment : BaseFragment() {
    private var formNo_EditText: EditText? = null
    private var noOfCards_EditText: EditText? = null
    private var amountViewModel: AmountViewModel? = null
    lateinit var binding : FragmentCardFeeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_fee, container, false)
        binding.root.setOnTouchListener { view, motionEvent -> true }
        amountViewModel = ViewModelProviders.of(requireActivity())[AmountViewModel::class.java]
        formNo_EditText = binding.root.findViewById<View>(R.id.formNo_EditText) as EditText
        textWatcherListener(formNo_EditText!!)
        noOfCards_EditText = binding.root.findViewById<View>(R.id.noOfCards_EditText) as EditText
        val gotoBack = binding.root.findViewById<View>(R.id.gotoBack) as ImageView
        val lLNext = binding.root.findViewById<View>(R.id.lLNext) as LinearLayout
        val lLcancl = binding.root.findViewById<View>(R.id.lLcancl) as LinearLayout
        lLcancl.setOnClickListener {
            hideKeypad()
            goToMainFragment()
        }
        lLNext.setOnClickListener {
            hideKeypad()
            callCnfrmAmntFrag()
        }
        gotoBack.setOnClickListener {
            hideKeypad()
            goToMainFragment()
        }
        handleOnBackPressed()
        handleEnterKey()
        return binding.root
    }

    fun textWatcherListener(formNo_EditText: EditText?) {
        formNo_EditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().length == 1 && s.toString().startsWith("0")) {
                    s.clear()
                }
            }
        })
    }

    private fun callCnfrmAmntFrag() {
        val formNo = formNo_EditText!!.text.toString()
        val numberOFCard = noOfCards_EditText!!.text.toString()
        if (formNo_EditText!!.text.toString().isBlank()) {
            Toast.makeText(context, "Please enter a valid Form Number.", Toast.LENGTH_SHORT).show()
        } else if (numberOFCard == "") {
            Toast.makeText(context, "Please enter Number of Card.", Toast.LENGTH_SHORT).show()
        } else {
            var amount = 50.00
            val cardAmount: String
            amount = amount * numberOFCard.toDouble()
            cardAmount = amount.toString()
            amountViewModel!!.amountDisplayText = "₹" + cardAmount
            val amounts: Double = cardAmount.replace(",", "").toDouble()
            var decimal = BigDecimal.valueOf(amounts)
            decimal = decimal.multiply(BigDecimal(amountViewModel!!.divider))
            amountViewModel!!.amount = decimal.toLong()
            GlobalMethods.setAmountViewModel(amountViewModel!!)
            getcardfee(formNo, numberOFCard, cardAmount)
        }
    }

    private fun getcardfee(formNo: String, noOfCard: String, cardAmount: String) {
        val bundle = Bundle()
        bundle.putString(Constants.FORMNUMBER, formNo)
        bundle.putString(Constants.NUMBEROFCARDS, noOfCard)
        bundle.putString(Constants.CARDAMOUNTS, cardAmount)
        navController!!.navigate(R.id.action_card_fee_confirm_amount_fragment,bundle)
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback( viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { goToMainFragment() }
            })
    }

    fun goToMainFragment() {
        ToastMessages.customMsgToast(requireContext(),getString(R.string.transaction_cancelled))
        navController!!.navigate(R.id.action_main_fragment)
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.formNoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.noOfCardsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.noOfCardsEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.noOfCardsEditText)
                callCnfrmAmntFrag()
                true
            } else false
        })
    }
}
