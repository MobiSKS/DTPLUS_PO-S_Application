package com.paytm.hpclpos.ui.paybackamountpointscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.paytm.hpclpos.R
import com.paytm.hpclpos.databinding.FragmentPaybackAmountpointScreenBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class PaybackAmountPointFragment : BaseFragment() {
    lateinit var binding: FragmentPaybackAmountpointScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payback_amountpoint_screen, container, false)
        //binding.payBackAmount.setText(" ")
        //binding.balancePoints.setText(" ")
        handleOnBackPressed()
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.loginconfirm.setOnClickListener({
            navController?.navigate(R.id.action_amount_entry_fragment)
        })

        binding.logincancel.setOnClickListener({
            goToMainActivity()
        })
    }

    private fun goToMainActivity() {
        navController!!.navigate(R.id.action_main_fragment)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goToMainActivity()
                }
            })
    }
}