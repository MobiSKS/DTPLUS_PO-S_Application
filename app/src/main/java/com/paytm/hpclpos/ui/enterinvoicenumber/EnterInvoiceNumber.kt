package com.paytm.hpclpos.ui.enterinvoicenumber

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.printreceipts.SaleReceipts
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.ToastMessages.customMsgToast
import com.paytm.hpclpos.databinding.FragmentEnterInvoiceNumberBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment


class EnterInvoiceNumber : BaseFragment() {

    lateinit var binding: FragmentEnterInvoiceNumberBinding
    override fun updateTimerUi(l: Long) {
     // Do nothing
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_invoice_number, container, false)
        val layoutDone = binding.mobileNokeyboard!!.findViewById<LinearLayout>(R.id.llayout_done)
        layoutDone.setOnClickListener { printTransactionReceipt() }
        binding.invoiceEdittext!!.setOnTouchListener { v, event ->
            val inType = binding.invoiceEdittext!!.inputType // backup the input type
            binding.invoiceEdittext!!.inputType = InputType.TYPE_NULL // disable soft input
            binding.invoiceEdittext!!.onTouchEvent(event) // call nat
            // ive handler
            binding.invoiceEdittext!!.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        binding.invoiceEdittext!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.invoiceEdittext!!.setTextIsSelectable(true)

        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.invoiceEdittext!!.onCreateInputConnection(EditorInfo())
        binding.mobileNokeyboard!!.setInputConnection(ic)
        return binding.root
    }

    fun printTransactionReceipt() {
        val db = AppRepository(requireContext())
        val transaction = db.getTransactionBasedOnId(binding.invoiceEdittext.text.toString().toInt())

        if (transaction != null) {
            showGifDialog()
            Handler(Looper.getMainLooper()).postDelayed({
                dialog!!.dismiss()
            }, 2000)
            SaleReceipts(requireContext(), requireActivity(), transaction).displayReceipt(printStatusListener)
        } else {
            customMsgToast(context, "No Transaction Found")
        }
    }

    private fun showGifDialog() {
        dialog = Dialog(requireActivity())
        dialog!!.setContentView(R.layout.custom_print_gif_layout)
        val imageView = dialog!!.findViewById<ImageView>(R.id.gifImageview)
        Glide.with(requireContext()).load(R.drawable.printergif).into(imageView)
        val window = dialog!!.window
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        dialog!!.show()
    }

    val printStatusListener = object : PrintStatusListener {
        override fun onSuccess() {
            //Do something
        }

        override fun onError(error: Int) {
            //Do something
        }
    }
}