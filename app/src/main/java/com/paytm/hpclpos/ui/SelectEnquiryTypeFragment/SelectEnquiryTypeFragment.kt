package com.paytm.hpclpos.ui.SelectEnquiryTypeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.cardredoptions.*
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.databinding.FragmentSelectEnquiryTypeBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class SelectEnquiryTypeFragment : BaseFragment(), View.OnClickListener,CardSuccessListener {

    lateinit var binding: FragmentSelectEnquiryTypeBinding
    var cardOptions: CardOptions? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_enquiry_type, container, false)
        return binding.root
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
        binding.lLcardlesstransaction.setOnClickListener(this)
        binding.cardSwipeTransaction.setOnClickListener(this)
        binding.llChipCardRead.setOnClickListener(this)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CardOptions.closeIccAndMag()
                navController!!.navigate(R.id.action_main_fragment)
            }
        })
    }

    private fun callEnterMobNoActivity() {
          CardOptions.closeIccAndMag()
          navController!!.navigate(R.id.action_enterMobileNumberFragment)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lLcardlesstransaction -> callEnterMobNoActivity()
        }
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

    override fun performActionSuccess(cardInfoEntity: CardInfoEntity) {
        CardOptions.closeIccAndMag()
        if(isAdded) {
            GlobalMethods.setCardInfoEntity(cardInfoEntity)
            requireActivity().runOnUiThread({
                navController!!.navigate(R.id.action_enterCardPinFragment)
            })
        }
    }
}