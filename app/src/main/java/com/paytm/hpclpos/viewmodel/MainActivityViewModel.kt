package com.paytm.hpclpos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount.RechargeCcmsAccountRequest
import com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount.RechargeCcmsAccountResponse
import com.paytm.hpclpos.livedatamodels.neftodometerbymono.NEFTOdoMeterRequest
import com.paytm.hpclpos.livedatamodels.neftodometerbymono.NEFTOdoMeterResponse
import com.paytm.hpclpos.livedatamodels.authenticateotp.AuthOtpRequest
import com.paytm.hpclpos.livedatamodels.authenticateotp.AuthOtpResponse
import com.paytm.hpclpos.livedatamodels.cashodometermobilereload.CashOdometerReloadRequest
import com.paytm.hpclpos.livedatamodels.cashodometermobilereload.CashOdometerReloadResponse
import com.paytm.hpclpos.livedatamodels.cashreload.CashReloadRequest
import com.paytm.hpclpos.livedatamodels.cashreload.CashReloadResponse
import com.paytm.hpclpos.livedatamodels.cashreloadbyreload.CashReloadByResponseByReload
import com.paytm.hpclpos.livedatamodels.cashreloadbyreload.CashReloadRequestByReload
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeResponse
import com.paytm.hpclpos.livedatamodels.ccmsrechargebycheque.CcmsRechargeByChqRequest
import com.paytm.hpclpos.livedatamodels.ccmsrechargebycheque.CcmsRechargeByChqResponse
import com.paytm.hpclpos.livedatamodels.ccmsrechargebymono.CcmsRechargeByMoNoRequest
import com.paytm.hpclpos.livedatamodels.ccmsrechargebymono.CcmsRechargeByMoNoResponse
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleResponse
import com.paytm.hpclpos.livedatamodels.chequeodometerreading.ChequeOdoMeterResponse
import com.paytm.hpclpos.livedatamodels.chequeodometerreading.ChequeOdometerReadingRequest
import com.paytm.hpclpos.livedatamodels.chequereloadbycheque.ChequeReloadByChequeRequest
import com.paytm.hpclpos.livedatamodels.chequereloadbycheque.ChequeReloadResponseByCheque
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenRequest
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenResponse
import com.paytm.hpclpos.livedatamodels.generateapi.GenerateOtpRequest
import com.paytm.hpclpos.livedatamodels.generateapi.GenerateOtpResponse
import com.paytm.hpclpos.livedatamodels.neftreloadbyneft.NEFTREloadByResponseReload
import com.paytm.hpclpos.livedatamodels.neftreloadbyneft.NEFTReloadRequsetByNEFT
import com.paytm.hpclpos.livedatamodels.sendotp.SendOtpRequest
import com.paytm.hpclpos.livedatamodels.sendotp.SendOtpResponse
import com.paytm.hpclpos.livedatamodels.validatepin.ValidatePinRequest
import com.paytm.hpclpos.livedatamodels.validatepin.ValidatePinResponse
import com.paytm.hpclpos.livedatamodels.walletbalance.WalletBalanceRequest
import com.paytm.hpclpos.livedatamodels.walletbalance.WalletBalanceResponse
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel : ViewModel() {

    var liveDataGenerateToken: MutableLiveData<GenerateTokenResponse> = MutableLiveData()

    fun getLiveDataObserverGenerateToken(): MutableLiveData<GenerateTokenResponse> {
        return liveDataGenerateToken
    }

    fun makeApiCallGenerateToken(generateTokenRequest: GenerateTokenRequest) {
        Constants.MainUrlChanged.forToken = "1"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getGenerateToken(generateTokenRequest)
        retroService.enqueue(object : Callback<GenerateTokenResponse> {
            override fun onResponse(call: Call<GenerateTokenResponse>, response: Response<GenerateTokenResponse>) {
                liveDataGenerateToken.postValue(response.body())
            }

            override fun onFailure(call: Call<GenerateTokenResponse>, t: Throwable) {
                liveDataGenerateToken.postValue(null)
            }

        })
    }

    var liveDataReloadApiByCash: MutableLiveData<CashReloadResponse>? = null
    fun getReloadApiByCash(): MutableLiveData<CashReloadResponse> {
        return liveDataReloadApiByCash!!
    }

    fun makeApiReloadApiByCash(cashReloadRequest: CashReloadRequest) {
        liveDataReloadApiByCash = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getReloadApiByCash(cashReloadRequest)
        retroService?.enqueue(object : Callback<CashReloadResponse> {


            override fun onResponse(call: Call<CashReloadResponse?>, response: Response<CashReloadResponse?>) {
                if (response.isSuccessful) {
                    liveDataReloadApiByCash!!.value = response.body()
                } else {
                    liveDataReloadApiByCash!!.value = null

                }

            }

            override fun onFailure(call: Call<CashReloadResponse>, t: Throwable) {
                liveDataReloadApiByCash!!.value = null

            }

        })
    }


    var liveDataSendOtp: MutableLiveData<SendOtpResponse>? = null
    fun getliveDataSendOtp(): MutableLiveData<SendOtpResponse> {
        return liveDataSendOtp!!
    }

    fun makeApiSendOtp(sendOtpRequest: SendOtpRequest) {
        liveDataSendOtp = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getSendOtp(sendOtpRequest)
        retroService?.enqueue(object : Callback<SendOtpResponse> {

            override fun onResponse(call: Call<SendOtpResponse?>, response: Response<SendOtpResponse?>) {
                if (response.isSuccessful) {
                    liveDataSendOtp!!.value = response.body()
                } else {

                    liveDataSendOtp!!.value = null


                }

            }

            override fun onFailure(call: Call<SendOtpResponse>, t: Throwable) {
                liveDataSendOtp!!.value = null

            }

        })
    }


    var liveDataAuthOtp: MutableLiveData<AuthOtpResponse>? = null
    fun getliveAuthOtp(): MutableLiveData<AuthOtpResponse> {
        return liveDataAuthOtp!!
    }

    fun makeApiAuthOtp(authOtpRequest: AuthOtpRequest) {
        liveDataAuthOtp = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getAuthOtp(authOtpRequest)
        retroService?.enqueue(object : Callback<AuthOtpResponse> {

            override fun onResponse(call: Call<AuthOtpResponse?>, response: Response<AuthOtpResponse?>) {
                if (response.isSuccessful) {
                    liveDataAuthOtp!!.value = response.body()
                } else {
                    liveDataAuthOtp!!.value = null
                }
            }
            override fun onFailure(call: Call<AuthOtpResponse>, t: Throwable) {
                liveDataAuthOtp!!.value = null
            }
        })
    }

    var liveDataValidatePinCard: MutableLiveData<ValidatePinResponse>? = null
    fun getliveValidatePin(): MutableLiveData<ValidatePinResponse> {
        return liveDataValidatePinCard!!
    }

    fun makeApiValidatePin(validatePinRequest: ValidatePinRequest) {
        liveDataValidatePinCard = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getValidatePin(validatePinRequest)
        retroService?.enqueue(object : Callback<ValidatePinResponse> {

            override fun onResponse(call: Call<ValidatePinResponse?>, response: Response<ValidatePinResponse?>) {
                if (response.isSuccessful) {
                    liveDataValidatePinCard!!.value = response.body()
                } else {

                    liveDataValidatePinCard!!.value = null


                }

            }

            override fun onFailure(call: Call<ValidatePinResponse>, t: Throwable) {
                liveDataValidatePinCard!!.value = null

            }

        })
    }

    var liveDataReloadByCash: MutableLiveData<CashReloadByResponseByReload>? = null
    fun getliveDataReloadByCash(): MutableLiveData<CashReloadByResponseByReload> {
        return liveDataReloadByCash!!
    }

    fun makeApiReloadByCash(cashReloadRequestByReload: CashReloadRequestByReload) {
        liveDataReloadByCash = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getcashReload(cashReloadRequestByReload)
        retroService?.enqueue(object : Callback<CashReloadByResponseByReload> {

            override fun onResponse(call: Call<CashReloadByResponseByReload?>, response: Response<CashReloadByResponseByReload?>) {
                if (response.isSuccessful) {
                    liveDataReloadByCash!!.value = response.body()
                } else {
                    liveDataReloadByCash!!.value = null

                }

            }

            override fun onFailure(call: Call<CashReloadByResponseByReload>, t: Throwable) {
                liveDataReloadByCash!!.value = null

            }

        })
    }


    var liveDataReloadByCheque: MutableLiveData<ChequeReloadResponseByCheque>? = null
    fun getliveDataReloadByCheque(): MutableLiveData<ChequeReloadResponseByCheque> {
        return liveDataReloadByCheque!!
    }

    fun makeApiReloadByCheque(chequeReloadByChequeRequest: ChequeReloadByChequeRequest) {
        liveDataReloadByCheque = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getchequeReload(chequeReloadByChequeRequest)
        retroService?.enqueue(object : Callback<ChequeReloadResponseByCheque> {

            override fun onResponse(call: Call<ChequeReloadResponseByCheque?>, response: Response<ChequeReloadResponseByCheque?>) {
                if (response.isSuccessful) {
                    liveDataReloadByCheque!!.value = response.body()
                } else {
                    liveDataReloadByCheque!!.value = null
                }
            }

            override fun onFailure(call: Call<ChequeReloadResponseByCheque>, t: Throwable) {
                liveDataReloadByCheque!!.value = null
            }
        })
    }


    var liveDataReloadByNEFT: MutableLiveData<NEFTREloadByResponseReload>? = null
    fun getliveDataReloadByNEFT(): MutableLiveData<NEFTREloadByResponseReload> {
        return liveDataReloadByNEFT!!
    }

    fun makeApiReloadByNEFT(neftReloadRequsetByNEFT: NEFTReloadRequsetByNEFT) {
        liveDataReloadByNEFT = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getneftReload(neftReloadRequsetByNEFT)
        retroService?.enqueue(object : Callback<NEFTREloadByResponseReload> {

            override fun onResponse(call: Call<NEFTREloadByResponseReload?>, response: Response<NEFTREloadByResponseReload?>) {
                if (response.isSuccessful) {
                    liveDataReloadByNEFT!!.value = response.body()
                } else {

                    liveDataReloadByNEFT!!.value = null


                }

            }

            override fun onFailure(call: Call<NEFTREloadByResponseReload>, t: Throwable) {
                liveDataReloadByNEFT!!.value = null

            }

        })
    }

    var liveDataGetWalletBalance: MutableLiveData<WalletBalanceResponse>? = null
    fun getliveDataliveDataGetWalletBalance(): MutableLiveData<WalletBalanceResponse> {
        return liveDataGetWalletBalance!!
    }

    fun makeApiliveDataGetWalletBalance(walletBalanceRequest: WalletBalanceRequest) {
        liveDataGetWalletBalance = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getliveDataGetWalletBalance(walletBalanceRequest)
        retroService?.enqueue(object : Callback<WalletBalanceResponse> {

            override fun onResponse(call: Call<WalletBalanceResponse?>, response: Response<WalletBalanceResponse?>) {
                if (response.isSuccessful) {
                    liveDataGetWalletBalance!!.value = response.body()

                } else {

                    liveDataGetWalletBalance!!.value = null


                }

            }

            override fun onFailure(call: Call<WalletBalanceResponse>, t: Throwable) {
                liveDataGetWalletBalance!!.value = null
                //Log.e("TAG","API NOT CALLED")

            }

        })
    }



    var liveDataCreditReloadByMoNo: MutableLiveData<CashOdometerReloadResponse>? = null
    fun getliveDataCreditReloadByMoNo(): MutableLiveData<CashOdometerReloadResponse> {
        return liveDataCreditReloadByMoNo!!
    }

    fun makeApiCreditReloadByMoNo(cashOdometerReloadRequest: CashOdometerReloadRequest) {
        liveDataCreditReloadByMoNo = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCashReloadByMoNo(cashOdometerReloadRequest)
        retroService?.enqueue(object : Callback<CashOdometerReloadResponse> {

            override fun onResponse(call: Call<CashOdometerReloadResponse?>, response: Response<CashOdometerReloadResponse?>) {
                if (response.isSuccessful) {
                    liveDataCreditReloadByMoNo!!.value = response.body()
                } else {
                    liveDataCreditReloadByMoNo!!.value = null
                }

            }


            override fun onFailure(call: Call<CashOdometerReloadResponse>, t: Throwable) {
                liveDataCreditReloadByMoNo!!.value = null
            }

        })
    }


    var liveDatachequeReloadByMoNo: MutableLiveData<ChequeOdoMeterResponse>? = null
    fun getliveDataChequeReloadByMoNo(): MutableLiveData<ChequeOdoMeterResponse> {
        return liveDatachequeReloadByMoNo!!
    }

    fun makeApiChequeReloadByMoNo(chequeOdometerReadingRequest: ChequeOdometerReadingRequest) {
        liveDatachequeReloadByMoNo = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getChequeOdoMeterByMoNo(chequeOdometerReadingRequest)
        retroService?.enqueue(object : Callback<ChequeOdoMeterResponse> {

            override fun onResponse(call: Call<ChequeOdoMeterResponse?>, response: Response<ChequeOdoMeterResponse?>) {
                if (response.isSuccessful) {
                    liveDatachequeReloadByMoNo!!.value = response.body()
                } else {
                    liveDatachequeReloadByMoNo!!.value = null
                }

            }


            override fun onFailure(call: Call<ChequeOdoMeterResponse>, t: Throwable) {
                liveDatachequeReloadByMoNo!!.value = null
            }

        })
    }


    var liveDataneftReloadByMoNo: MutableLiveData<NEFTOdoMeterResponse>? = null
    fun getliveDataNeftReloadByMoNo(): MutableLiveData<NEFTOdoMeterResponse> {
        return liveDataneftReloadByMoNo!!
    }

    fun makeApiNeftReloadByMoNo(neftOdoMeterRequest: NEFTOdoMeterRequest) {
        liveDataneftReloadByMoNo = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getNeftOdoMeterByMoNo(neftOdoMeterRequest)
        retroService?.enqueue(object : Callback<NEFTOdoMeterResponse> {

            override fun onResponse(call: Call<NEFTOdoMeterResponse?>, response: Response<NEFTOdoMeterResponse?>) {
                if (response.isSuccessful) {
                    liveDataneftReloadByMoNo!!.value = response.body()
                } else {
                    liveDataneftReloadByMoNo!!.value = null
                }

            }


            override fun onFailure(call: Call<NEFTOdoMeterResponse>, t: Throwable) {
                liveDataneftReloadByMoNo!!.value = null
            }

        })
    }


    var liveDataCcmsRechargeByMoNo: MutableLiveData<CcmsRechargeByMoNoResponse>? = null
    fun getliveDataCcmsRechargeByMoNo(): MutableLiveData<CcmsRechargeByMoNoResponse> {
        return liveDataCcmsRechargeByMoNo!!
    }

    fun makeApiCcmsRechargeByMoNo(ccmsRechargeByMoNoRequest: CcmsRechargeByMoNoRequest) {
        liveDataCcmsRechargeByMoNo = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsRechargeByMoNo(ccmsRechargeByMoNoRequest)
        retroService?.enqueue(object : Callback<CcmsRechargeByMoNoResponse> {

            override fun onResponse(call: Call<CcmsRechargeByMoNoResponse?>, response: Response<CcmsRechargeByMoNoResponse?>) {
                if (response.isSuccessful) {
                    liveDataCcmsRechargeByMoNo!!.value = response.body()
                } else {
                   // liveDataCcmsRechargeByCash!!.value = null
                }

            }


            override fun onFailure(call: Call<CcmsRechargeByMoNoResponse>, t: Throwable) {
                liveDataCcmsRechargeByMoNo!!.value = null
            }

        })

    }

    var liveDataCcmsRechargeByCheque: MutableLiveData<CcmsRechargeByChqResponse>? = null
    fun getliveDataCcmsRechargeByCheque(): MutableLiveData<CcmsRechargeByChqResponse> {
        return liveDataCcmsRechargeByCheque!!
    }

    fun makeApiCcmsRechargeByCheque(ccmsRechargeByChqRequest: CcmsRechargeByChqRequest) {
        liveDataCcmsRechargeByCheque = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsRechargeByCheque(ccmsRechargeByChqRequest)
        retroService?.enqueue(object : Callback<CcmsRechargeByChqResponse> {

            override fun onResponse(call: Call<CcmsRechargeByChqResponse?>, response: Response<CcmsRechargeByChqResponse?>) {
                if (response.isSuccessful) {
                    liveDataCcmsRechargeByCheque!!.value = response.body()
                } else {
                    liveDataCcmsRechargeByCheque!!.value = null
                }

            }


            override fun onFailure(call: Call<CcmsRechargeByChqResponse>, t: Throwable) {
                liveDataCcmsRechargeByCheque!!.value = null
            }

        })

    }

    var liveDataCcmsRecharge: MutableLiveData<RechargeCcmsAccountResponse>? = null
    fun getliveDataCcmsRecharge(): MutableLiveData<RechargeCcmsAccountResponse> {
        return liveDataCcmsRecharge!!
    }

    fun makeApiCcmsRecharge(rechargeCcmsAccountRequest: RechargeCcmsAccountRequest) {
        liveDataCcmsRecharge = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsRecharge(rechargeCcmsAccountRequest)
        retroService?.enqueue(object : Callback<RechargeCcmsAccountResponse> {

            override fun onResponse(call: Call<RechargeCcmsAccountResponse?>, response: Response<RechargeCcmsAccountResponse?>) {
                if (response.isSuccessful) {
                    liveDataCcmsRecharge!!.value = response.body()
                } else {
                    liveDataCcmsRecharge!!.value = null
                }
            }

            override fun onFailure(call: Call<RechargeCcmsAccountResponse>, t: Throwable) {
                liveDataCcmsRecharge!!.value = null
            }
        })
    }

    var liveDatagetOtp: MutableLiveData<GenerateOtpResponse>? = null
    fun getliveDatagetOtp(): MutableLiveData<GenerateOtpResponse> {
        return liveDatagetOtp!!
    }

    fun makeApigetOTP(generateOtpRequest: GenerateOtpRequest) {
        liveDatagetOtp = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getSendOtp(generateOtpRequest)
        retroService?.enqueue(object : Callback<GenerateOtpResponse> {

            override fun onResponse(
                call: Call<GenerateOtpResponse?>,
                response: Response<GenerateOtpResponse?>
            ) {
                if (response.isSuccessful) {
                    liveDatagetOtp!!.value = response.body()
                } else {
                    liveDatagetOtp!!.value = null
                }
            }

            override fun onFailure(call: Call<GenerateOtpResponse>, t: Throwable) {
                liveDatagetOtp!!.value = null
            }
        })
    }


    var liveDataCcmsSale: MutableLiveData<CCMSSaleResponse>? = null
    fun getliveDataCcmsSale(): MutableLiveData<CCMSSaleResponse> {
        return liveDataCcmsSale!!
    }

    fun makeApiCcmsSale(ccmsSaleRequest: CCMSSaleRequest) {
        liveDataCcmsSale = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsSale(ccmsSaleRequest)
        retroService?.enqueue(object : Callback<CCMSSaleResponse> {

            override fun onResponse(
                call: Call<CCMSSaleResponse?>,
                response: Response<CCMSSaleResponse?>
            ) {
                if (response.isSuccessful) {
                    liveDataCcmsSale!!.value = response.body()
                } else {
                    liveDataCcmsSale!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSSaleResponse>, t: Throwable) {
                liveDataCcmsSale!!.value = null
            }
        })
    }

    var liveDataCcmsRechargeSale: MutableLiveData<CCMSRechargeResponse>? = null
    fun getliveDataCcmsRechargeSale(): MutableLiveData<CCMSRechargeResponse> {
        return liveDataCcmsRechargeSale!!
    }

    fun makeApiCcmsRechargeSale(ccmsRechargeRequest: CCMSRechargeRequest) {
        liveDataCcmsRechargeSale = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getCcmsRechargeSale(ccmsRechargeRequest)
        retroService?.enqueue(object : Callback<CCMSRechargeResponse> {

            override fun onResponse(
                call: Call<CCMSRechargeResponse?>,
                response: Response<CCMSRechargeResponse?>
            ) {
                if (response.isSuccessful) {
                    liveDataCcmsRechargeSale!!.value = response.body()
                } else {
                    liveDataCcmsRechargeSale!!.value = null
                }
            }

            override fun onFailure(call: Call<CCMSRechargeResponse>, t: Throwable) {
                liveDataCcmsRechargeSale!!.value = null
            }
        })
    }
}
