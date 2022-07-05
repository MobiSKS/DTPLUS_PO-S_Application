package com.paytm.hpclpos.ui.cardfeeamountfragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.paytm.hpclpos.PrintReceipts.Store
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.FragmentCardFeeCnfrmAmntBinding
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.ConstructSettlementRequest

class CardFeeCnfrmAmntFrag : BaseFragment(), View.OnClickListener {
    private var rcvAmount: String? = null
    private var formNumber: String? = null
    private var numberOfCards: String? = null
    private var dialog1: Dialog? = null
    private var settlementDialog: SettlementDialog? = null

    lateinit var binding: FragmentCardFeeCnfrmAmntBinding
    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        // val rootView = inflater.inflate(R.layout.fragment_card_fee_cnfrm_amnt, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_fee_cnfrm_amnt, container, false)
        binding.root.setOnTouchListener { view, motionEvent -> true }
        rcvAmount = arguments?.getString(Constants.CARDAMOUNTS)
        formNumber = arguments?.getString(Constants.FORMNUMBER)
        numberOfCards = arguments?.getString(Constants.NUMBEROFCARDS)
        binding.formNoTextView.text = formNumber
        binding.numberOFCardTextView.text = numberOfCards
        binding.amountTextView.text = rcvAmount
        val lLOkconfirm = binding.root.findViewById<View>(R.id.lLOkconfirm) as LinearLayout
        val lLCancl = binding.root.findViewById<View>(R.id.lLCancl) as LinearLayout
        lLOkconfirm.setOnClickListener(this)
        lLCancl.setOnClickListener(this)
        handleOnBackPressed()
        return binding.root
    }

    //Click Event handle of confirm  button and cancel button
    override fun onClick(v: View) {
        when (v.id) {
            R.id.lLOkconfirm -> checkNetworkStatus()
            R.id.lLCancl -> goToMainFragment()
        }
    }

    fun checkNetworkStatus() {
        if (NetworkUtil.checkNetworkStatus(requireContext())) {
            showProcessingDialog()
            callCardFeeApi()
        }
    }

    private fun callCardFeeApi() {
        val viewModel: CardFeeViewModel = ViewModelProvider(this).get(CardFeeViewModel::class.java)
        val cardFeeRequest = ConstructSettlementRequest(requireContext()).getCardFeeRequest(formNumber!!,
            numberOfCards!!.toInt(),rcvAmount!!.toDouble().toInt())

        viewModel.makeApiCardFeeRequest(cardFeeRequest)
        viewModel.getliveDataCardFee().observe(this, {
            if (it != null) {
                if (it.success) {
                    if(it.internelStatusCode.equals(Constants.STATUS_SUCCESS)) {
                        Store(requireContext()).storeCardFeeTransDetailsInDB(cardFeeRequest)
                        settlementDialog!!.onSuccess()
                        Handler().postDelayed({ goToTransactionSuccessActivity()},1000)
                    }
                } else {
                    GlobalMethods.decrementTransactionIdByOne(requireContext()
                        ,cardFeeRequest.invoiceId.toString())
                    gotoTransactionFailedActivity(it.data[0].reason)
                    ToastMessages.customMsgToast(
                        requireContext(),
                        "Response Message ${it.internelStatusCode} Message ${it.data[0].reason}")
                }
            } else {
                GlobalMethods.decrementTransactionIdByOne(requireContext()
                    ,cardFeeRequest.invoiceId.toString())
                gotoTransactionFailedActivity("Internal Server Error")
                ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
            }
        })
    }

    //Dialog box used for when priting slip for printing progress.
    private fun showGifDialog() {
        dialog1 = Dialog(requireContext()!!)
        dialog1!!.setContentView(R.layout.custom_print_gif_layout)
        val imageView = dialog1!!.findViewById<ImageView>(R.id.gifImageview)
        Glide.with(this).load(R.drawable.printergif).into(imageView)
        val window = dialog1!!.window
        dialog1!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        dialog1!!.show()
    }

    private fun goToTransactionSuccessActivity() {
        settlementDialog!!.dismiss()
        navController!!.navigate(R.id.action_ccmsRecharge_transactionSuccess)
    }

    private fun gotoTransactionFailedActivity(message: String) {
        settlementDialog!!.onFailure(message)
        Handler().postDelayed({
            settlementDialog!!.dismiss()
            val bundle = Bundle()
            bundle.putString(Constants.LIMITEXCEED, message)
            navController!!.navigate(R.id.action_ccmsRecharge_transactionFailed, bundle)
        },1000)
    }

    fun goToMainFragment() {
        navController!!.navigate(R.id.action_main_fragment)
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback( viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { goToMainFragment() }
            })
    }

    private fun showProcessingDialog() {
        settlementDialog = SettlementDialog(requireActivity())
        binding.linearLayout.visibility = View.GONE
        settlementDialog?.setTitle(AppUtils.getTitleName(GlobalMethods.getSaleType()!!))
        settlementDialog?.setProcessing()
        settlementDialog?.setpacketStatus("Recieving.....")
        settlementDialog?.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
        settlementDialog?.show()
    }
}