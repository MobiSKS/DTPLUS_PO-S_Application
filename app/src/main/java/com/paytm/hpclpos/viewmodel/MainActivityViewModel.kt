package com.paytm.hpclpos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.HttpError
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeRequest
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.livedatamodels.generatetoken.ApiResponse
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenRequest
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    var liveDataGenerateToken: MutableLiveData<ApiResponse> = MutableLiveData()

    fun getLiveDataObserverGenerateToken(): MutableLiveData<ApiResponse> {
        return liveDataGenerateToken
    }

    fun makeApiCallGenerateToken(generateTokenRequest: GenerateTokenRequest) {
        Constants.MainUrlChanged.forToken = "1"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getGenerateToken(generateTokenRequest)
        retroService.enqueue(object : Callback<ApiResponse.GenerateTokenResponse> {
            override fun onResponse(call: Call<ApiResponse.GenerateTokenResponse>, response: Response<ApiResponse.GenerateTokenResponse>) {
                if(response.isSuccessful) {
                    liveDataGenerateToken.postValue(response.body())
                } else {
                    liveDataGenerateToken.postValue(ApiResponse.Error(HttpError(response.code().toString(),response.errorBody().toString()).toString()))
                }
            }

            override fun onFailure(call: Call<ApiResponse.GenerateTokenResponse>, t: Throwable) {
                liveDataGenerateToken.postValue(ApiResponse.Error(TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataCcmsSale: MutableLiveData<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse>? = MutableLiveData()
    fun getliveDataCcmsSale(): MutableLiveData<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse> {
        return liveDataCcmsSale!!
    }

    fun makeApiCcmsSale(ccmsSaleRequest: CCMSSaleRequest) {
        liveDataCcmsSale = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsSale(ccmsSaleRequest)
        retroService.enqueue(object : Callback<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.CCMSSaleResponse> {

            override fun onResponse(call: Call<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.CCMSSaleResponse?>,
            response: Response<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.CCMSSaleResponse?>) {
                if (response.isSuccessful) {
                    liveDataCcmsSale!!.postValue(response.body())
                } else {
                    liveDataCcmsSale!!.postValue(com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.Error(HttpError(response.code().toString(),response.errorBody().toString()).toString()))
                }
            }

            override fun onFailure(call: Call<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.CCMSSaleResponse>, t: Throwable) {
                liveDataCcmsSale?.postValue(com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.Error(TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataCcmsRechargeSale: MutableLiveData<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse>? = null
    fun getliveDataCcmsRechargeSale(): MutableLiveData<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse> {
        return liveDataCcmsRechargeSale!!
    }

    fun makeApiCcmsRechargeSale(ccmsRechargeRequest: CCMSRechargeRequest) {
        liveDataCcmsRechargeSale = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsRechargeSale(ccmsRechargeRequest)
        retroService.enqueue(object : Callback<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse?>,
                response: Response<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataCcmsRechargeSale?.postValue(response.body())
                } else {
                    liveDataCcmsRechargeSale?.postValue(com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.Error(HttpError(response.code().toString(),response.errorBody().toString()).toString()))
                }
            }

            override fun onFailure(call: Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataCcmsSale?.postValue(com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.Error(TransactionUtils.handleExceptions(t)))
            }
        })
    }
}
