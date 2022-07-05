package com.paytm.hpclpos.fragmentscardedandmobtrans.reload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.TitleName
import com.paytm.hpclpos.databinding.FragmentReloadMainBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class ReloadMainFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding: FragmentReloadMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reload_main, container, false)
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
        binding.rlCashReld.setOnClickListener(this)
        binding.rlChequeReld.setOnClickListener(this)
        binding.rlCcmsReld.setOnClickListener(this)
        binding.gotoBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity().onBackPressed()
            R.id.rlCashReld -> {
                displayDialog(
                    "Remind",
                    "This Transaction is for payment received by CASH",
                    object : DialogUtil.OnClickListener {
                        override fun onConfirm() {
                            GlobalMethods.setTransType("RELOAD")
                            GlobalMethods.setSaleType(SaleTransactionDetails.CASH_RELOAD.saleName)
                            TitleName.titleName = resources.getString(R.string.cashreload)
                            callCcsmsFragment()
                        }

                        override fun onCancel() {
                            // Do nothing
                        }
                    })
            }
            R.id.rlChequeReld -> {
                displayDialog(
                    "Remind",
                    "This Transaction is for payment received by CHEQUE",
                    object : DialogUtil.OnClickListener {
                        override fun onConfirm() {
                            GlobalMethods.setTransType("RELOAD")
                            GlobalMethods.setSaleType(SaleTransactionDetails.CHEQUE_RELOAD.saleName)
                            TitleName.titleName = resources.getString(R.string.chequereload)
                            callEnterchecknoandmicr()
                        }

                        override fun onCancel() {
                            // Do nothing
                        }
                    })
            }
            R.id.rlCcmsReld -> {
                GlobalMethods.setTransType("RELOAD")
                GlobalMethods.setSaleType(SaleTransactionDetails.CCMS_RELOAD.saleName)
                TitleName.titleName = resources.getString(R.string.ccmsreload)
                callCcsmsFragment()
            }
        }
    }

    private fun callCcsmsFragment() {
        navController!!.navigate(R.id.action_cashReload)
    }

    private fun callEnterchecknoandmicr() {
        navController!!.navigate(R.id.action_chequeReload)
    }
}