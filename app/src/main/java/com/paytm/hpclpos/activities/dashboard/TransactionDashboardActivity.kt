package com.paytm.hpclpos.activities.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.ActivityTransactionDashboardBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class TransactionDashboardActivity : BaseFragment() {
    var rvCardedAndMobile: RecyclerView? = null
    var rvNonCarded: RecyclerView? = null
    lateinit var binding: ActivityTransactionDashboardBinding
    private var cardedAndMobileAdapter: CardedAndMobileAdapter? = null
    private var nonCardAdapter: NonCardedAdapter? = null
    lateinit var cardedAndMobileModelList: MutableList<CardedAndMobileModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_transaction_dashboard, container, false)
        rvCardedAndMobile = binding.root.findViewById(R.id.rvCardedAndMobile)
        rvNonCarded = binding.root.findViewById(R.id.rvNonCarded)
        val gotoBack = binding.root.findViewById<View>(R.id.gotoBack) as ImageView
        gotoBack.setOnClickListener { gotoBack() }
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        cardedAndMobileDataSet()
        nonCardedDataSet()
    }

    private fun nonCardedDataSet() {
        cardedAndMobileModelList = ArrayList<CardedAndMobileModel>()
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Pay Merchant",
                ContextCompat.getDrawable(requireContext(),R.drawable.paymrchnt_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Credit Sale Complete",
                ContextCompat.getDrawable(requireContext(),R.drawable.creditsalecmplt_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Payback",
                ContextCompat.getDrawable(requireContext(),R.drawable.payback_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "CCMS Recharge",
                ContextCompat.getDrawable(requireContext(),R.drawable.ccmsrchrg_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "CCMS Balance",
                ContextCompat.getDrawable(requireContext(),R.drawable.ccmsbalance_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Loyalty Redeem",
                ContextCompat.getDrawable(requireContext(),R.drawable.redeemloyality_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Loyalty Balance",
                ContextCompat.getDrawable(requireContext(),R.drawable.loyalitybalance_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Card Fee",
                ContextCompat.getDrawable(requireContext(),R.drawable.cardfee_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Control Pin Change",
                ContextCompat.getDrawable(requireContext(),R.drawable.chngcontrolpin_icon)
            )
        )
        rvNonCarded!!.layoutManager = GridLayoutManager(requireContext(), 4)
        nonCardAdapter = NonCardedAdapter(
            requireContext(),
            cardedAndMobileModelList,
            requireActivity(),navController)
        rvNonCarded!!.adapter = nonCardAdapter
    }

    private fun cardedAndMobileDataSet() {
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Reload",
                ContextCompat.getDrawable(requireContext(),R.drawable.reload_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "CCMS Recharge",
                ContextCompat.getDrawable(requireContext(),R.drawable.reload_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Balance Transfer",
                ContextCompat.getDrawable(requireContext(),R.drawable.balancetransfer_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Void",
                ContextCompat.getDrawable(requireContext(),R.drawable.void_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Tracking",
                ContextCompat.getDrawable(requireContext(),R.drawable.tracking_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Loyalty",
                ContextCompat.getDrawable(requireContext(),R.drawable.redeemloyality_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "OTC/Driver Redeem",
                ContextCompat.getDrawable(requireContext(),R.drawable.otc_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Balance Enquiry",
                ContextCompat.getDrawable(requireContext(),R.drawable.balanceenquiry_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Change Card Pin",
                ContextCompat.getDrawable(requireContext(),R.drawable.chngcardpin_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Unblock Card Pin",
                ContextCompat.getDrawable(requireContext(),R.drawable.unblockcardpin_icon)
            )
        )
        cardedAndMobileModelList.add(
            CardedAndMobileModel(
                "Card Unblocking",
                ContextCompat.getDrawable(requireContext(),R.drawable.cardunblocking_icon)
            )
        )
        rvCardedAndMobile!!.layoutManager = GridLayoutManager(requireContext(), 4)
        cardedAndMobileAdapter = CardedAndMobileAdapter(
            requireContext(),
            cardedAndMobileModelList,
            requireActivity(),navController!!
        )
        rvCardedAndMobile!!.adapter = cardedAndMobileAdapter
    }

    private fun gotoBack() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        requireActivity().finish()
    }

    override fun onResume() {
        super.onResume()
        GlobalMethods.setCardInfoEntity(null)
    }
}