package com.paytm.hpclpos.ui.amount

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivityCcsmsSaleBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment
import java.math.BigDecimal
import java.util.*

class AmountEntryFragment : BaseFragment() {

    lateinit var binding: ActivityCcsmsSaleBinding
    private var rcvAmount: String? = null
    private var textToSpeech: TextToSpeech? = null
    private var amountViewModel: AmountViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_ccsms_sale, container, false)
        amountViewModel = ViewModelProviders.of(requireActivity())[AmountViewModel::class.java]
        viewFinds()
        textToSpeech(binding.root)
        return binding.root
    }

    private fun viewFinds() {
        binding.tvTitle.text = GlobalMethods.getSaleType()
        binding.lLconfirm.setOnClickListener({ callConfirmAmountActivity() })
       // binding.gotoBack.setOnClickListener({ /*goToMainFragment()*/ })
        binding.amountEntryCancelButton.setOnClickListener({ goToMainFragment() })
        handleOnBackPressed()
    }

    private fun textToSpeech(v: View) {
        textToSpeech = TextToSpeech(context, object : TextToSpeech.OnInitListener {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech!!.setLanguage(Locale.US)
                    val utteranceId = this.hashCode().toString() + ""
                    val speechStatus = textToSpeech!!.speak(
                        "Please Enter Amount", TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!")
                    }
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                        || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        Log.e("TTS", "The Language is not supported!")
                    } else {
                        Log.i("TTS", "Language Supported.")
                    }
                    Log.i("TTS", "Initialization success.")
                } else {
                    Toast.makeText(context, "TTS Initialization failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.enterAmountText.setText(rcvAmount)
        binding.enterAmountText.addTextChangedListener(AmountTextWatcher(binding, amountViewModel!!))
        // pass the InputConnection from the EditText to the keyboard
        val keyboard = v.findViewById<View>(R.id.keyboard) as MyKeyboard
        val ic = binding.enterAmountText.onCreateInputConnection(EditorInfo())
        keyboard.setInputConnection(ic)
    }

    private fun callConfirmAmountActivity() {
        amountViewModel!!.amountDisplayText = "₹" + binding.enterAmountText.text.toString()
        if(binding.enterAmountText.text.toString().equals("")) {
            Toast.makeText(context, "Please Enter a Valid Amount.", Toast.LENGTH_SHORT).show()
        } else {
            val amounts: Double = binding.enterAmountText.text.toString().replace(",","").toDouble()
            var decimal = BigDecimal.valueOf(amounts)
            decimal = decimal.multiply(BigDecimal(amountViewModel!!.divider))
            amountViewModel!!.amount = decimal.toLong()
            if (amountViewModel!!.amount > 0) {
                GlobalMethods.setAmount(binding.enterAmountText.text.toString())
                GlobalMethods.setAmountViewModel(amountViewModel!!)
                checkTransAndNavigate()
            } else {
                Toast.makeText(context, "Please Enter a Valid Amount.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkTransAndNavigate() {
        when (GlobalMethods.getTransType()) {
            SaleTransactionDetails.CCMS_CASHRECHARGE.category,
            SaleTransactionDetails.CASH_RELOAD.category -> {
                showTerminalPinDialog(dialogListener)
            }
            else -> navigateAmountConfirmFragment()
        }
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    fun showTerminalPinDialog(dialogListener: DialogUtil.TerminalPinOnClickListener) {
        DialogUtil.showTerminalPinDialog(requireContext(),dialogListener)
    }

    fun navigateCardConfirmFragment() {
        requireActivity().runOnUiThread({ navController!!.navigate(R.id.action_cardConfirmFragment) })
    }

    fun navigateAmountConfirmFragment() {
        requireActivity().runOnUiThread({ navController!!.navigate(R.id.action_amountConfirmFragment) })
    }

    val dialogListener = object : DialogUtil.TerminalPinOnClickListener {
        override fun onConfirm(pinPassword: String) {
            if (TransactionUtils.getTerminalPin(requireContext(), Constants.TERMINAL_PIN).equals(pinPassword)) {
                if (GlobalMethods.getTransType().equals(SaleTransactionDetails.CCMS_CASHRECHARGE.category) &&
                    GlobalMethods.getCardInfoEntity() != null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
                    navigateCardConfirmFragment()
                } else { navigateAmountConfirmFragment() }
            } else {
                ToastMessages.customMsgToast(requireContext(),"Incorrect Terminal Password")
            }
        }

        override fun onCancel() {
            // Do nothing
        }
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback( viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { goToMainFragment() }
            })
    }

    fun goToMainFragment() {
        ToastMessages.customMsgToast(requireContext(),"Transaction Cancelled")
        navController!!.navigate(R.id.action_main_fragment)
    }
}
