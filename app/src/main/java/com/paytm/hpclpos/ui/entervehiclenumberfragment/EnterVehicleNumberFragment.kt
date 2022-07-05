package com.paytm.hpclpos.ui.entervehiclenumberfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.ui.entermobilenumber.EnterMobileNumberFragment
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.FragmentEnterVehicleNumberBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSaleRequest
import com.paytm.hpclpos.viewmodel.MainActivityViewModel
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class EnterVehicleNumberFragment : BaseFragment() {

    lateinit var binding: FragmentEnterVehicleNumberBinding
    lateinit var vehicleNumber: String
    private var settlementDialog: SettlementDialog? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_vehicle_number, container, false)
        val layoutDone = binding.mobileNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener { getOtpNumber() }
        binding.gotoBack.setOnClickListener { goToConfirmAmountActivity() }
        binding.title.setText(GlobalMethods.getSaleType())
        binding.vehicleEdittext.setOnTouchListener { v, event ->
            val inType = binding.vehicleEdittext.inputType // backup the input type
            binding.vehicleEdittext.inputType = InputType.TYPE_NULL // disable soft input
            binding.vehicleEdittext.onTouchEvent(event) // call native handler
            binding.vehicleEdittext.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        binding.vehicleEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.vehicleEdittext.setTextIsSelectable(true)

        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.vehicleEdittext.onCreateInputConnection(EditorInfo())
        binding.mobileNokeyboard.setInputConnection(ic)
        handleOnBackPressed()
        return binding.root
    }

    private fun getOtpNumber() {
        val vehicleNumber = binding.vehicleEdittext.text.toString()
        if (!Validation.isvalidCardPin(vehicleNumber.trim { it <= ' ' })) {
            Toast.makeText(context, "Please enter a valid Vehicle No.", Toast.LENGTH_SHORT).show()
        } else {
            GlobalMethods.getCardInfoEntity()!!.vehicleNumber = vehicleNumber
            checkTransTypeforNavigation()
        }
    }

    private fun goToConfirmAmountActivity() {
        val bundle = Bundle()
        val fragment: Fragment = EnterMobileNumberFragment()
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
            .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
    }

    fun checkTransTypeforNavigation() {
        val batchId = TransactionUtils.getCurrentBatch(requireContext(),Constants.BATCH)
        val merchantViewModel = ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        val idfcGetOtpRequest = ConstructSaleRequest(requireContext(),batchId.toInt(),"").constructIDFCgetOtp()
        merchantViewModel.makeApiIdfcGetOtp(idfcGetOtpRequest)
        merchantViewModel.getliveDataIdfcGetOtp().observe(viewLifecycleOwner,{

            if (it != null) {
                if (it.success) {
                    settlementDialog!!.onSuccess()
                    Handler().postDelayed({ goToTransactionSuccessActivity()},1000)
                } else {
                  /*  GlobalMethods.decrementTransactionIdByOne(requireContext(), ccmsSaleRequest.invoiceId.toString())
                    settlementDialog!!.onFailure(it.data[0].reason)
                    Handler().postDelayed({
                        settlementDialog!!.dismiss()
                        val bundle = Bundle()
                        bundle.putString(Constants.LIMITEXCEED, it.data[0].reason)
                        navController!!.navigate(R.id.action_transactionFailed, bundle)
                    },1000)*/
                }
            } else {
              //  GlobalMethods.decrementTransactionIdByOne(requireContext(),ccmsSaleRequest.invoiceId.toString())
                ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
            }
        })

    }

    override fun updateTimerUi(l: Long) {
        requireView().findViewById<TextView>(R.id.timer).setText(l.toString()
            .substring(0, l.toString().length - 3) +" s")
    }

    private fun goToTransactionSuccessActivity() {
        settlementDialog?.dismiss()
        navController!!.navigate(R.id.action_transactionSuccess)
    }
}