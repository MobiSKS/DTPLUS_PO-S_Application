package com.paytm.hppay.api

import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.CCMSBalance.CCMSBalanceRequest
import com.paytm.hpclpos.livedatamodels.ChangeCardPinRequest.ChangeCardPinRequest
import com.paytm.hpclpos.livedatamodels.ControlPinChange.ControlPinChange
import com.paytm.hpclpos.livedatamodels.Reload.ReloadRequest
import com.paytm.hpclpos.livedatamodels.UnblockCardPin.UnblockCardPinRequest
import com.paytm.hpclpos.livedatamodels.balancetransfer.BalanceTransferRequest
import com.paytm.hpclpos.livedatamodels.batchsettlement.BatchSettlementRequest
import com.paytm.hpclpos.livedatamodels.cardfee.CardFeeRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeRequest
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.livedatamodels.generateotp.GenerateOTPRequest
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenRequest
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpRequest
import com.paytm.hpclpos.livedatamodels.registrationapi.ApiResponse
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    @POST(Utils.GET_GENERATE_TOKEN)
    fun getGenerateToken(@Body generateTokenRequest: GenerateTokenRequest): Call<com.paytm.hpclpos.livedatamodels.generatetoken.ApiResponse.GenerateTokenResponse>

    @POST(Utils.GET_CCMS_SALE)
    fun getCcmsSale(@Body ccmsSaleRequest: CCMSSaleRequest): Call<com.paytm.hpclpos.livedatamodels.ccmssale.ApiResponse.CCMSSaleResponse>

    @POST(Utils.GET_CCMS_RECHARGESALE)
    fun getCcmsRechargeSale(@Body ccmsRechargeRequest: CCMSRechargeRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_BATCH_SETTLEMENT)
    fun getBatchSettlement(@Body bacthSettlementRequest: BatchSettlementRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_CARD_FEE_PAYMENT)
    fun getCardFeePayment(@Body cardFeeRequest: CardFeeRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_BALANCE_TRANSFER)
    fun getBalanceTransfer(@Body balanceTransferRequest: BalanceTransferRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_OTP)
    fun getOTP(@Body generateOtpRequest: GenerateOTPRequest): Call<com.paytm.hpclpos.livedatamodels.generateotp.ApiResponse.GenerateOTPResponse>

    @POST(Utils.GET_REGISTRATION)
    fun getRegistration(@Body registrationRequest: RegistrationRequest): Call<ApiResponse.RegistrationResponse>

    @POST(Utils.GET_RELOAD)
    fun getReload(@Body reloadRequest: ReloadRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_BALANCE_ENQUIRY)
    fun getBalanceEnquiry(@Body balanceEnquiryRequest: BalanceEnquiryRequest): Call<com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.ApiResponse.BalanceEnquiryResponse>

    @POST(Utils.GET_CCMS_BALANCE_ENQUIRY)
    fun getCCMSBalanceEnquiry(@Body ccmsBalanceRequest: CCMSBalanceRequest): Call<com.paytm.hpclpos.livedatamodels.CCMSBalance.ApiResponse.CCMSBalanceResponse>

    @POST(Utils.GET_UNBLOCK_CARD_PIN)
    fun getUnblockCardPin(@Body unblockCardPinRequest: UnblockCardPinRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_CHANGE_CARD_PIN)
    fun getChangeCardPin(@Body changeCardPinRequest: ChangeCardPinRequest): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_CONTROL_CHANGE_PIN)
    fun getChangeCardPin(@Body controlPinChange: ControlPinChange): Call<com.paytm.hpclpos.livedatamodels.ccmsrecharge.ApiResponse.CCMSRechargeResponse>

    @POST(Utils.GET_IDFC_GET_OTP)
    fun getIdfcGetOtp(@Body idfcGetOtpRequest: IdfcGetOtpRequest): Call<com.paytm.hpclpos.livedatamodels.idfcGetOtp.ApiResponse.IdfcGetOtpResponse>
}