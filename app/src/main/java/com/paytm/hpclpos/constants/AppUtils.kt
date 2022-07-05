package com.paytm.hpclpos.constants

import com.paytm.hpclpos.enums.SaleTransactionDetails

class AppUtils {

    companion object {

        fun getTitleName(saleType: String) : String {
            when(saleType){
                SaleTransactionDetails.CARDSALE.saleName -> return "SALE(CARD)"
                SaleTransactionDetails.CCMSSALE.saleName -> return "SALE(CCMS)"
                SaleTransactionDetails.DEALER_CREDIT_SALE.saleName -> return "SALE(DEALER CREDIT)"
                SaleTransactionDetails.CREDIT_TXN.saleName -> return "SALE(CREDIT TXN)"
                SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName -> return "SALE(FASTTAG)"
                SaleTransactionDetails.CCMS_CASHRECHARGE.saleName -> return "CCMS RECHARGE(CARD)"
                SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName -> return "CCMS RECHARGE(CHEQUE)"
                SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName -> return "CCMS RECHARGE(NEFT/RTGS)"
                SaleTransactionDetails.BALANCE_TRANSFER.saleName -> return "BALANCE TFR(CARD)"
                SaleTransactionDetails.BALANCE_ENQUIRY.saleName -> return "CARD BALANCE"
                SaleTransactionDetails.CCMS_RELOAD.saleName -> return SaleTransactionDetails.CCMS_RELOAD.saleName
                SaleTransactionDetails.CASH_RELOAD.saleName -> return SaleTransactionDetails.CASH_RELOAD.saleName
                SaleTransactionDetails.CHEQUE_RELOAD.saleName -> return SaleTransactionDetails.CHEQUE_RELOAD.saleName
            }
            return ""
        }
    }
}