package com.paytm.hpclpos.constants

import android.content.Context
import com.paytm.hpclpos.enums.SaleTransactionDetails
import java.util.*

interface Constants {
    object TokenClass {
        @JvmField
        var TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IiIsIm5iZiI6MTYyOTI4MTg5MiwiZXhwIjoxNjI5MjgzNjkyLCJpYXQiOjE2MjkyODE4OTJ9.G5tsWEYKYkm5OsJ-Stuj4YE625sqyYUuCroAWDpX4T4"
    }

    companion object {

        fun getTrace(context: Context?): String? {
            return GlobalMethods.getString("trace", "000001")
        }

        fun setTrace(context: Context?, msg: String?) {
            GlobalMethods.putString("trace", msg)
        }

        const val VERIFY_PIN = "verifypin"
        const val CHANGE_PIN = "changepin"
        const val CHANGE_TERMINAL_PIN = "changeterminalpin"
        const val UNBLOCK_TERMINAL_PIN = "unblockterminalpin"

        const val NAV_VALUE = "navValue"
        const val NAV_VALUE1 = "navValue1"
        const val ICC = "ICC"
        const val MAG_STRIPE = "MAGSTRIPE"
        const val BY_MOBILE_NUMBER = "BYMOBILENUMBER"
        const val SOURCE_ID = 1
        const val UserId = "string"
        const val LIMITEXCEED = "limitexceed"
        const val DATABASENAME = "TransactionDetails"
        const val MOBILENO = "mobileNumber"
        const val BASE_URL = "http://180.179.222.161/"
        const val MYPREF = "mypreference"

        const val SELECTRANSACTIONTYPE = "selectrasactiontype"
        const val SELECTPROD = "selectedProdName"
        const val LOYALTYDTPNONDTP = "loyaltyDtpNondtp"
        const val CARDEDNONCARDEDPREF = "cardednoncardedpref"
        const val ENTERAMOUNT = "enteramount"
        const val CONTROLCARDNO = "controlcardno"
        const val REDEEMPOINT = "redeempoint"
        const val FORMNUMBER = "formnumber"
        const val NUMBEROFCARDS = "numberofcards"
        const val CARDAMOUNTS = "cardamount"
        const val TOKENIDPREF = "tokenidpref"
        const val LOGINPREF = "loginpref"
        const val TOKENID = "tokenid"
        const val ANDROIDAGENT = "Terminal"

        const val PREFCONFIG = "prefconfigfile"
        const val BATCHID = "batch_id"
        const val CCMSSALE = "ccmssale"
        const val REGISTRATION_STATUS = "registration_status"
        const val TERMINAL_PIN = "terminal_pin"

        const val TRANSID = "TransId"
        const val TRANSDATE = "TransDate"
        const val TRANSAMOUNT = "TransAmount"
        const val TRANSPRODUCT = "TransProduct"
        const val MERCHANT_NAME = "merchantName"
        const val VEHICLE_NO = "vehicleNo"
        const val CARD_EXP_DATE = "cardExpDate"
        const val BALANCE = "balance"
        const val RSP = "rsp"
        const val VOLUME = "volume"


        const val STATUS_SUCCESS = 1000
        const val STATUS_TOKEN_EXPIRED = 1006
        const val STATUS_FALSE = 200
        const val STATUS_RECORD_NOT_FOUND = 1001
        const val BATCH = "BATCH"
        const val TERMINAL_ID = "TERMINAL ID"
        const val SERVER_IP = "SERVER IP"
        const val SECOND_SERVER_IP = "SECOND SERVER IP"

        //
        const val SALE = "sale"
        const val ALL = "all"
        const val RECHARGE = "recharge"
        const val SIGNUP = "signup"
        const val LOGIN = "login"
        const val LOGIN_SCREEN = "loginscreen"

        fun getAllTransactionsList() :ArrayList<String> {
            val allTrans: ArrayList<String> = ArrayList()
            allTrans.add(SaleTransactionDetails.CARDSALE.saleName)
            allTrans.add(SaleTransactionDetails.CCMSSALE.saleName)
           // allTrans.add(SaleTransactionDetails.DEALER_CREDIT_SALE.saleName)
            allTrans.add(SaleTransactionDetails.CREDIT_TXN.saleName)
            allTrans.add(SaleTransactionDetails.CCMS_CASHRECHARGE.saleName)
            allTrans.add(SaleTransactionDetails.CCMS_CHEQUERECHARGE.saleName)
            allTrans.add(SaleTransactionDetails.CCMS_NEFTRECHARGE.saleName)
            allTrans.add(SaleTransactionDetails.BALANCE_TRANSFER.saleName)
            return allTrans
        }

        fun getAllSaleTransactions() :ArrayList<String> {
            val allTrans: ArrayList<String> = ArrayList()
            allTrans.add(SaleTransactionDetails.CARDSALE.saleName)
            allTrans.add(SaleTransactionDetails.CCMSSALE.saleName)
            allTrans.add(SaleTransactionDetails.CREDIT_TXN.saleName)
            allTrans.add(SaleTransactionDetails.FASTAG_SALE_ONLY_CARDLESS_MOBILE.saleName)
            return allTrans
        }

        fun getAllProductsList() : List<String> {
            return mutableListOf("HSD","TURBOJET","MS","POWER","LUBE","AUTO LPG","CNG")
        }

        fun getAllSaleList() : List<String> {
            return mutableListOf("CCMS SALE","CREDIT TXN","CARD SALE","DEALER CREDIT SALE","Fastag Sale (Only Cardless (Mobile))")
        }
    }

    object MainUrlChanged {
        @JvmStatic
        var urlChanged = "0"
        @JvmStatic
        var forToken = "0"
        @JvmStatic
        var tokenID = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IiIsIm5iZiI6MTYzMjM3MTY5OSwiZXhwIjoxNjMyMzczNDk5LCJpYXQiOjE2MzIzNzE2OTl9.OChOQf5x8mkEuiWKmQ7G2qHCUf-hdIrbQsh6Zkf6leE"
        //...
    }
}