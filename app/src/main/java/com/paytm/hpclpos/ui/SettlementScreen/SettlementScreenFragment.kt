package com.paytm.hpclpos.ui.SettlementScreen

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.databinding.FragmentSettlementScreenBinding
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.TransSumAvg.TransSumAvg
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.printreceipts.SettlementReportReceipt
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener
import com.paytm.hpclpos.ui.SettlementScreen.SettlementAdapter.SettlementAdapter
import com.paytm.hpclpos.viewmodel.ConstructSettlementReportObject
import com.paytm.hpclpos.viewmodel.ConstructSettlementRequest
import com.paytm.hpclpos.viewmodel.MerchantActivityViewModel

class SettlementScreenFragment : BaseFragment() {

    lateinit var binding: FragmentSettlementScreenBinding
    var recyclerView: RecyclerView? = null
    private var settlementAdapter: SettlementAdapter? = null
    private var settlementDialog: SettlementDialog? = null

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settlement_screen, container, false)
        recyclerView = binding.rvCardedAndMobile
        binding.clickSettlement.setOnClickListener {
            requireActivity().runOnUiThread({
                settlementDialog = SettlementDialog(requireActivity())
                settlementDialog?.setVisibilityForSettlementStatus()
                settlementDialog?.setTitle(getString(R.string.title_settlement))
                settlementDialog?.setProcessing()
                settlementDialog?.show()
                batchSettlement()
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayUi()
    }

    fun displayUi() {
        val appRepository = AppRepository(requireContext())
        val batchNum = TransactionUtils.getCurrentBatch(requireContext(), Constants.BATCH)
        val arrayListTransSum = ArrayList<TransSumAvg>()
        SaleTransactionDetails.getAllCategoryData().forEach {
            with(appRepository.getSumOfTrans(it,batchNum.toInt())) {
                arrayListTransSum.add(TransSumAvg(it,total,average))
            }
        }
        recyclerView!!.layoutManager = GridLayoutManager(requireContext(),1)
        settlementAdapter = SettlementAdapter(requireContext(), arrayListTransSum)
        recyclerView!!.adapter = settlementAdapter
    }

    fun batchSettlement() {
        val viewModel: MerchantActivityViewModel =
            ViewModelProvider(this).get(MerchantActivityViewModel::class.java)
        val constructSettlementRequest = ConstructSettlementRequest(requireContext()).getSettlementRequest(Constants.ALL)
        viewModel.makeApiBatchSettlement(constructSettlementRequest)
        viewModel.getliveDataBatchSettlement().observe(viewLifecycleOwner, {
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
                        SettlementReportReceipt(requireContext(),requireActivity())
                            .printNoDetailReport(object: PrintStatusListener{
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
}