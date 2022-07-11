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
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentEnterMobileNoBinding

class EnterMobileNoFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentEnterMobileNoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_enter_mobile_no,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener { view, motionEvent -> true }
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        binding.gotoBack.setOnClickListener(this)
        val mobNokeyboard: EnterMobileNoKeyboard = view.findViewById(R.id.mobileNokeyboard)
        val layoutDone = mobNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener { otpActivityCall() }
        binding.mobnoEditText!!.setOnTouchListener { v, event ->
            val inType = binding.mobnoEditText.inputType // backup the input type
            binding.mobnoEditText.inputType = InputType.TYPE_NULL // disable soft input
            binding.mobnoEditText.onTouchEvent(event) // call native handler
            binding.mobnoEditText.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        binding.mobnoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.mobnoEditText.setTextIsSelectable(true)

        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.mobnoEditText.onCreateInputConnection(EditorInfo())
        mobNokeyboard.setInputConnection(ic)
    }

    private fun otpActivityCall() {
        if (Validation.isValidMobileNo(binding.mobnoEditText.text.toString())) {
            val fragment: Fragment = DriverLoyaltyFragment()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.entervalidmono), Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> { requireActivity().onBackPressed() }
        }
    }
}