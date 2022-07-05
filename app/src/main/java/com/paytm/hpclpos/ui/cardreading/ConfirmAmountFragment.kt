package com.paytm.hpclpos.ui.cardreading

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity
import com.paytm.hpclpos.cardredoptions.*
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.databinding.ActivityConfirmAmountBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class ConfirmAmountFragment : BaseFragment(), View.OnClickListener,CardSuccessListener {

    lateinit var binding: ActivityConfirmAmountBinding

    var cardOptions: CardOptions? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_confirm_amount, container, false)
        viewFinds()
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
     // Do nothing
    }

    override fun onResume() {
        super.onResume()
        displayCardOptions()
    }

    fun displayCardOptions() {
        if (GlobalMethods.getSaleType()!!
                .equals(SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName)) {
            binding.llChipCardRead.visibility = View.GONE
            binding.cardSwipeTransaction.visibility = View.GONE
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1.0f
                gravity = Gravity.CENTER
            }
            binding.lLcardlesstransaction.layoutParams = params
            binding.textHintforAmount.setText(getText(R.string.enter_mobile_no))
        } else {
            cardOptions = CardOptions(requireActivity(), object : CardEventListener {
                override fun onCardEvent(state: CardEventState?) {
                    handleCardReadError(state)
                }

                override fun onCardReadSuccess() {
                    requireActivity().runOnUiThread({
                        ToastMessages.customMsgToastShort(requireActivity(), "Card Read Success")
                    })
                }
            }, this)
        }
    }

    private fun viewFinds() {
        binding.lLcardlesstransaction.setOnClickListener(this)
        binding.cardSwipeTransaction.setOnClickListener(this)
        binding.llChipCardRead.setOnClickListener(this)
        Log.e("get_amount", "" + GlobalMethods.getAmount())
        binding.rcvAmountText.setText(GlobalMethods.getAmount())
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CardOptions.closeIccAndMag()
                ToastMessages.customMsgToastShort(requireContext(),getString(R.string.transaction_cancelled))
                navController!!.navigate(R.id.action_main_fragment)
            }
        })
    }

    private fun callEnterMobNoActivity() {
        navController!!.navigate(R.id.action_enterMobileNumberFragment)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lLcardlesstransaction -> {
                CardOptions.closeIccAndMag()
                callEnterMobNoActivity()
            }
        }
    }

    override fun performActionSuccess(cardInfoEntity: CardInfoEntity) {
        CardOptions.closeIccAndMag()
        if(isAdded) {
            GlobalMethods.setCardInfoEntity(cardInfoEntity)
            requireActivity().runOnUiThread({
                navController!!.navigate(R.id.action_ConfirmCardFragment) })
        }
    }

    fun handleCardReadError(state: CardEventState?) {
        when (state!!.state) {
            CardEventState.SWIPE_INCORRECT ->   requireActivity().runOnUiThread({
                ToastMessages.customMsgToast(requireActivity(), "Card Read Failed")
                CardOptions.closeIccAndMag()
                navController!!.navigate(R.id.action_main_fragment)
            })

            CardEventState.CARD_REMOVED_READ_WHILE_READING ->  requireActivity().runOnUiThread {
                ToastMessages.customMsgToast(requireActivity(), "Card Removed while Reading")
                CardOptions.closeIccAndMag()
                navController!!.navigate(R.id.action_main_fragment)
            }
        }
    }
}