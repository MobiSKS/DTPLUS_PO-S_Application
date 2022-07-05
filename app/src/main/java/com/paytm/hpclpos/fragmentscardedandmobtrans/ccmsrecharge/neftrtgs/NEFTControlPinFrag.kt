package com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge.neftrtgs

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
import com.paytm.hpclpos.databinding.FragmentNEFTControlPinBinding

class NEFTControlPinFrag : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentNEFTControlPinBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_n_e_f_t_control_pin, container, false)
        val mobNokeyboard = binding.root.findViewById<View>(R.id.mobileNokeyboard) as EnterMobileNoKeyboard
        val layoutDone = mobNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener(this)
        binding.gotoBack!!.setOnClickListener(this)
        binding.cardPinEditText!!.setOnTouchListener { v, event ->
            val inType = binding.cardPinEditText!!.inputType // backup the input type
            binding.cardPinEditText!!.inputType = InputType.TYPE_NULL // disable soft input
            binding.cardPinEditText!!.onTouchEvent(event) // call native handler
            binding.cardPinEditText!!.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        binding.cardPinEditText!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.cardPinEditText!!.setTextIsSelectable(true)

        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.cardPinEditText!!.onCreateInputConnection(EditorInfo())
        mobNokeyboard.setInputConnection(ic)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener { view, motionEvent -> true }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity()!!.onBackPressed()
            R.id.llayout_done -> callEnterTerminalPInFrag()
        }
    }

    private fun callEnterTerminalPInFrag() {
        if (Validation.isvalidControlPin(binding.cardPinEditText!!.text.toString())) {
            val fragment: Fragment = NEFTTerminalPinFrag()
            requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.entervalidctrlpin), Toast.LENGTH_LONG).show()
        }
    }
}