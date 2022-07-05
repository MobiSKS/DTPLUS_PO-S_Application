package com.paytm.hpclpos.activities.dashboard.merchantservice

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivityMerchantServiceDashboardBinding
import com.paytm.hpclpos.livedatamodels.batchupload.TransactionDetail
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.printreceipts.SettlementReportReceipt
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.viewmodel.ConstructSettlementReportObject
import com.paytm.hpclpos.viewmodel.ConstructSettlementRequest
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class MerchantServiceDashboardActivity : BaseFragment(), OnItemClick {

    private var reprintAdapter: MServiceReprintAdapter? = null
    private var settlementAdapter: MserviceSettlementAdapter? = null
    private var managementAdapter: MserviceManagementAdapter? = null
    private var mserviceHpPayReportAdapter: MserviceHpPayReportAdapter? = null
    private var mserviceReportsAdapter : MserviceReportsAdapter? = null
    private var mserviceReviewsAdapter: MserviceReviewsAdapter? = null
    private var cardedAndMobileModelList: MutableList<CardedAndMobileModel>? = null
    private var settlementDialog: SettlementDialog? = null
    private val DETAIL_REPORT = 0
    private val NO_REPORT = 1

    override fun updateTimerUi(l: Long) {
       // Do nothing
    }

    var arraylist = ArrayList<TransactionDetail>()
    lateinit var binding: ActivityMerchantServiceDashboardBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_merchant_service_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
        navController = Navigation.findNavController(view)
        binding.gotoBack.setOnClickListener { gotoBackActivity() }
        reprintData()
        settlementData()
        managementData()
        reports()
        reviews()
        hpPayReport()
    }

    private fun reports(){
            cardedAndMobileModelList = ArrayList()
            cardedAndMobileModelList!!.add(CardedAndMobileModel("Operator Summary", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Batch Detail", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Batch Summary", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Daily Summary", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        binding.reports.layoutManager = GridLayoutManager(requireContext(), 4)
            mserviceReportsAdapter = MserviceReportsAdapter(requireActivity(), cardedAndMobileModelList, requireActivity())
            binding.reports.adapter = mserviceReportsAdapter
    }

    private fun reviews(){
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Detail", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Summary", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        binding.reviews.layoutManager = GridLayoutManager(requireContext(), 4)
        mserviceReviewsAdapter = MserviceReviewsAdapter(requireActivity(), cardedAndMobileModelList, requireActivity())
        binding.reviews.adapter = mserviceReviewsAdapter
    }

    private fun hpPayReport(){
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Last Receipt", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Any Receipt", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Transaction Summary", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        binding.hpPayReport.layoutManager = GridLayoutManager(requireContext(), 4)
        mserviceHpPayReportAdapter = MserviceHpPayReportAdapter(requireActivity(), cardedAndMobileModelList, requireActivity())
        binding.hpPayReport.adapter = mserviceHpPayReportAdapter
    }

    private fun gotoBackActivity() {
        navController!!.navigate(R.id.action_main_fragment)
    }

    private fun reprintData() {
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Last TXN Slip", ContextCompat.getDrawable(requireContext(),R.drawable.lasttxnslip_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Any TXN Slip", ContextCompat.getDrawable(requireContext(),R.drawable.anytxnslip_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Last Batch", ContextCompat.getDrawable(requireContext(),R.drawable.lastbatch_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Batch Summary", ContextCompat.getDrawable(requireContext(),R.drawable.batchsummary_icon)))
        binding.rvReprint.layoutManager = GridLayoutManager(requireContext(), 4)
        reprintAdapter = MServiceReprintAdapter(requireActivity(), cardedAndMobileModelList, navController,requireActivity())
        binding.rvReprint.adapter = reprintAdapter
    }

    private fun settlementData() {
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Detail Report", ContextCompat.getDrawable(requireContext(),R.drawable.detailreport_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("No Report", ContextCompat.getDrawable(requireContext(),R.drawable.noreport_icon)))
        binding.rvSettlement.layoutManager = GridLayoutManager(requireContext(), 4)
        settlementAdapter = MserviceSettlementAdapter(requireActivity(), cardedAndMobileModelList,this)
        binding.rvSettlement.adapter = settlementAdapter
    }

    private fun managementData() {
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Operator Options", ContextCompat.getDrawable(requireContext(),R.drawable.operatoroptions_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Change Terminal PIN", ContextCompat.getDrawable(requireContext(),R.drawable.chngtrminalpin_icon)))
        cardedAndMobileModelList!!.add(CardedAndMobileModel("Unblock Terminal PIN", ContextCompat.getDrawable(requireContext(),R.drawable.unblockterminalpin_icon)))
        binding.rvManagement.layoutManager = GridLayoutManager(requireContext(), 4)
        managementAdapter = MserviceManagementAdapter(requireActivity(), cardedAndMobileModelList, requireActivity(),navController)
        binding.rvManagement.adapter = managementAdapter
    }

    fun batchSettlement() {
        val viewModel: MerchantActivityViewModel =
            ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        val constructSettlementRequest = ConstructSettlementRequest(requireContext()).getSettlementRequest(Constants.ALL)
        viewModel.makeApiBatchSettlement(constructSettlementRequest)
        viewModel.getliveDataBatchSettlement().observe(this, {
            if (it != null) {
                if (it.success) {
                    if(it.internelStatusCode.equals(Constants.STATUS_SUCCESS)) {
                        ConstructSettlementReportObject(requireContext())
                            .constructSettlementReportObj(constructSettlementRequest)
                        settlementDialog!!.onSuccess()
                        Handler().postDelayed({ settlementDialog!!.dismiss() },1000)
                        ToastMessages.customMsgToast(requireContext(),
                            "Response Message ${it.internelStatusCode} Message ${it.message}")
                        Log.d("MerchantService", "Incremented to" + TransactionUtils.getCurrentBatch( requireContext(),
                            Constants.BATCH))
                        TransactionUtils.incrementBatch(requireContext(),Constants.BATCH)
                        GlobalMethods.decrementTransactionIdByOne(requireContext(), "000001")
                        SettlementReportReceipt(requireContext(),requireActivity()).displayReceipt(object: PrintStatusListener{
                            override fun onSuccess() {
                                //
                            }

                            override fun onError(error: Int) {
                                ToastMessages.customMsgToast(context,"Printing Error $error")
                            }
                        },constructSettlementRequest)
                    }
                } else {
                    settlementDialog!!.onFailure(it.message)
                    Handler().postDelayed({ settlementDialog!!.dismiss() },1000)
                }
            } else {
                ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
            }
        })
    }

    override fun onBatchSettlement(position: Int) {
        when(position) {
            DETAIL_REPORT -> {
                requireActivity().runOnUiThread({
                    settlementDialog = SettlementDialog(requireActivity())
                    settlementDialog?.setVisibilityForSettlementStatus()
                    settlementDialog?.setTitle(getString(R.string.title_settlement))
                    settlementDialog?.setProcessing()
                    settlementDialog!!.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
                    settlementDialog!!.setpacketStatus("Recieving.....")
                    settlementDialog?.show()
                    batchSettlement()
                })
            }
            NO_REPORT -> {
                requireActivity().runOnUiThread({
                    navController!!.navigate(R.id.action_settlement_fragment)
                })
            }
        }
    }

    override fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController!!.navigate(R.id.action_main_fragment)
                }
            })
    }
}