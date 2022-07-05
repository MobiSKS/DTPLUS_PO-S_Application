package com.paytm.hpclpos.ui.TerminalInputScreenFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.databinding.FragmentTerminalInputScreenBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment


class TerminalInputScreenFragment : BaseFragment(), View.OnClickListener  {

    lateinit var binding: FragmentTerminalInputScreenBinding

    override fun updateTimerUi(l: Long) {
     // Do nothing
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terminal_input_screen, container, false)
        if (TransactionUtils.getTerminalPin(requireContext(), Constants.TERMINAL_PIN)
                .equals("")) {
            ToastMessages.customMsgToast(
                requireContext(),
                "Please Set Terminal Pin before Registration"
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds()
    }

    private fun viewFinds() {
        binding.gotoBack.setOnClickListener(this)
        binding.lLcancl.setOnClickListener(this)
        binding.lLNext.setOnClickListener(this)
        if (TransactionUtils.getTerminalPin(requireContext(), Constants.TERMINAL_PIN)
                .equals("")) {
            handleEnterKey()
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.terminalPin.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.reEnterTerminalPin.setImeOptions(EditorInfo.IME_ACTION_DONE)
        binding.reEnterTerminalPin.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveTerminalPin()
                true
            } else false
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lLNext -> {
                hideKeypad()
                saveTerminalPin()
            }
            R.id.lLcancl, R.id.gotoBack -> {
                hideKeypad()
                requireActivity().onBackPressed()
            }
        }
    }

    private fun saveTerminalPin() {
        if (binding.terminalPin.text.toString().equals("", ignoreCase = true) &&
            binding.reEnterTerminalPin.text.toString().equals("", ignoreCase = true)) {
            binding.terminalPin.setError(getString(R.string.input_required))
            binding.reEnterTerminalPin.setError(getString(R.string.input_required))
        } else if (binding.terminalPin.text.toString().equals("", ignoreCase = true)) {
            binding.terminalPin.setError(getString(R.string.input_required))
        } else if (binding.reEnterTerminalPin.text.toString().equals("", ignoreCase = true)) {
            binding.reEnterTerminalPin.setError(getString(R.string.input_required))
        } else if (!binding.terminalPin.text.toString()
                .equals(binding.reEnterTerminalPin.text.toString(), ignoreCase = true)) {
            binding.reEnterTerminalPin.setError(resources.getString(R.string.mismatchterminalpin))
            ToastMessages.customMsgToast(context, resources.getString(R.string.mismatchterminalpin))
        } else {
            TransactionUtils.setTerminalPin(requireContext(),binding.terminalPin.text.toString(),Constants.TERMINAL_PIN)
            navController!!.popBackStack()
            sendMessage()
        }
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
    // be received by the ReceiverActivity.
    private fun sendMessage() {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("registration")
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!")
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
}