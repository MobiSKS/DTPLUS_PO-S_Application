package com.paytm.hpclpos.activities.broadcastreceiver

import android.content.Context
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse
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
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(
                call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    settlementListener.response(response.body()!!)
                } else {
                    settlementListener.response(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                settlementListener.response(null)
            }
        })
    }
}