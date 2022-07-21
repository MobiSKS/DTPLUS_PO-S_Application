package com.paytm.hpclpos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.constants.TransactionUtils.Companion.convertErrorBody
import com.paytm.hpclpos.constants.TransactionUtils.Companion.getStringFromError
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.CCMSBalance.CCMSBalanceRequest
import com.paytm.hpclpos.livedatamodels.ChangeCardPinRequest.ChangeCardPinRequest
import com.paytm.hpclpos.livedatamodels.ControlPinChange.ControlPinChange
import com.paytm.hpclpos.livedatamodels.Reload.ReloadRequest
import com.paytm.hpclpos.livedatamodels.UnblockCardPin.UnblockCardPinRequest
import com.paytm.hpclpos.livedatamodels.balancetransfer.BalanceTransferRequest
import com.paytm.hpclpos.livedatamodels.batchsettlement.BatchSettlementRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPRequest
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpRequest
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MerchantActivityViewModel : ViewModel() {

    var liveDatabatchSettlement: MutableLiveData<ApiResponse>? = null
    fun getliveDataBatchSettlement(): MutableLiveData<ApiResponse> {
        return liveDatabatchSettlement!!
    }

    fun makeApiBatchSettlement(batchSettlementRequest: BatchSettlementRequest) {
        liveDatabatchSettlement = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBatchSettlement(batchSettlementRequest)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDatabatchSettlement!!.value = response.body()
                } else {
                    liveDatabatchSettlement!!.value = null
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDatabatchSettlement!!.value = null
            }
        })
    }

    var liveDataBalanceTransfer: MutableLiveData<ApiResponse>? = null
    fun getliveDataBalanceTransfer(): MutableLiveData<ApiResponse> {
        return liveDataBalanceTransfer!!
    }

    fun makeApiBalanceTransfer(balanceTransferRequest: BalanceTransferRequest) {
        liveDataBalanceTransfer = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBalanceTransfer(balanceTransferRequest)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataBalanceTransfer?.postValue(response.body())
                } else {
                    liveDataBalanceTransfer?.postValue(ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response)))
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataBalanceTransfer?.postValue(ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataGenerateOTP: MutableLiveData<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse>? = null
    fun getliveDataGenerateOTP(): MutableLiveData<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse> {
        return liveDataGenerateOTP!!
    }

    fun makeApiGenerateOTP(genearteOtpRequest: GenerateOTPRequest) {
        liveDataGenerateOTP = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getOTP(genearteOtpRequest)
        retroService.enqueue(object : Callback<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.GenerateOTPResponse> {

            override fun onResponse(call: Call<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.GenerateOTPResponse?>,
            response: Response<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.GenerateOTPResponse?>) {
                if (response.isSuccessful) {
                    liveDataGenerateOTP!!.value = response.body()
                } else {
                    liveDataGenerateOTP?.postValue(com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response)))
                }
            }

            override fun onFailure(call: Call<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.GenerateOTPResponse>, t: Throwable) {
                liveDataGenerateOTP?.postValue(com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataReload: MutableLiveData<ApiResponse>? = null
    fun getliveDataReload(): MutableLiveData<ApiResponse> {
        return liveDataReload!!
    }

    fun makeApiReload(reloadRequest: ReloadRequest) {
        liveDataReload = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getReload(reloadRequest)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataReload?.value = response.body()
                } else {
                    liveDataReload?.postValue(ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response)))
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataReload?.postValue(ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataBalanceEnquiry: MutableLiveData<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse>? = null
    fun getliveDataBalanceEnquiry(): MutableLiveData<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse> {
        return liveDataBalanceEnquiry!!
    }

    fun makeApiBalanceEnquiry(balanceEnquiryRequest: BalanceEnquiryRequest) {
        liveDataBalanceEnquiry = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBalanceEnquiry(balanceEnquiryRequest)
        retroService.enqueue(object : Callback<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.BalanceEnquiryResponse> {

            override fun onResponse(call: Call<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.BalanceEnquiryResponse?>,
                                    response: Response<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.BalanceEnquiryResponse?>) {
                if (response.isSuccessful) {
                    liveDataBalanceEnquiry?.value = response.body()
                } else {
                    liveDataBalanceEnquiry?.postValue(
                        com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.Error(
                            getStringFromError(convertErrorBody(response.errorBody()!!)!!, response)))
                }
            }

            override fun onFailure(call: Call<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.BalanceEnquiryResponse>, t: Throwable) {
                liveDataBalanceEnquiry?.postValue(com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataCCMSBalanceEnquiry: MutableLiveData<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse>? = null
    fun getliveDataCCMSBalanceEnquiry(): MutableLiveData<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse> {
        return liveDataCCMSBalanceEnquiry!!
    }

    fun makeApiCCMSBalanceEnquiry(ccmsBalanceRequest: CCMSBalanceRequest) {
        liveDataCCMSBalanceEnquiry = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCCMSBalanceEnquiry(ccmsBalanceRequest)
        retroService.enqueue(object : Callback<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.CCMSBalanceResponse> {

            override fun onResponse(call: Call<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.CCMSBalanceResponse?>, response: Response<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.CCMSBalanceResponse?>) {
                if (response.isSuccessful) {
                    liveDataCCMSBalanceEnquiry!!.value = response.body()
                } else {
                    liveDataCCMSBalanceEnquiry!!.value = com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response))
                }
            }

            override fun onFailure(call: Call<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.CCMSBalanceResponse>, t: Throwable) {
                liveDataCCMSBalanceEnquiry?.postValue(com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataUnblockCardPin: MutableLiveData<ApiResponse>? = null
    fun getliveDataUnblockCardPin(): MutableLiveData<ApiResponse> {
        return liveDataUnblockCardPin!!
    }

    fun makeApiUnblockCardPin(unblockCardPinRequest: UnblockCardPinRequest) {
        liveDataUnblockCardPin = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getUnblockCardPin(unblockCardPinRequest)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataUnblockCardPin!!.value = response.body()
                } else {
                    liveDataUnblockCardPin!!.value = ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response))
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataUnblockCardPin?.postValue(ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataChangeCardPin: MutableLiveData<ApiResponse>? = null
    fun getliveDataChangeCardPin(): MutableLiveData<ApiResponse> {
        return liveDataChangeCardPin!!
    }


    fun makeApiChangeCardPin(changeCardPinRequest: ChangeCardPinRequest) {
        liveDataChangeCardPin = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getChangeCardPin(changeCardPinRequest)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataChangeCardPin!!.value = response.body()
                } else {
                    liveDataChangeCardPin!!.value = ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response))
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataChangeCardPin?.postValue(ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataControlPinChange: MutableLiveData<ApiResponse>? = null
    fun getliveDataControlPinChange(): MutableLiveData<ApiResponse> {
        return liveDataControlPinChange!!
    }

    fun makeApiControlPinChange(controlPinChange: ControlPinChange) {
        liveDataControlPinChange = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getChangeCardPin(controlPinChange)
        retroService.enqueue(object : Callback<ApiResponse.CCMSRechargeResponse> {

            override fun onResponse(call: Call<ApiResponse.CCMSRechargeResponse?>, response: Response<ApiResponse.CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataControlPinChange!!.value = response.body()
                } else {
                    liveDataControlPinChange!!.value = ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response))
                }
            }

            override fun onFailure(call: Call<ApiResponse.CCMSRechargeResponse>, t: Throwable) {
                liveDataControlPinChange?.postValue(ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }

    var liveDataIdfcGetOtp: MutableLiveData<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse>? = null
    fun getliveDataIdfcGetOtp(): MutableLiveData<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse> {
        return liveDataIdfcGetOtp!!
    }

    fun makeApiIdfcGetOtp(idfcGetOtpRequest: IdfcGetOtpRequest) {
        liveDataIdfcGetOtp = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getIdfcGetOtp(idfcGetOtpRequest)
        retroService.enqueue(object : Callback<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse.IdfcGetOtpResponse> {

            override fun onResponse(call: Call<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse.IdfcGetOtpResponse?>,
            response: Response<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse.IdfcGetOtpResponse?>) {
                if (response.isSuccessful) {
                    liveDataIdfcGetOtp!!.value = response.body()
                } else {
                    liveDataIdfcGetOtp!!.postValue(com.paytm.hpclpos.livedatamodels.idfcGetOtp.
                    ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response)))
                }
            }

            override fun onFailure(call: Call<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse.IdfcGetOtpResponse>, t: Throwable) {
                liveDataIdfcGetOtp?.postValue(com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse.Error(
                    TransactionUtils.handleExceptions(t)))
            }
        })
    }
}