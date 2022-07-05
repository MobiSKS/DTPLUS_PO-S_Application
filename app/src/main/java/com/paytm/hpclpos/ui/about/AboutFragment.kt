package com.paytm.hpclpos.ui.about

import com.paytm.hpclpos.posterminal.base.BaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.paytm.hpclpos.R
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.printreceipts.AboutReciept
import com.paytm.hpclpos.ui.displayparameters.DisplayParameterAdapter
import com.paytm.hpclpos.ui.displayparameters.DisplayParameterViewModel

class AboutFragment : BaseFragment() {

    override fun updateTimerUi(l: Long) {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_about, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val recyclerView = requireView().findViewById(R.id.recyclerView) as RecyclerView
        val adapter = DisplayParameterAdapter(
            DisplayParameterViewModel
            .getAboutFragmentDetails(requireContext()),requireActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewsFind(view)
    }

    fun viewsFind(view: View) {
        view.findViewById<TextView>(R.id.printAboutReceipt).setOnClickListener({
             AboutReciept(requireActivity()).printReceipt()
        })
    }
}