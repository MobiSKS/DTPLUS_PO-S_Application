package com.paytm.hpclpos.ui.PresentCardFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity
import com.paytm.hpclpos.cardredoptions.*
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.databinding.FragmentPresentCardTypeBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class PresentCardFragment : BaseFragment(),View.OnClickListener, CardSuccessListener {

    lateinit var presentCardFragment: FragmentPresentCardTypeBinding
    var cardOptions: CardOptions? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        presentCardFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_present_card_type, container, false)
        return presentCardFragment.root
    }

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        presentCardFragment.cardSwipeTransaction.setOnClickListener(this)
        presentCardFragment.llChipCardRead.setOnClickListener(this)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CardOptions.closeIccAndMag()
                navController!!.navigate(R.id.action_main_fragment)
            }
        })
    }

     override fun onClick(v: View) {
       // Do nothing
    }

    override fun onResume() {
        super.onResume()
        cardOptions = CardOptions(requireActivity(), object : CardEventListener {
            override fun onCardEvent(state: CardEventState?) {
                handleCardReadError(state)
            }

            override fun onCardReadSuccess() {
                requireActivity().runOnUiThread({ ToastMessages.customMsgToast(requireActivity(), "Card Read Success") })
            }
        }, this)
    }

     override fun performActionSuccess(cardInfoEntity: CardInfoEntity) {
        CardOptions.closeIccAndMag()
        if(isAdded) {
            GlobalMethods.setCardInfoEntity(cardInfoEntity)
            requireActivity().runOnUiThread({
                when(GlobalMethods.getSaleType()) {
                    SaleTransactionDetails.UNBLOCK_CARD_PIN.saleName -> {
                        navController!!.navigate(R.id.action_enter_newpin)
                    }

                    SaleTransactionDetails.CHANGE_CARD_PIN.saleName -> {
                        navController!!.navigate(R.id.action_change_card_pin_fragment)
                    }
                }
            })
        }
    }

    fun handleCardReadError(state: CardEventState?) {
        when (state!!.state) {
            CardEventState.SWIPE_INCORRECT ->   requireActivity().runOnUiThread({
                ToastMessages.customMsgToast(requireActivity(), "Card Read Failed")
                navController!!.navigate(R.id.action_main_fragment)
            })

            CardEventState.CARD_REMOVED_READ_WHILE_READING ->  requireActivity().runOnUiThread {
                ToastMessages.customMsgToast(requireActivity(), "Card Removed while Reading")
                navController!!.navigate(R.id.action_main_fragment)
            }
        }
    }
}