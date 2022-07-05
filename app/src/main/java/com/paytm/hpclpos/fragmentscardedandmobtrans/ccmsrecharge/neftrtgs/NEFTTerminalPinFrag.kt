package com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge.neftrtgs

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.FragmentNEFTTerminalPinBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount.RechargeCcmsAccountRequest
import com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge.TransactionFailedActivity
import com.paytm.hpclpos.viewmodel.MainActivityViewModel

class NEFTTerminalPinFrag : Fragment(), View.OnClickListener {
    @JvmField
    @BindView(R.id.termnlPin_editText)
    var termnlPin_editText: EditText? = null

    @JvmField
    @BindView(R.id.gotoBack)
    var gotoBack: LinearLayout? = null

    lateinit var binding: FragmentNEFTTerminalPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_n_e_f_t_terminal_pin, container, false)
        val mobNokeyboard = binding.root.findViewById<View>(R.id.mobileNokeyboard) as EnterMobileNoKeyboard
        val layoutDone = mobNokeyboard.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener(this)
        binding.gotoBack!!.setOnClickListener(this)
        binding.termnlPinEditText!!.setOnTouchListener { v, event ->
            val inType = binding.termnlPinEditText!!.inputType // backup the input type
            binding.termnlPinEditText!!.inputType = InputType.TYPE_NULL // disable soft input
            binding.termnlPinEditText!!.onTouchEvent(event) // call native handler
            binding.termnlPinEditText!!.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        binding.termnlPinEditText!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.termnlPinEditText!!.setTextIsSelectable(true)

        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.termnlPinEditText!!.onCreateInputConnection(EditorInfo())
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
            R.id.llayout_done -> callTransactionSlip()
        }
    }

    private fun callTransactionSlip() {
            val sharedPreferencesData = SharedPreferencesData(requireContext())
            val batchId = sharedPreferencesData.getSharedPreferenceData(Constants.PREFCONFIG, Constants.BATCHID)
            val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val rechargeCcmsAccountRequest = RechargeCcmsAccountRequest(
            "string",
            Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(requireContext()),
            GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(requireContext()).toString(),
            "1142419208929028",
            batchId!!.toInt(),
            GlobalMethods.getAmount()!!.replace(".", "").replace(",","").toInt(),
            SaleTransactionDetails.CARDSALE.saleType.toString(),
            GlobalMethods.getTransactionId(requireContext()),
            "2022-02-10 16:02:47.170",
            "",
            "",
            "1234567891234567891234",
            "INR",
            "",
            "",
            "",
            "1234",
            "Terminal",
            "123456"
        )
        viewModel.makeApiCcmsRecharge(rechargeCcmsAccountRequest)
        viewModel.getliveDataCcmsRecharge().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                if (it.success) {
                    Log.d("CashEnterTerminal", " CashSaleByCard ${it.statusCode} ${it.message}")
                    ToastMessages.customMsgToast(context, "${it.data[0].status}")
                    callTransactionSuccess(
                        "1",
                        rechargeCcmsAccountRequest.transdate,
                        rechargeCcmsAccountRequest.amount.toString(),
                        rechargeCcmsAccountRequest.transid
                    )
                } else {
                    val intent = Intent(requireContext(), TransactionFailedActivity::class.java)
                    intent.putExtra(Constants.LIMITEXCEED, it.message)
                    startActivity(intent)
                }
            } else {
                ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
            }
        })
    }

    private fun callTransactionSuccess(transId: String, transDate: String, transAmount: String, product: String) {
        val intent = Intent(requireContext(), com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge.TransactionSucessActivity::class.java)
        intent.putExtra(Constants.TRANSID, transId)
        intent.putExtra(Constants.TRANSDATE, transDate)
        intent.putExtra(Constants.TRANSAMOUNT, transAmount)
        intent.putExtra(Constants.TRANSPRODUCT, product)
        startActivity(intent)
    }
}