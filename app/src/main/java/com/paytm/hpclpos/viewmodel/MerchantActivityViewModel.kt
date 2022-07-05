package com.paytm.hpclpos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryResponse
import com.paytm.hpclpos.livedatamodels.CCMSBalance.CCMSBalanceRequest
import com.paytm.hpclpos.livedatamodels.CCMSBalance.CCMSBalanceResponse
import com.paytm.hpclpos.livedatamodels.ChangeCardPinRequest.ChangeCardPinRequest
import com.paytm.hpclpos.livedatamodels.ControlPinChange.ControlPinChange
import com.paytm.hpclpos.livedatamodels.Reload.ReloadRequest
import com.paytm.hpclpos.livedatamodels.UnblockCardPin.UnblockCardPinRequest
import com.paytm.hpclpos.livedatamodels.balancetransfer.BalanceTransferRequest
import com.paytm.hpclpos.livedatamodels.batchsettlement.BatchSettlementRequest
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPRequest
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPResponse
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeResponse
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpRequest
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpResponse
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MerchantActivityViewModel : ViewModel() {

    var liveDatabatchSettlement: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataBatchSettlement(): MutableLiveData<CCMSRechargeResponse> {
        return liveDatabatchSettlement!!
    }

    fun makeApiBatchSettlement(batchSettlementRequest: BatchSettlementRequest) {
        liveDatabatchSettlement = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBatchSettlement(batchSettlementRequest)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(
                call: Call<CCMSRechargeResponse?>,
                response: Response<CCMSRechargeResponse?>
            ) {
                if (response.isSuccessful) {
                    liveDatabatchSettlement!!.value = response.body()
                } else {
                    liveDatabatchSettlement!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDatabatchSettlement!!.value = null
            }
        })
    }

    var liveDataBalanceTransfer: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataBalanceTransfer(): MutableLiveData<CCMSRechargeResponse> {
        return liveDataBalanceTransfer!!
    }

    fun makeApiBalanceTransfer(balanceTransferRequest: BalanceTransferRequest) {
        liveDataBalanceTransfer = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBalanceTransfer(balanceTransferRequest)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(call: Call<CCMSRechargeResponse?>, response: Response<CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataBalanceTransfer!!.value = response.body()
                } else {
                    liveDataBalanceTransfer!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDataBalanceTransfer!!.value = null
            }
        })
    }

    var liveDataGenerateOTP: MutableLiveData<GenerateOTPResponse>? = null
    fun getliveDataGenerateOTP(): MutableLiveData<GenerateOTPResponse> {
        return liveDataGenerateOTP!!
    }

    fun makeApiGenerateOTP(genearteOtpRequest: GenerateOTPRequest) {
        liveDataGenerateOTP = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getOTP(genearteOtpRequest)
        retroService.enqueue(object : Callback<GenerateOTPResponse> {

            override fun onResponse(call: Call<GenerateOTPResponse?>, response: Response<GenerateOTPResponse?>) {
                if (response.isSuccessful) {
                    liveDataGenerateOTP!!.value = response.body()
                } else {
                    liveDataGenerateOTP!!.value = null
                }
            }

            override fun onFailure(call: Call<GenerateOTPResponse>, t: Throwable) {
                liveDataGenerateOTP!!.value = null
            }
        })
    }

    var liveDataReload: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataReload(): MutableLiveData<CCMSRechargeResponse> {
        return liveDataReload!!
    }

    fun makeApiReload(reloadRequest: ReloadRequest) {
        liveDataReload = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getReload(reloadRequest)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(
                    call: Call<CCMSRechargeResponse?>,
                    response: Response<CCMSRechargeResponse?>
            ) {
                if (response.isSuccessful) {
                    liveDataReload!!.value = response.body()
                } else {
                    liveDataReload!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDataReload!!.value = null
            }
        })
    }

    var liveDataBalanceEnquiry: MutableLiveData<BalanceEnquiryResponse>? = null
    fun getliveDataBalanceEnquiry(): MutableLiveData<BalanceEnquiryResponse> {
        return liveDataBalanceEnquiry!!
    }

    fun makeApiBalanceEnquiry(balanceEnquiryRequest: BalanceEnquiryRequest) {
        liveDataBalanceEnquiry = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getBalanceEnquiry(balanceEnquiryRequest)
        retroService.enqueue(object : Callback<BalanceEnquiryResponse> {

            override fun onResponse(call: Call<BalanceEnquiryResponse?>, response: Response<BalanceEnquiryResponse?>) {
                if (response.isSuccessful) {
                    liveDataBalanceEnquiry!!.value = response.body()
                } else {
                    liveDataBalanceEnquiry!!.value = null
                }
            }

            override fun onFailure(call: Call<BalanceEnquiryResponse>, t: Throwable) {
                liveDataBalanceEnquiry!!.value = null
            }
        })
    }

    var liveDataCCMSBalanceEnquiry: MutableLiveData<CCMSBalanceResponse>? = null
    fun getliveDataCCMSBalanceEnquiry(): MutableLiveData<CCMSBalanceResponse> {
        return liveDataCCMSBalanceEnquiry!!
    }

    fun makeApiCCMSBalanceEnquiry(ccmsBalanceRequest: CCMSBalanceRequest) {
        liveDataCCMSBalanceEnquiry = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCCMSBalanceEnquiry(ccmsBalanceRequest)
        retroService.enqueue(object : Callback<CCMSBalanceResponse> {

            override fun onResponse(call: Call<CCMSBalanceResponse?>, response: Response<CCMSBalanceResponse?>) {
                if (response.isSuccessful) {
                    liveDataCCMSBalanceEnquiry!!.value = response.body()
                } else {
                    liveDataCCMSBalanceEnquiry!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSBalanceResponse>, t: Throwable) {
                liveDataCCMSBalanceEnquiry!!.value = null
            }
        })
    }

    var liveDataUnblockCardPin: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataUnblockCardPin(): MutableLiveData<CCMSRechargeResponse> {
        return liveDataUnblockCardPin!!
    }


    fun makeApiUnblockCardPin(unblockCardPinRequest: UnblockCardPinRequest) {
        liveDataUnblockCardPin = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getUnblockCardPin(unblockCardPinRequest)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(call: Call<CCMSRechargeResponse?>, response: Response<CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataUnblockCardPin!!.value = response.body()
                } else {
                    liveDataUnblockCardPin!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDataUnblockCardPin!!.value = null
            }
        })
    }

    var liveDataChangeCardPin: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataChangeCardPin(): MutableLiveData<CCMSRechargeResponse> {
        return liveDataChangeCardPin!!
    }


    fun makeApiChangeCardPin(changeCardPinRequest: ChangeCardPinRequest) {
        liveDataChangeCardPin = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getChangeCardPin(changeCardPinRequest)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(call: Call<CCMSRechargeResponse?>, response: Response<CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataChangeCardPin!!.value = response.body()
                } else {
                    liveDataChangeCardPin!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDataChangeCardPin!!.value = null
            }
        })
    }

    var liveDataControlPinChange: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataControlPinChange(): MutableLiveData<CCMSRechargeResponse> {
        return liveDataControlPinChange!!
    }


    fun makeApiControlPinChange(controlPinChange: ControlPinChange) {
        liveDataControlPinChange = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getChangeCardPin(controlPinChange)
        retroService.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(call: Call<CCMSRechargeResponse?>, response: Response<CCMSRechargeResponse?>) {
                if (response.isSuccessful) {
                    liveDataControlPinChange!!.value = response.body()
                } else {
                    liveDataControlPinChange!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDataControlPinChange!!.value = null
            }
        })
    }

    var liveDataIdfcGetOtp: MutableLiveData<IdfcGetOtpResponse>? = null
    fun getliveDataIdfcGetOtp(): MutableLiveData<IdfcGetOtpResponse> {
        return liveDataIdfcGetOtp!!
    }

    fun makeApiIdfcGetOtp(idfcGetOtpRequest: IdfcGetOtpRequest) {
        liveDataIdfcGetOtp = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getIdfcGetOtp(idfcGetOtpRequest)
        retroService.enqueue(object : Callback<IdfcGetOtpResponse> {

            override fun onResponse(call: Call<IdfcGetOtpResponse?>, response: Response<IdfcGetOtpResponse?>) {
                if (response.isSuccessful) {
                    liveDataIdfcGetOtp!!.value = response.body()
                } else {
                    liveDataIdfcGetOtp!!.value = null
                }
            }

            override fun onFailure(call: Call<IdfcGetOtpResponse>, t: Throwable) {
                liveDataIdfcGetOtp!!.value = null
            }
        })
    }
}