package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import butterknife.BindView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentVoidBinding

class VoidFragment : Fragment(), View.OnClickListener {

    @JvmField
    @BindView(R.id.gotoBack)
    var gotoBack: LinearLayout? = null

    @JvmField
    @BindView(R.id.rocNo_EditText)
    var rocNo_EditText: EditText? = null

    @JvmField
    @BindView(R.id.reEnterrocNo_EditText)
    var reEnterrocNo_EditText: EditText? = null

    lateinit var binding: FragmentVoidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_void,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        binding.gotoBack!!.setOnClickListener(this)
        val mobNokeyboard: EnterMobileNoKeyboard = view.findViewById(R.id.keyboard)
        val layoutDone = mobNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener { callDtpFragment() }


        binding.rocNoEditText.setOnTouchListener { v, event ->
            val inType = binding.rocNoEditText.inputType // backup the input type
            binding.rocNoEditText.inputType = InputType.TYPE_NULL // disable soft input
            binding.rocNoEditText.onTouchEvent(event) // call native handler
            binding.rocNoEditText.inputType = inType // restore input type
            // prevent system keyboard from appearing when EditText is tapped
            binding.rocNoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding.rocNoEditText.setTextIsSelectable(true)

            // pass the InputConnection from the EditText to the keyboard
            val ic = binding.rocNoEditText.onCreateInputConnection(EditorInfo())
            mobNokeyboard.setInputConnection(ic)
            true // consume touch even
        }

        binding.reEnterrocNoEditText.setOnTouchListener { v, event ->
            val inType = binding.reEnterrocNoEditText.inputType // backup the input type
            binding.reEnterrocNoEditText.inputType = InputType.TYPE_NULL // disable soft input
            binding.reEnterrocNoEditText.onTouchEvent(event) // call native handler
            binding.reEnterrocNoEditText.inputType = inType // resto
            // prevent system keyboard from appearing when EditText is tapped
            binding.reEnterrocNoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding.reEnterrocNoEditText.setTextIsSelectable(true)

            // pass the InputConnection from the EditText to the keyboard
            val icNew = binding.reEnterrocNoEditText.onCreateInputConnection(EditorInfo())
            mobNokeyboard.setInputConnection(icNew) // re input type
            true // consume touch even
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity()!!.onBackPressed()
        }
    }

    private fun callDtpFragment() {
        if (binding.rocNoEditText!!.text.toString().equals(binding.reEnterrocNoEditText!!.text.toString(), ignoreCase = true)) {
            if (Validation.isValidRocNo(binding.rocNoEditText!!.text.toString())) {
                val fragment: Fragment = CcmsSaleFragment()
                requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                        .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
            } else {
                Toast.makeText(context, resources.getString(R.string.entervalirocno), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, resources.getString(R.string.mismatchrocno), Toast.LENGTH_SHORT).show()
        }
    }
}