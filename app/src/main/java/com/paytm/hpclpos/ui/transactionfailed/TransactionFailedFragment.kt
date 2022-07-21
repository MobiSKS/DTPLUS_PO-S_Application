package com.paytm.hpclpos.ui.transactionfailed

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.databinding.ActivityTransactionFailedBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class TransactionFailedFragment : BaseFragment() {
    var tvErrorMessage: TextView? = null
    lateinit var binding: ActivityTransactionFailedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_transaction_failed, container, false)
        val lLgoToMain = binding.root.findViewById<View>(R.id.lLgoToMain) as LinearLayout
        lLgoToMain.setOnClickListener {
            goToMainActivity()
        }
        tvErrorMessage = binding.root.findViewById(R.id.tvErrorMessage)
        val errorMessage = arguments?.getString(Constants.LIMITEXCEED)
        tvErrorMessage!!.setText(errorMessage)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToMainActivity()
            }
        })
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        transResultTimer.start()
    }

    private fun goToMainActivity() {
        transResultTimer.cancel()
        navController!!.navigate(R.id.action_main_fragment)
    }

    val transResultTimer = object : CountDownTimer(10000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            requireActivity().runOnUiThread({
                binding.backToMainMenu.setText("${getString(R.string.back_to_main_menu)} ${millisUntilFinished.toString()
                    .substring(0, millisUntilFinished.toString().length - 3)}")
            })
        }

        override fun onFinish() { requireActivity().runOnUiThread{ goToMainActivity() } }
    }
}