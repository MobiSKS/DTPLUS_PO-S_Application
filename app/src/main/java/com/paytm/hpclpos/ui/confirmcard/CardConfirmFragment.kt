package com.paytm.hpclpos.ui.confirmcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.databinding.FragmentCardConfirmFragmentBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment


class CardConfirmFragment  : BaseFragment()  {

    lateinit var binding: FragmentCardConfirmFragmentBinding
    override fun updateTimerUi(l: Long) {
         // Do nothing
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        var cardType = ""
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_confirm_fragment, container, false)
        var cardNumber: String? = null
        if (GlobalMethods.getTransType().equals(SaleTransactionDetails.CCMS_CASHRECHARGE.category)
            && GlobalMethods.getCardInfoEntity() != null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
            cardType = "Control Card No"
            cardNumber = GlobalMethods.getControlCardNumber()!!
        } else {
            cardType = "Card No"
            if (GlobalMethods.getCardInfoEntity() != null) {
                cardNumber = GlobalMethods.getCardInfoEntity()?.cardNo!!
            } else {
                navController!!.navigate(R.id.action_main_fragment)
            }
        }
        showDialogForCardCOnfirm(cardType, getString(R.string.confirm_transaction), cardNumber!!, GlobalMethods.getSaleType()!!,
            GlobalMethods.getAmount()!!, object : DialogUtil.OnClickListener {
                override fun onConfirm() {
                    navController!!.navigate(R.id.action_enterCardPinFragment)
                    dialog?.cancel()
                }

                override fun onCancel() {
                    goToMainFragment()
                    dialog?.cancel()
                }
            })
        return binding.root
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback( viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { goToMainFragment() }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
    }

    private fun goToMainFragment() {
        ToastMessages.customMsgToastShort(requireContext(),getString(R.string.transaction_cancelled))
        navController!!.navigate(R.id.action_main_fragment)
    }
}