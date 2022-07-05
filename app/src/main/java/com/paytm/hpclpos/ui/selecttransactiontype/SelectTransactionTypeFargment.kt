package com.paytm.hpclpos.ui.selecttransactiontype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.ui.enterdcstoken.DCSTokenNoFragment
import com.paytm.hpclpos.ui.selectproduct.NavigateListener
import com.paytm.hpclpos.ui.selectproduct.SelectProductAdapter
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.ActivitySelectTransactionTypeBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.posterminal.base.BaseFragment

class SelectTransactionTypeFargment : BaseFragment() {
    private var selectedTransType: String? = null

    lateinit var binding: ActivitySelectTransactionTypeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_select_transaction_type, container, false)
        viewFinds()
        return binding.root
    }

    private fun viewFinds() {
        //All click listner
        binding.gotoBack!!.setOnClickListener({
            showExitDialog()
        })
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val recyclerView = binding.root.findViewById(R.id.recyclerView) as RecyclerView
        val adapter =
            SelectProductAdapter(Constants.getAllSaleList(), requireActivity(),object : NavigateListener {
                override fun navigate(type: String) {
                    requireActivity().runOnUiThread {
                        GlobalMethods.setSaleType(type)
                        if (type.equals(SaleTransactionDetails.DEALER_CREDIT_SALE.saleName)) {
                            navController!!.navigate(R.id.action_dcs_token_number_fragment)
                        } else if (type.equals(SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName)) {
                            navController!!.navigate(R.id.action_fasttag_bank_name)
                        } else {
                            navController!!.navigate(R.id.action_Select_Transaction_Type_Fragment_to_Amount_Entry_Fragment)
                        }
                    }
                }
            })
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}