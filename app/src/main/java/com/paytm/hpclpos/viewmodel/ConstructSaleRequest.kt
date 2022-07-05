package com.paytm.hpclpos.viewmodel

import android.content.Context
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.enums.ProductDetails
import com.paytm.hpclpos.enums.SaleTransactionDetails
import com.paytm.hpclpos.livedatamodels.BalanceEnquiryResponse.BalanceEnquiryRequest
import com.paytm.hpclpos.livedatamodels.CCMSBalance.CCMSBalanceRequest
import com.paytm.hpclpos.livedatamodels.ChangeCardPinRequest.ChangeCardPinRequest
import com.paytm.hpclpos.livedatamodels.ControlPinChange.ControlPinChange
import com.paytm.hpclpos.livedatamodels.Reload.ReloadRequest
import com.paytm.hpclpos.livedatamodels.UnblockCardPin.UnblockCardPinRequest
import com.paytm.hpclpos.livedatamodels.balancetransfer.BalanceTransferRequest
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeRequest
import com.paytm.hpclpos.livedatamodels.ccmssale.CCMSSaleRequest
import com.paytm.hpclpos.livedatamodels.idfcGetOtp.IdfcGetOtpRequest

class ConstructSaleRequest (var context: Context,var batchId:Int,var odometerReading:String) {

    val date = DateUtils.getCurrentDateTime()
    fun constructSaleRequest() : CCMSSaleRequest {
        val ccmsSaleRequest = CCMSSaleRequest(Constants.UserId,Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context),
            GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),"",batchId,GlobalMethods.getAmountViewModel()!!.amount.toFloat()/100,
            SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString(),
            GlobalMethods.getTransactionId(context)!!.toInt().toString(), date, "",
            ProductDetails.getProductIdByName(GlobalMethods.getProductType())!!.toInt(),
            "","","",1,3,"123456","","")

        if (GlobalMethods.getCardInfoEntity() != null && (GlobalMethods.getCardInfoEntity()!!.cardType.equals(
                Constants.ICC) || GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.MAG_STRIPE))) {
            ccmsSaleRequest.cardNo = GlobalMethods.getCardInfoEntity()!!.cardNo
            val formfactor = if (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC)) { 1 } else { 2 }
            ccmsSaleRequest.formFactor = formfactor
            ccmsSaleRequest.pin = GlobalMethods.getPinData()
            ccmsSaleRequest.odometerReading = odometerReading
        } else {
            ccmsSaleRequest.mobileNo = GlobalMethods.getMobileNo()
            ccmsSaleRequest.otp = "123456"
            ccmsSaleRequest.odometerReading = odometerReading
        }
        if(GlobalMethods.getSaleType()!!.equals(SaleTransactionDetails.DEALER_CREDIT_SALE.saleName)) {
            ccmsSaleRequest.dcsTokenNumber = GlobalMethods.getTokenNumber()
        }

        if(GlobalMethods.getSaleType()!!.equals(SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName)) {
            ccmsSaleRequest.vehicleNo = GlobalMethods.getVehilceNo()
        }
        return ccmsSaleRequest
    }

    fun constructCCMSRechargeRequest() : CCMSRechargeRequest {
        val ccmsRechargeRequest = CCMSRechargeRequest(Constants.UserId,Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context),
            GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),"",batchId,GlobalMethods.getAmountViewModel()!!.amount.toFloat()/100,
            SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString(),
            GlobalMethods.getTransactionId(context)!!.toInt(),date,"",
           "", "","1","","","",1,3,"123456","")
        if (GlobalMethods.getSaleType().equals(SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName)) {
            ccmsRechargeRequest.chequeNo = GlobalMethods.getChequeNumber() as String
            ccmsRechargeRequest.micr = GlobalMethods.getMicrCode() as String
        } else if(GlobalMethods.getSaleType().equals(SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName)){
            ccmsRechargeRequest.mUtrNo = GlobalMethods.getUTRNumber()!!
        }
        if(GlobalMethods.getCardInfoEntity() != null && GlobalMethods.getCardInfoEntity()!!.isControlCardNumber) {
            ccmsRechargeRequest.ccn = GlobalMethods.getCardInfoEntity()!!.controlCardNumber!!
            ccmsRechargeRequest.formFactor = 4
            ccmsRechargeRequest.pin = GlobalMethods.getPinData()!!
        } else if(GlobalMethods.getCardInfoEntity() != null && (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC) ||
            GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.MAG_STRIPE))) {
            ccmsRechargeRequest.cardNo = GlobalMethods.getCardInfoEntity()!!.cardNo!!
            val formfactor = if (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC)) { 1 } else { 2 }
            ccmsRechargeRequest.formFactor = formfactor
            ccmsRechargeRequest.pin = GlobalMethods.getPinData()!!
        } else {
            ccmsRechargeRequest.mobileNo = GlobalMethods.getMobileNo()!!
            ccmsRechargeRequest.otp = "123456"
        }
        return ccmsRechargeRequest
    }

    fun constructBalanceTransferRequest() : BalanceTransferRequest{
    val balanceTransferRequest = BalanceTransferRequest(Constants.UserId,Constants.ANDROIDAGENT,
        GlobalMethods.getDeviceId(context),
        GlobalMethods.TestMerchant_Id.toString(),
        GlobalMethods.getTerminalId(context).toString(),"",batchId,GlobalMethods.getAmountViewModel()!!.amount.toFloat()/100,
                SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString(),
        GlobalMethods.getTransactionId(context)!!.toInt().toString(),date,"","","",
        1,3,"123456","")
        if(GlobalMethods.getCardInfoEntity() != null && (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC) ||
            GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.MAG_STRIPE))) {
            balanceTransferRequest.cardNo = GlobalMethods.getCardInfoEntity()!!.cardNo
            val formfactor = if (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC)) { 1 } else { 2 }
            balanceTransferRequest.formFactor = formfactor
            balanceTransferRequest.pin = GlobalMethods.getPinData()
        } else {
            balanceTransferRequest.mobileNo = GlobalMethods.getMobileNo()
            balanceTransferRequest.otp = "123456"
        }
        return balanceTransferRequest
    }

    fun constructReloadRequest() : ReloadRequest {
        val reloadRequest = ReloadRequest(Constants.UserId,Constants.ANDROIDAGENT,
                GlobalMethods.getDeviceId(context),
                GlobalMethods.TestMerchant_Id.toString(),
                GlobalMethods.getTerminalId(context).toString(),"",batchId,GlobalMethods.getAmountViewModel()!!.amount.toFloat()/100,
                SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString(),
                GlobalMethods.getTransactionId(context)!!.toInt().toString(),date,"",
                "", "","","",1,3,"123456")
        if (GlobalMethods.getSaleType().equals(SaleTransactionDetails.CHEQUE_RELOAD.saleName)) {
            reloadRequest.chequeNo = GlobalMethods.getChequeNumber() as String
            reloadRequest.micr = GlobalMethods.getMicrCode() as String
        }
        if(GlobalMethods.getCardInfoEntity() != null && (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC) ||
                        GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.MAG_STRIPE))) {
            reloadRequest.cardNo = GlobalMethods.getCardInfoEntity()!!.cardNo
            val formfactor = if (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC)) { 1 } else { 2 }
            reloadRequest.formFactor = formfactor
            reloadRequest.pin = GlobalMethods.getPinData()
        } else {
            reloadRequest.mobileNo = GlobalMethods.getMobileNo()
            reloadRequest.otp = "123456"
        }
        return reloadRequest
    }

    fun constructBalanceEnquiryRequest() : BalanceEnquiryRequest {
         val balanceEnquiryRequest = BalanceEnquiryRequest(Constants.UserId,Constants.ANDROIDAGENT,
                 GlobalMethods.getDeviceId(context),
                 GlobalMethods.TestMerchant_Id.toString(),
                 GlobalMethods.getTerminalId(context).toString(),"","","","",
                 1,3,"123456")
        if(GlobalMethods.getCardInfoEntity() != null && (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC) ||
                        GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.MAG_STRIPE))) {
            balanceEnquiryRequest.cardNo = GlobalMethods.getCardInfoEntity()!!.cardNo
            val formfactor = if (GlobalMethods.getCardInfoEntity()!!.cardType.equals(Constants.ICC)) { 1 } else { 2 }
            balanceEnquiryRequest.formFactor = formfactor
            balanceEnquiryRequest.pin = GlobalMethods.getPinData()
        } else {
            balanceEnquiryRequest.mobileNo = GlobalMethods.getMobileNo()
            balanceEnquiryRequest.otp = "123456"
        }
        return balanceEnquiryRequest
    }

    fun constructCCMSBalanceRequest() : CCMSBalanceRequest {
        val ccmsBalanceRequest = CCMSBalanceRequest(Constants.UserId,Constants.ANDROIDAGENT,
                GlobalMethods.getDeviceId(context), GlobalMethods.TestMerchant_Id.toString(),
                GlobalMethods.getTerminalId(context).toString(),GlobalMethods.getControlCardNumber()
                ,GlobalMethods.getPinData(), 1,3,"123456")
        return ccmsBalanceRequest
    }

    fun constructUnblockCardPinRequest(pinNew: String) : UnblockCardPinRequest {
        val unblockCardPinRequest = UnblockCardPinRequest(Constants.UserId,Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context), GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString()
            ,GlobalMethods.getCardInfoEntity()!!.cardNo, pinNew,1,3,"123456")
        return unblockCardPinRequest
    }

    fun constructChangeCardPinRequest(pinNew: String,pinOld: String) : ChangeCardPinRequest {
        val changeCardPinRequest = ChangeCardPinRequest(Constants.UserId,Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context), GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString()
            ,GlobalMethods.getCardInfoEntity()!!.cardNo, pinNew,pinOld,1,3,"123456")
        return changeCardPinRequest
    }

    fun constructControlPinChangeRequest(pinNew: String,pinOld: String) : ControlPinChange {
        val controlPinChange = ControlPinChange(Constants.UserId,Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context), GlobalMethods.TestMerchant_Id.toString(),
            GlobalMethods.getTerminalId(context).toString(),SaleTransactionDetails.getSaleIdByName(GlobalMethods.getSaleType()).toString()
            ,GlobalMethods.getControlCardNumber(),pinOld, pinNew,1,3,"123456")
        return controlPinChange
    }

    fun constructIDFCgetOtp() : IdfcGetOtpRequest {
        return IdfcGetOtpRequest(Constants.UserId,Constants.ANDROIDAGENT,
            GlobalMethods.getDeviceId(context),GlobalMethods.getMobileNo(),GlobalMethods.getCardInfoEntity()!!.vehicleNumber,GlobalMethods.getAmountViewModel()!!.amount.toFloat()/100)
    }
}