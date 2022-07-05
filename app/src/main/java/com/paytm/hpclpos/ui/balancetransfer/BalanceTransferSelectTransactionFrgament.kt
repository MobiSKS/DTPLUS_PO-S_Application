package com.paytm.hpclpos.ui.balancetransfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.databinding.FragmentBalanceTransferSelectTransactionFragmentBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class BalanceTransferSelectTransactionFrgament : BaseFragment(){

    lateinit var binding: FragmentBalanceTransferSelectTransactionFragmentBinding
    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_balance_transfer_select_transaction_fragment, container, false)
        viewFinds()
        return binding.root
    }

    private fun viewFinds() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { goToMainFragment() }})
        binding.gotoBack.setOnClickListener({ goToMainFragment() })
        binding.ccmsToCard.setOnClickListener({
           // GlobalMethods.setSaleType(SaleTransactionDetails.CCMS_TO_CARD.saleName)
            callAmountEntryFragment()
        })
        binding.cardToCcms.setOnClickListener({
            // GlobalMethods.setSaleType(SaleTransactionDetails.CARD_TO_CCMS.saleName)
            callAmountEntryFragment()
        })
    }

    fun callAmountEntryFragment() {
        navController!!.navigate(R.id.amountEntryFragment)
    }

    fun goToMainFragment() {
        ToastMessages.customMsgToast(requireContext(),getString(R.string.transaction_cancelled))
        navController!!.navigate(R.id.action_main_fragment)
    }
}