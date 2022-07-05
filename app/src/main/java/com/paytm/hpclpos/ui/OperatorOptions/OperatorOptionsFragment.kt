package com.paytm.hpclpos.ui.OperatorOptions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.Dialog.LoginDialogFragment
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.databinding.FragmentOperatorOptionsBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class OperatorOptionsFragment : BaseFragment() {

    lateinit var binding : FragmentOperatorOptionsBinding
    lateinit var recyclerView: RecyclerView
    var recyclerAdapter:RecyclerAdapter? = null
    lateinit var operatorOptionsViewModel: OperatorOptionsViewModel

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d("OperatorOptionsFragment","OnCreateView Called")
        operatorOptionsViewModel =
            ViewModelProviders.of(this@OperatorOptionsFragment).get(OperatorOptionsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operator_options, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("OperatorOptionsFragment","On Resume Called")
        binding.addOperator.setOnClickListener { navigate2LoginFragment(Constants.SIGNUP) }
        binding.backpress.setOnClickListener { navController!!.navigate(R.id.action_mainFragment_to_merchantServices) }
        handleOnBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        recyclerView = binding.root.findViewById(R.id.recyclerView)
        Log.d("OperatorOptionsFragment","onViewCreated Called")
        operatorOptionsViewModel.getAllOperatorsList(requireContext()).observe(viewLifecycleOwner, {
            recyclerAdapter = RecyclerAdapter(it, requireActivity(),navController)
            recyclerView.adapter = recyclerAdapter
            recyclerView.setLayoutManager(LinearLayoutManager(requireActivity()))
        })
    }

    private fun navigate2LoginFragment(type: String) {
        val bundle = Bundle()
        bundle.putString(Constants.NAV_VALUE, type)
        navController!!.navigate(R.id.action_login_fragment, bundle)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController!!.navigate(R.id.action_mainFragment_to_merchantServices)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("OperatorOptions","OnDestroy Called")
    }
}