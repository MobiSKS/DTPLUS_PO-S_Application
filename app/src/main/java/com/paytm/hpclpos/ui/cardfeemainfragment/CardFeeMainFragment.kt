package com.paytm.hpclpos.ui.cardfeemainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import butterknife.ButterKnife
import com.paytm.hpclpos.R
import com.paytm.hpclpos.databinding.FragmentCardFeeMainBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class CardFeeMainFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentCardFeeMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //val rootView = inflater.inflate(R.layout.fragment_card_fee_main, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_fee_main, container, false)
        binding.root.setOnTouchListener { view, motionEvent -> true }
        ButterKnife.bind(this, binding.root)
        viewFinds(binding.root)
        return binding.root
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }

        //All click listner
        binding.gotoBack!!.setOnClickListener(this)
        binding.rLDTPLUS!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity()!!.onBackPressed()
            R.id.rLDTPLUS -> goToCardFeeFrag()
        }
    }

    private fun goToCardFeeFrag() {
       //navController!!.navigate(R.id.action_amountEntryFragment)
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }
}