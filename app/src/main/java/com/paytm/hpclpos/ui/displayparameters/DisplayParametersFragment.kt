package com.paytm.hpclpos.ui.displayparameters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.posterminal.base.BaseFragment

class DisplayParametersFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_display_parameters, container, false)
        return rootView
    }

    override fun updateTimerUi(l: Long) {
       // Do nothing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val recyclerView = requireView().findViewById(R.id.recyclerView) as RecyclerView
        val adapter = DisplayParameterAdapter(DisplayParameterViewModel
        .getDisplayParameterData(requireContext()),requireActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        view.findViewById<LinearLayout>(R.id.gotoBack).setOnClickListener({
            navController!!.popBackStack()
        })
    }
}