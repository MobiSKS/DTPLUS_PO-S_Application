package com.paytm.hpclpos.ui.selectproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.ActivitySelectProductBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class SelectProductFragment : BaseFragment() {
    lateinit var binding: ActivitySelectProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.activity_select_product, container, false)
        viewFinds()
        return binding.root
    }

    private fun viewFinds() {
       handleOnBackPressed()
    }

    override fun updateTimerUi(l: Long) {
       requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
           .substring(0, l.toString().length - 3) +" s")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val recyclerView = binding.root.findViewById(R.id.recyclerView) as RecyclerView
        val adapter = SelectProductAdapter(Constants.getAllProductsList(), requireActivity(),object : NavigateListener {
                override fun navigate(type: String) {
                    GlobalMethods.setProductType(type)
                    navController!!.navigate(R.id.action_sale_Product_Fragment_to_Select_Transaction_Type_Fragment)
                }
            })
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}