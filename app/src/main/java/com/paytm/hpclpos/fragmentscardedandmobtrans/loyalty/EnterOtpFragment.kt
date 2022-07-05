package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard
import com.paytm.hpclpos.databinding.FragmentEnterOtp2Binding

class EnterOtpFragment : Fragment() {
    private var mParam1: String? = null
    private var mParam2: String? = null


    lateinit var binding: FragmentEnterOtp2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments()!!.getString(ARG_PARAM1)
            mParam2 = requireArguments()!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_enter_otp2, container, false)
        //ButterKnife.bind(this, view)
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_enter_otp2,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        val mobNokeyboard: EnterMobileNoKeyboard = view.findViewById(R.id.mobileNokeyboard)
        val layoutDone = mobNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener { callSuccessFragment() }
        binding.gotoBack.setOnClickListener {
            requireActivity()!!.onBackPressed()
        }
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
    }

    private fun callSuccessFragment() {
      /*  if (Validation.isValidOtp(binding.cardPinEditText!!.text.toString())) {
            val fragment: Fragment = PrintBalanceFragment()
            requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.entervalidotp), Toast.LENGTH_LONG).show()
        }*/
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): EnterOtpFragment {
            val fragment = EnterOtpFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}