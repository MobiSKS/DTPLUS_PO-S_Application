package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard
import com.paytm.hpclpos.constants.SharedPreferencesData
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentEnterTerminalPinBinding

class EnterTerminalPinFragment : Fragment(), View.OnClickListener {

    private var dtpNonDtpCheck: String? = ""
    var dtpNondtpCheckPref: SharedPreferencesData? = null
    var mobNokeyboard: EnterMobileNoKeyboard? = null
    lateinit var binding:FragmentEnterTerminalPinBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_enter_terminal_pin,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener { view, motionEvent -> true }
        dtpNondtpCheckPref = SharedPreferencesData(context)
        if (dtpNondtpCheckPref != null) {
            dtpNonDtpCheck = dtpNondtpCheckPref!!.getSharedPreferenceData(Constants.CARDEDNONCARDEDPREF, Constants.LOYALTYDTPNONDTP)
        }
        binding.gotoBack!!.setOnClickListener(this)
        binding.lLskip!!.setOnClickListener(this)
        // Intent intent = getIntent();
        //rcvPageDetails = intent.getStringExtra("pageDetails");
         mobNokeyboard=view.findViewById(R.id.mobileNokeyboard)
        val layoutDone = mobNokeyboard!!.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener {
            if (dtpNonDtpCheck.equals("NONDTP", ignoreCase = true)) {
                callEnterOtpFragment()
            } else {
                callSuccessFragment()
            }
        }
        binding.lLskip!!.setOnClickListener {
        }
        binding.odometerEditText!!.setOnTouchListener { v, event ->
            val inType = binding.odometerEditText!!.inputType // backup the input type
            binding.odometerEditText!!.inputType = InputType.TYPE_NULL // disable soft input
            binding.odometerEditText!!.onTouchEvent(event) // call native handler
            binding.odometerEditText!!.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        binding.odometerEditText!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.odometerEditText!!.setTextIsSelectable(true)

        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.odometerEditText!!.onCreateInputConnection(EditorInfo())
        mobNokeyboard!!.setInputConnection(ic)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity()!!.onBackPressed()
        }
    }

    private fun callEnterOtpFragment() {
        if (Validation.isvalidTerminalPin(binding.odometerEditText!!.text.toString())) {
            val fragment: Fragment = EnterOtpFragment()
            requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.enterterminalpin), Toast.LENGTH_LONG).show()
        }
    }

    private fun callSuccessFragment() {
        if (Validation.isvalidTerminalPin(binding.odometerEditText!!.text.toString())) {
            val fragment: Fragment = TransactionSuccessFragment()
            requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.enterterminalpin), Toast.LENGTH_LONG).show()
        }
    }
}