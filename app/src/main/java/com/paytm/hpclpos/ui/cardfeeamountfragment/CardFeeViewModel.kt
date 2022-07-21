package com.paytm.hpclpos.ui.cardfeeamountfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.livedatamodels.cardfee.CardFeeRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardFeeViewModel : ViewModel() {

    var liveDataCardFee: MutableLiveData<ApiResponse.CCMSRechargeResponse>? = null
    fun getliveDataCardFee(): MutableLiveData<ApiResponse.CCMSRechargeResponse> {
        return liveDataCardFee!!
    }

    fun makeApiCardFeeRequest(cardFeeRequest: CardFeeRequest) {
        liveDataCardFee = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCardFeePayment(cardFeeRequest)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(
                call: Call<ApiResponse.CCMSRechargeResponse?>,
                response: Response<ApiResponse.CCMSRechargeResponse?>
            ) {
                if (response.isSuccessful) {
                    liveDataCardFee!!.value = response.body()
                } else {
                    liveDataCardFee!!.value = null
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataCardFee!!.value = null
            }
        })
    }
}