package com.paytm.hpclpos.ui.terminalsettingfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.databinding.FragmentTerminalSettingBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.ui.amount.AmountViewModel
import com.paytm.hpclpos.ui.displayparameters.DisplayParameterAdapter
import com.paytm.hpclpos.ui.displayparameters.DisplayParameterViewModel

class TerminalSettingFragment : BaseFragment(){

    lateinit var binding: FragmentTerminalSettingBinding
    lateinit var amountViewModel: AmountViewModel

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terminal_setting, container, false)
        amountViewModel = ViewModelProviders.of(requireActivity())[AmountViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireView().findViewById(R.id.recyclerView) as RecyclerView
        val adapter = TerminalSettingAdapter(
            DisplayParameterViewModel
                .getTerminalSettingData(requireContext()),requireActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        binding.confirm.setOnClickListener({ navController!!.popBackStack() })
    }
}