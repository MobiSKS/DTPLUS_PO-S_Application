package com.paytm.hpclpos.ui.payback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.FragmentPaybackMainBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class PayBackMainFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding: FragmentPaybackMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payback_main, container, false)
        binding.root.setOnTouchListener { view, motionEvent -> true }
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds()
    }

    private fun viewFinds() {
        binding.pbEarn.setOnClickListener(this)
        binding.pbBurn.setOnClickListener(this)
        binding.pbVoid.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.pbEarn -> {
                GlobalMethods.setSaleType(SaleTransactionDetails.PAYBACK_EARN.saleName)
                callPresentCardFragment()
            }

            R.id.pbBurn -> {
                GlobalMethods.setSaleType(SaleTransactionDetails.PAYBACK_BURN.saleName)
                callPresentCardFragment()
            }

            R.id.pbVoid -> {
                GlobalMethods.setSaleType(SaleTransactionDetails.PAYBACK_VOID.saleName)
                callPresentCardFragment()
            }
        }
    }

    private fun callPresentCardFragment() {
        navController?.navigate(R.id.action_present_card)
    }
}