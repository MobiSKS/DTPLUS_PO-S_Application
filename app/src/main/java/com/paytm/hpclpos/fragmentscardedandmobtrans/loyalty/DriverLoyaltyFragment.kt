package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.SharedPreferencesData
import com.paytm.hpclpos.databinding.FragmentDriverLoyaltyBinding

class DriverLoyaltyFragment : Fragment(), View.OnClickListener {

    var cardedNonCardSharedPref: SharedPreferencesData? = null

    lateinit var binding: FragmentDriverLoyaltyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_driver_loyalty,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        binding.gotoBack!!.setOnClickListener(this)
        cardedNonCardSharedPref = SharedPreferencesData(context)
        cardedNonCardSharedPref!!.createNewSharedPreferences(Constants.CARDEDNONCARDEDPREF)
        binding.rlDTP!!.setOnClickListener(this)
        binding.rlNonDTP!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity()!!.onBackPressed()
            R.id.rlDTP -> callDtpFragment()
            R.id.rlNonDTP -> callNonDTPFragment()
        }
    }

    private fun callDtpFragment() {
        cardedNonCardSharedPref!!.setSharedPreferenceData(Constants.CARDEDNONCARDEDPREF, Constants.LOYALTYDTPNONDTP, "DTP")
        val fragment: Fragment = VoidFragment()
        requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
    }

    private fun callNonDTPFragment() {
        cardedNonCardSharedPref!!.setSharedPreferenceData(Constants.CARDEDNONCARDEDPREF, Constants.LOYALTYDTPNONDTP, "NONDTP")
        val fragment: Fragment = CcmsSaleFragment()
        requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
    }

}