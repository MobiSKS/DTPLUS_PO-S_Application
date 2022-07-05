package com.paytm.hppay.api

import com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount.RechargeCcmsAccountRequest
import com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount.RechargeCcmsAccountResponse
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
import com.paytm.hpclpos.livedatamodels.cardfee.CardFeeRequest
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPRequest
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPResponse
import com.paytm.hpclpos.livedatamodels.neftodometerbymono.NEFTOdoMeterRequest
import com.paytm.hpclpos.livedatamodels.neftodometerbymono.NEFTOdoMeterResponse
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationRequest
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationResponse
import com.paytm.hpclpos.livedatamodels.authenticateotp.AuthOtpRequest
import com.paytm.hpclpos.livedatamodels.authenticateotp.AuthOtpResponse
import com.paytm.hpclpos.livedatamodels.batchupload.BatchUploadRequest
import com.paytm.hpclpos.livedatamodels.batchupload.BatchUploadResponse
import com.paytm.hpclpos.livedatamodels.ccmssalebycard.AddCCMSRequest
import com.paytm.hpclpos.livedatamodels.ccmssalebycard.CCMSSaleModel
import com.paytm.hpclpos.livedatamodels.cashodometermobilereload.CashOdometerReloadRequest
import com.paytm.hpclpos.livedatamodels.cashodometermobilereload.CashOdometerReloadResponse
import com.paytm.hpclpos.livedatamodels.cashreload.CashReloadRequest
import com.paytm.hpclpos.livedatamodels.cashreload.CashReloadResponse
import com.paytm.hpclpos.livedatamodels.cashreloadbycheque.CashReloadRequestByCheque
import com.paytm.hpclpos.livedatamodels.cashreloadbycheque.CashReloadResponseByCheque
import com.paytm.hpclpos.livedatamodels.cashreloadbyneft.CashReloadByNEFTResponse
import com.paytm.hpclpos.livedatamodels.cashreloadbyneft.NEFTByRequest
import com.paytm.hpclpos.livedatamodels.cashreloadbyreload.CashReloadByResponseByReload
import com.paytm.hpclpos.livedatamodels.cashreloadbyreload.CashReloadRequestByReload
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeResponse
import com.paytm.hpclpos.livedatamodels.ccmsrechargebycash.CcmsRechargeByCashRequest
import com.paytm.hpclpos.livedatamodels.ccmsrechargebycash.CcmsRechargeByCashResponse
import com.paytm.hpclpos.livedatamodels.ccmsrechargebycheque.CcmsRechargeByChqRequest
import com.paytm.hpclpos.livedatamodels.ccmsrechargebycheque.CcmsRechargeByChqResponse
import com.paytm.hpclpos.livedatamodels.ccmsrechargebymono.CcmsRechargeByMoNoRequest
import com.paytm.hpclpos.livedatamodels.ccmsrechargebymono.CcmsRechargeByMoNoResponse
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleResponse
import com.paytm.hpclpos.livedatamodels.ccmssalebymono.CcmsSaleByMoNoRequest
import com.paytm.hpclpos.livedatamodels.ccmssalebymono.CcmsSaleByMoNoResponse
import com.paytm.hpclpos.livedatamodels.chequeodometerreading.ChequeOdoMeterResponse
import com.paytm.hpclpos.livedatamodels.chequeodometerreading.ChequeOdometerReadingRequest
import com.paytm.hpclpos.livedatamodels.chequereloadbycheque.ChequeReloadByChequeRequest
import com.paytm.hpclpos.livedatamodels.chequereloadbycheque.ChequeReloadResponseByCheque
import com.paytm.hpclpos.livedatamodels.configurationforterminal.ConfigurationRequest
import com.paytm.hpclpos.livedatamodels.configurationforterminal.ConfigurationResponse
import com.paytm.hpclpos.livedatamodels.creditsale.CreditSaleRequest
import com.paytm.hpclpos.livedatamodels.creditsale.CreditSaleResponse
import com.paytm.hpclpos.livedatamodels.creditsalebymono.CreditSaleByMoNoRequest
import com.paytm.hpclpos.livedatamodels.creditsalebymono.CreditSaleByMoNoResponse
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenRequest
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenResponse
import com.paytm.hpclpos.livedatamodels.generateapi.GenerateOtpRequest
import com.paytm.hpclpos.livedatamodels.generateapi.GenerateOtpResponse
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpRequest
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpResponse
import com.paytm.hpclpos.livedatamodels.neftreloadbyneft.NEFTREloadByResponseReload
import com.paytm.hpclpos.livedatamodels.neftreloadbyneft.NEFTReloadRequsetByNEFT
import com.paytm.hpclpos.livedatamodels.sendotp.SendOtpRequest
import com.paytm.hpclpos.livedatamodels.sendotp.SendOtpResponse
import com.paytm.hpclpos.livedatamodels.validatepin.ValidatePinRequest
import com.paytm.hpclpos.livedatamodels.validatepin.ValidatePinResponse
import com.paytm.hpclpos.livedatamodels.walletbalance.WalletBalanceRequest
import com.paytm.hpclpos.livedatamodels.walletbalance.WalletBalanceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    @POST(Utils.GET_GENERATE_TOKEN)
    fun getGenerateToken(@Body generateTokenRequest: GenerateTokenRequest): Call<GenerateTokenResponse>

    @POST(Utils.GET_SET_ALL_CONFIGURATION_FOR_TERMINAL)
    fun getConfiguration(@Body configurationRequest: ConfigurationRequest): Call<ConfigurationResponse>

    @POST(Utils.GET_RELOAD_API_BY_CASH)
    fun getReloadApiByCash(@Body cashReloadRequest: CashReloadRequest): Call<CashReloadResponse>

    @POST(Utils.GET_RELOAD_API_BY_CHEQUE)
    fun getReloadApiByCheque(@Body CashReloadRequestByCheque: CashReloadRequestByCheque): Call<CashReloadResponseByCheque>

    @POST(Utils.GET_RELOAD_API_BY_NEFT)
    fun getReloadApiByNeft(@Body neftByRequest: NEFTByRequest): Call<CashReloadByNEFTResponse>

    @POST(Utils.GET_CCMS_SALE_BY_CARD)
    fun getccmssalebycard(@Body ccmsSaleByCardRequest: AddCCMSRequest): Call<CCMSSaleModel>

    @POST(Utils.GET_CREDIT_TXN_BY_CARD)
    fun getCreditTxnByCard(@Body addCCMSRequest: AddCCMSRequest): Call<CCMSSaleModel>

    @POST(Utils.GET_SEND_OTP)
    fun getSendOtp(@Body sendOtpRequest: SendOtpRequest): Call<SendOtpResponse>

    @POST(Utils.GET_AUTH_OTP)
    fun getAuthOtp(@Body authOtpRequest: AuthOtpRequest): Call<AuthOtpResponse>

    @POST(Utils.GET_CCMS_SALE_BY_MO_NO)
    fun getCcmsSaleByMoNo(@Body ccmsSaleByMoNoRequest: CcmsSaleByMoNoRequest): Call<CcmsSaleByMoNoResponse>

    @POST(Utils.GET_CARD_SALE_BY_NO)
    fun getCardSaleByMobileNo(@Body ccmsSaleByMoNoRequest: CcmsSaleByMoNoRequest): Call<CcmsSaleByMoNoResponse>

    @POST(Utils.GET_VALIDATE_PIN)
    fun getValidatePin(@Body validatePinRequest: ValidatePinRequest): Call<ValidatePinResponse>

    @POST(Utils.GET_CASH_RELOAD)
    fun getcashReload(@Body cashReloadRequestByReload: CashReloadRequestByReload): Call<CashReloadByResponseByReload>

    @POST(Utils.GET_CHEQUE_RELOAD)
    fun getchequeReload(@Body chequeReloadByChequeRequest: ChequeReloadByChequeRequest): Call<ChequeReloadResponseByCheque>

    @POST(Utils.GET_NEFT_RELOAD)
    fun getneftReload(@Body neftReloadRequsetByNEFT: NEFTReloadRequsetByNEFT): Call<NEFTREloadByResponseReload>

    @POST(Utils.GET_CREDIT_TXN_BY_MO_NO)
    fun getCreditSaleByMoNo(@Body creditSaleByMoNoRequest: CreditSaleByMoNoRequest): Call<CreditSaleByMoNoResponse>


    @POST(Utils.GET_CASH_RELOAD_MOBILE_NO)
    fun getCashReloadByMoNo(@Body cashOdometerReloadRequest: CashOdometerReloadRequest): Call<CashOdometerReloadResponse>

    @POST(Utils.GET_CHEQUE_RELOAD_MOBILE_NO)
    fun getChequeOdoMeterByMoNo(@Body chequeOdometerReadingRequest: ChequeOdometerReadingRequest): Call<ChequeOdoMeterResponse>

    @POST(Utils.GET_NEFT_RELOAD_MOBILE_NO)
    fun getNeftOdoMeterByMoNo(@Body neftOdoMeterRequest: NEFTOdoMeterRequest): Call<NEFTOdoMeterResponse>

    @POST(Utils.GET_WALLET_BALANCE_LIMIT)
    fun getliveDataGetWalletBalance(@Body walletBalanceRequest: WalletBalanceRequest): Call<WalletBalanceResponse>

    @POST(Utils.GET_CARD_SALE_BY_CARD)
    fun cardSaleByCard(@Body addCCMSRequest: AddCCMSRequest): Call<CCMSSaleModel>

    @POST(Utils.GET_CREDIT_SALE_BY_CARD)
    fun creditSaleByCard(@Body creditSaleRequest: CreditSaleRequest): Call<CreditSaleResponse>

    @POST(Utils.GET_BATCH_UPLOAD)
    fun batchUpload(@Body batchUploadRequest: BatchUploadRequest): Call<BatchUploadResponse>

    @POST(Utils.GET_CCMS_RECHARGE_BY_CASH)
    fun getCcmsRechargeByCash(@Body ccmsRechargeByCashRequest: CcmsRechargeByCashRequest): Call<CcmsRechargeByCashResponse>

    @POST(Utils.GET_CCMS_RECHARGE_BY_MONO)
    fun getCcmsRechargeByMoNo(@Body ccmsRechargeByMoNoRequest: CcmsRechargeByMoNoRequest): Call<CcmsRechargeByMoNoResponse>

    @POST(Utils.GET_CCMS_RECHARGE_BY_CHEQUE)
    fun getCcmsRechargeByCheque(@Body ccmsRechargeByChqRequest: CcmsRechargeByChqRequest): Call<CcmsRechargeByChqResponse>

    @POST(Utils.GET_CCMS_RECHARGE_API)
    fun getCcmsRecharge(@Body rechargeCcmsAccountRequest: RechargeCcmsAccountRequest): Call<RechargeCcmsAccountResponse>

    @POST(Utils.GET_GENERATE_OTP)
    fun getSendOtp(@Body generateOtpRequest: GenerateOtpRequest): Call<GenerateOtpResponse>

    @POST(Utils.GET_CCMS_SALE)
    fun getCcmsSale(@Body ccmsSaleRequest: CCMSSaleRequest): Call<CCMSSaleResponse>

    @POST(Utils.GET_CCMS_RECHARGESALE)
    fun getCcmsRechargeSale(@Body ccmsRechargeRequest: CCMSRechargeRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_BATCH_SETTLEMENT)
    fun getBatchSettlement(@Body bacthSettlementRequest: BatchSettlementRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_CARD_FEE_PAYMENT)
    fun getCardFeePayment(@Body cardFeeRequest: CardFeeRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_BALANCE_TRANSFER)
    fun getBalanceTransfer(@Body balanceTransferRequest: BalanceTransferRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_OTP)
    fun getOTP(@Body generateOtpRequest: GenerateOTPRequest): Call<GenerateOTPResponse>

    @POST(Utils.GET_REGISTRATION)
    fun getRegistration(@Body registrationRequest: RegistrationRequest): Call<RegistrationResponse>

    @POST(Utils.GET_RELOAD)
    fun getReload(@Body reloadRequest: ReloadRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_BALANCE_ENQUIRY)
    fun getBalanceEnquiry(@Body balanceEnquiryRequest: BalanceEnquiryRequest): Call<BalanceEnquiryResponse>

    @POST(Utils.GET_CCMS_BALANCE_ENQUIRY)
    fun getCCMSBalanceEnquiry(@Body ccmsBalanceRequest: CCMSBalanceRequest): Call<CCMSBalanceResponse>

    @POST(Utils.GET_UNBLOCK_CARD_PIN)
    fun getUnblockCardPin(@Body unblockCardPinRequest: UnblockCardPinRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_CHANGE_CARD_PIN)
    fun getChangeCardPin(@Body changeCardPinRequest: ChangeCardPinRequest): Call<CCMSRechargeResponse>

    @POST(Utils.GET_CONTROL_CHANGE_PIN)
    fun getChangeCardPin(@Body controlPinChange: ControlPinChange): Call<CCMSRechargeResponse>

    @POST(Utils.GET_IDFC_GET_OTP)
    fun getIdfcGetOtp(@Body idfcGetOtpRequest: IdfcGetOtpRequest): Call<IdfcGetOtpResponse>
}