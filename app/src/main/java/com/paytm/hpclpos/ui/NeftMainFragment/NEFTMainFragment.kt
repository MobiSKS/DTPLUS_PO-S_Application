package com.paytm.hpclpos.ui.NeftMainFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentNEFTMainBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment

class NEFTMainFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentNEFTMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_n_e_f_t_main, container, false)
        binding.gotoBack.setOnClickListener(this)
        binding.lLNext.setOnClickListener(this)
        binding.lLcancl.setOnClickListener(this)
        return binding.root
    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(
            l.toString()
                .substring(0, l.toString().length - 3) + " s"
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        handleEnterKey()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lLcancl, R.id.gotoBack -> {
                hideKeypad()
                requireActivity().onBackPressed()
            }
            R.id.lLNext, R.id.llayout_done -> {
                hideKeypad()
                callControlCardNoFrag()
            }
        }
    }

    private fun callControlCardNoFrag() {
        if(binding.uTRNoEditText.text.toString().isBlank()) {
            binding.uTRNoEditText.setError(getString(R.string.input_required))
        } else if(!Validation.isvalidUTRNo(binding.uTRNoEditText.text.toString())) {
            binding.uTRNoEditText.setError(getString(R.string.entervalidutrno))
        } else {
            GlobalMethods.setUTRNumber(binding.uTRNoEditText.text.toString())
            if (GlobalMethods.getCardInfoEntity() != null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
                navController!!.navigate(R.id.action_controlCardNumber)
            } else {
                navController!!.navigate(R.id.amountEntryFragment)
            }
        }
    }

    private fun handleEnterKey() {
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.uTRNoEditText.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        binding.uTRNoEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.uTRNoEditText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closesoftKeypad(binding.uTRNoEditText)
                callControlCardNoFrag()
                true
            } else false
        })
    }
}