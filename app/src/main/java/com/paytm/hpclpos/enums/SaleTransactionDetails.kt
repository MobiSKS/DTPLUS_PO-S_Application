package com.paytm.hpclpos.enums

import android.util.Log

enum class SaleTransactionDetails(val saleType : Int ,val saleName : String,val category: String) {
    // Sale
    CARDSALE(503,"CARD SALE","SALE"),
    CCMSSALE( 501 ,"CCMS SALE","SALE"),
    DEALER_CREDIT_SALE(504,"DEALER CREDIT SALE","SALE"),
    CREDIT_TXN(502,"CREDIT TXN","SALE"),
    FASTAG_SALE_ONLY_CARDLESS_MOBILE(505,"Fastag Sale (Only Cardless (Mobile))","SALE"),

    // Reload
    CASH_RELOAD(506,"CASH RELOAD","RELOAD"),
    CHEQUE_RELOAD(507,"CHEQUE RELOAD","RELOAD"),
    CCMS_RELOAD(508,"CCMS RELOAD","RELOAD"),

    // Void
    VOID(518,"VOID","VOID"),

    // CCMS Recharge Types
    CCMS_CASHRECHARGE(528,"CASH RECHARGE","CCMS RECHARGE"),
    CCMS_CHEQUERECHARGE(529,"CHEQUE RECHARGE","CCMS RECHARGE"),
    CCMS_NEFTRECHARGE(513,"NEFT RECHARGE","CCMS RECHARGE"),

    // BALANCE Transfer
    BALANCE_TRANSFER(515,"BALANCE TRANSFER","BALANCE TRANSFER"),

    // Card Fee Payment
    CARD_FEE_PAYMENT(534,"CARD FEE PAYMENT","CARD FEE PAYMENT"),

    //Balance Enquiry
    BALANCE_ENQUIRY(514,"BALANCE ENQUIRY","BALANCE ENQUIRY"),

    //Unblock Card Pin
    UNBLOCK_CARD_PIN(519,"UNBLOCK CARD PIN","UNBLOCK CARD PIN"),

    //Unblock Card Pin
    CHANGE_CARD_PIN(520,"CHANGE CARD PIN","CHANGE CARD PIN"),

    //Unblock Card Pin
    CONTROL_PIN_CHANGE(533,"CONTROL PIN CHANGE","CONTROL PIN CHANGE"),

    //Driver Loyalty (NON DTP)
    DRIVER_LOYAL(510,"DRIVER LOYAL","DRIVER LOYAL"),

    // Credit Sale Complete
    CREDIT_SALE_COMPLETE(522,"CREDIT SALE COMPLETE","CREDIT SALE COMPLETE"),

    // CCMS Balance
    CCMS_BALANCE(531,"CCMS BALANCE","CCMS BALANCE");

    companion object {
        fun getSaleIdByName(code: String?): Int? {
            if (code == null) {
                return null
            }
            for (e in values()) {
                if (e.saleName.equals(code) || e.saleName.contains(code)) {
                    return e.saleType
                }
            }
            return null
        }

        fun getSaleNameById(code: Int?): String? {
            if (code == null) {
                return null
            }
            for (e in values()) {
                if (e.saleType.equals(code)) {
                    return e.saleName
                }
            }
            return null
        }

        fun getTransCategoryById(code: Int?): String? {
            if (code == null) {
                return null
            }
            for (e in values()) {
                if (e.saleType.equals(code)) {
                    return e.category
                }
            }
            return null
        }

        fun getAllCategoryData(): LinkedHashSet<String> {
            val linkedHashSet = LinkedHashSet<String>()
            for (e in values()) {
                linkedHashSet.add(e.category)
            }
            Log.d("SaleTransactionDetails","getAllCategoryData ${linkedHashSet.toString()}" )
            return linkedHashSet
        }
    }
}