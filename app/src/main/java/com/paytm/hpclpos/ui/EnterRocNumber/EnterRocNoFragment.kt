package com.paytm.hpclpos.ui.EnterRocNumber

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.paytm.hpclpos.R
import androidx.databinding.DataBindingUtil
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.FragmentRocNoBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class EnterRocNoFragment : BaseFragment() {

    lateinit var binding: FragmentRocNoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("oncreate", ":: Called")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_roc_no, container, false)
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEnterKey()
    }

    private fun enterAmountCall() {
         if (binding.rocNoEditText.getText().toString().equals(binding.reEnterrocNoEditText.getText().toString(),true)) {
            if (Validation.isValidRocNo(binding.rocNoEditText.getText().toString())) {
               getTransactionForRoc()
            } else {
                ToastMessages.customMsgToast(requireContext(),getString(R.string.entervalirocno))
            }
        } else {
             ToastMessages.customMsgToast(requireContext(),getString(R.string.mismatchrocno))
        }
    }

    fun getTransactionForRoc() {
        val db = AppRepository(requireContext())
        val transaction = db.getTransactionBasedOnId(binding.rocNoEditText.getText().toString().toInt())
        if (transaction != null) {
            dialogCnfrmAmnt(transaction.transaction_Amount!!)
        } else {
            ToastMessages.customMsgToast(requireContext(),"ROC not found")
        }
    }

    private fun dialogCnfrmAmnt(amount: String) {
        DialogUtil.showOkDialog(requireContext(),"CONFIRM AMOUNT","${Constants.RUPEEE_SYMBOL} ${amount}",object : DialogUtil.OnClickListener{
            override fun onConfirm() {
                if (GlobalMethods.getCardInfoEntity()!!.isMobileTransaction) {
                    DialogUtil.showTerminalPinDialog(requireContext(), dialogListener)
                } else {
                    requireActivity().runOnUiThread({ navController?.navigate(R.id.action_enterCardPinFragment) })
                }
            }

            override fun onCancel() { DialogUtil.dismissOkDialog() }
        })
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.rocNoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.reEnterrocNoEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.reEnterrocNoEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.reEnterrocNoEditText)
                enterAmountCall()
                true
            } else false
        })
    }

    val dialogListener = object : DialogUtil.TerminalPinOnClickListener {
        override fun onConfirm(pinPassword: String) {
            if (TransactionUtils.getTerminalPin(requireContext(), Constants.TERMINAL_PIN).equals(pinPassword)) {
                navController?.navigate(R.id.action_enterOtpFragment)
            } else {
                ToastMessages.customMsgToast(requireContext(),"Incorrect Terminal Password")
            }
        }

        override fun onCancel() {
            // Do nothing
        }
    }
}