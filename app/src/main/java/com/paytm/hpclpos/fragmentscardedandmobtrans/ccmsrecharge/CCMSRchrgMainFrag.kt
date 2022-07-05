package com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.paytm.hpclpos.R
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.FragmentCCMSRchrgMainBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class CCMSRchrgMainFrag : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentCCMSRchrgMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_c_c_m_s_rchrg_main, container, false)
        binding.rlSelectCash.setOnClickListener { callEnterCashAmntFrag() }
        binding.rLSelectCheque.setOnClickListener { callChequeMainFrag() }
        binding.rLSelectNEFT.setOnClickListener { callNEFTMainFrag() }
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.setOnTouchListener { view, motionEvent -> true }
        binding.gotoBack.setOnClickListener(this)
    }

    private fun callEnterCashAmntFrag() {
        displayDialog(getString(R.string.remind),"${getString(R.string.message)} CASH" ,object : DialogUtil.OnClickListener{
            override fun onConfirm() {
                GlobalMethods.setTransType(SaleTransactionDetails.CCMS_CASHRECHARGE.category)
                GlobalMethods.setSaleType(SaleTransactionDetails.CCMS_CASHRECHARGE.saleName)
                if (GlobalMethods.getCardInfoEntity() != null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
                    navController!!.navigate(R.id.action_ccmsRecharge_non_carded_cash)
                } else {
                    navController!!.navigate(R.id.action_ccmsRecharge_cash)
                }
            }

            override fun onCancel() {
                // Do nothing
            }
        })
    }

    private fun callChequeMainFrag() {
        displayDialog(getString(R.string.remind),"${getString(R.string.message)} CHEQUE" ,object : DialogUtil.OnClickListener{
            override fun onConfirm() {
                GlobalMethods.setTransType(SaleTransactionDetails.CCMS_CASHRECHARGE.category)
                GlobalMethods.setSaleType(SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName)
                if (GlobalMethods.getCardInfoEntity()!=null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
                    navController!!.navigate(R.id.action_ccmsRecharge_non_carded_cheque)
                } else {
                    navController!!.navigate(R.id.action_cheque_and_micr)
                }
            }

            override fun onCancel() {
                // Do nothing
            }
        })
    }

    private fun callNEFTMainFrag() {
        displayDialog(getString(R.string.remind),"${getString(R.string.message)} NEFT/RTGS" ,object : DialogUtil.OnClickListener{
            override fun onConfirm() {
                GlobalMethods.setTransType(SaleTransactionDetails.CCMS_CASHRECHARGE.category)
                GlobalMethods.setSaleType(SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName)
                if (GlobalMethods.getCardInfoEntity()!= null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
                    navController!!.navigate(R.id.action_ccmsRecharge_non_carded_neft)
                } else {
                    navController!!.navigate(R.id.action_neft_fragment)
                }
            }

            override fun onCancel() {
                // Do nothing
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity().onBackPressed()
        }
    }
}