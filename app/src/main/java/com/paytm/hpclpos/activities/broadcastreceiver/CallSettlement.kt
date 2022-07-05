package com.paytm.hpclpos.activities.broadcastreceiver

import android.content.Context
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeResponse
import com.paytm.hpclpos.viewmodel.ConstructSettlementRequest
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallSettlement(val context: Context) {

    fun makeApiCallForBatchSettlement(settlementListener: SettlementListener) {
        val constructSettlementRequest = ConstructSettlementRequest(context).getSettlementRequest(Constants.SALE)
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBatchSettlement(constructSettlementRequest)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(
                call: Call<CCMSRechargeResponse?>, response: Response<CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    settlementListener.response(response.body()!!)
                } else {
                    settlementListener.response(null)
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                settlementListener.response(null)
            }
        })
    }
}