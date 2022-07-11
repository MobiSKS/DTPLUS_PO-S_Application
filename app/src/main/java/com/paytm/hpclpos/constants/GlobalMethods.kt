package com.paytm.hpclpos.constants

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.paytm.hpclpos.cardredoptions.CardInfoEntity
import com.paytm.hpclpos.ui.amount.AmountViewModel
import lombok.Getter
import lombok.Setter


class GlobalMethods {

    companion object {
        private var datas: HashMap<String, Any?>? = null
        private lateinit var sharedPreferencesData: SharedPreferencesData

        fun initAndClear() {
            if (datas == null) {
                datas = HashMap()
            } else {
                datas!!.clear()
            }
        }

        var sp: SharedPreferences? = null
        var editor: SharedPreferences.Editor? = null
        var context: Context? = null
        private val VAL = "000001"

        fun getTransactionId(context: Context): String? {
            Log.d("Global Methods", "Get Transaction Id Called")
            initSharedPreference(context)
            val s = Constants.getTrace(context)
            val s1 = strNumAuto(s!!)
            setTrace(context, s1)
            Log.d("Global Methods","Invoice Id "+s)
            return numToStr6(s)
        }

        // For development purpose
        fun displayTransId(context: Context) : String{
            initSharedPreference(context)
            return Constants.getTrace(context)!!
        }

        fun decrementTransactionIdByOne(context: Context, invoiceNum: String) {
            Log.d("Global Methods", "Decrements Transaction Id By one")
            initSharedPreference(context)
            val s1 = strNumAutoDecrement(invoiceNum)
            setTrace(context, s1)
        }

        fun strNumAuto(s: String): String? {
            var i = s.toInt()
            i++
            val s1 = i.toString()
            return numToStr6(s1)
        }

        fun strNumAutoDecrement(s: String): String? {
            return numToStr6(s)
        }

        fun numToStr6(numString: String): String? {
            var numString = numString
            if (TextUtils.isEmpty(numString)) {
                numString = VAL
            }
            if (numString.length > 6) {
                numString = VAL
            } else if (numString.length < 6) {
                val str = StringBuilder()
                for (i in 0 until 6 - numString.length) {
                    str.append("0")
                }
                numString = str.append(numString).toString().trim { it <= ' ' }
            }
            return numString
        }

        fun setTrace(context: Context?, msg: String?) {
            val s: String? = numToStr6(msg!!)
            Constants.setTrace(context, s)
        }

        fun initSharedPreference(context: Context) {
            this.context = context
            this.sp =
                (context.getApplicationContext()
                    .getSharedPreferences("hpclData", Context.MODE_PRIVATE))
            this.editor = sp!!.edit()
        }

        fun putString(key: String?, value: String?) {
            editor!!.putString(key, value).apply()
        }

        fun getString(key: String?): String? {
            return getString(key, null)
        }

        fun getString(key: String?, defaultValue: String?): String? {
            return sp!!.getString(key, defaultValue)
        }

        fun setIsLoginOperator(context: Context, status: Boolean) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val statusLocked = prefs.edit().putBoolean(Constants.LOGIN, status).apply()
        }

        fun getIsLoginOperator(context: Context): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(Constants.LOGIN, false);
        }

        private val CHEQUE_NUMBER = "CHEQUE_NUMBER"
        private val MICR_CODE = "MICR_CODE"
        private val CONTROL_CARD_NUMBER = "CONTROL_CARD_NUMBER"
        private val TRANSACTION_TYPE = "TRANSACTION_TYPE"
        private val MOBILE_NUMBER_FOR_TRANSACTION = "MOBILE_NUMBER_FOR_TRANSACTION"
        private val VEHICLE_NUMBER = "VEHICLE NUMBER"
        private val PRODUCT_TYPE = "PRODUCT_TYPE"
        private val SALE_TYPE = "SALE_TYPE"
        private val AMOUNT = "AMOUNT"
        private val AMOUNT_VIEW_MODEL = "AMOUNT_VIEW_MODEL"
        private val CARD_INFO_ENTITY = "CARD_INFO_ENTITY"
        private val PIN_DATA = "PIN_DATA"
        private val UTR_NUMBER = "UTR_NUMBER"
        private val TOKEN_NUMBER = "TOKEN_NUMBER"
        private val OTP_NUMBER = "OTP_NUMBER"
        private val CARD_NUM = "CARD_NUM"
        private val MICR_NUMBER = "MICR_NUMBER"

        var chequeNumber = ""

        var micrCode = ""

        private var controlCardNumber = ""


        var TransactionID = "101202"

        var transactionType: String = ""
        var mobileNoForTrans: String = ""
        var productTypeP: String = ""
        var saleTypeP: String = ""
        var amountP: String = ""
        var amountViewModel: AmountViewModel? = null
        var cardInfoEntity: CardInfoEntity? = null
        var pinData: String? = null
        var utrNumber: String? = null

        var cardNom: String = ""

        var status = true

        var micrNo: String = ""
        var chequeNo: String = ""

        fun getDeviceId(context: Context): String {
            var androidId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
            );
            Log.e("GLOBALMETHODS", "DEVICEID=" + androidId)
            return androidId
        }

        var TestMerchant_Id: Long = 3050020222


        var TestOutLetId = 3089798987

        //var TestTID: Long = 5064726187
        var TestCARDNO = 7000210078041570
        var TestTerminalPin = 1234
        var TestRoleId = 2
        var TestUserType = 1

        fun setTerminalId(terminalId: String,context: Context) {
            val sharedPref = SharedPreferencesData(context)
            sharedPref.setSharedPreferenceData(Constants.TOKENIDPREF,Constants.TERMINAL_ID , terminalId)
        }

        fun getTerminalId(context: Context) : Long {
            val sharedPref = SharedPreferencesData(context)
            return sharedPref.getSharedPreferenceData(Constants.TOKENIDPREF,Constants.TERMINAL_ID)!!.toLong()
        }

        fun setServerIp(serverIp: String,context: Context) {
            val sharedPref = SharedPreferencesData(context)
            sharedPref.setSharedPreferenceData(Constants.TOKENIDPREF,Constants.SERVER_IP , serverIp)
        }

        fun getServerIp(context: Context) : String {
            val sharedPref = SharedPreferencesData(context)
            return sharedPref.getSharedPreferenceData(Constants.TOKENIDPREF,Constants.SERVER_IP)!!
        }

        fun setSecondServerIp(serverIp: String,context: Context) {
            val sharedPref = SharedPreferencesData(context)
            sharedPref.setSharedPreferenceData(Constants.TOKENIDPREF,Constants.SECOND_SERVER_IP , serverIp)
        }

        fun getSeocndServerIp(context: Context) : String {
            val sharedPref = SharedPreferencesData(context)
            return sharedPref.getSharedPreferenceData(Constants.TOKENIDPREF,Constants.SECOND_SERVER_IP)!!
        }

        fun setMobileNo(mobileNo: String) {
            datas!!.put(MOBILE_NUMBER_FOR_TRANSACTION, mobileNo)
        }

        fun getMobileNo(): String? {
            return datas!!.get(MOBILE_NUMBER_FOR_TRANSACTION) as? String
        }

        fun setVehilceNo(mobileNo: String) {
            datas!!.put(VEHICLE_NUMBER, mobileNo)
        }

        fun getVehilceNo(): String? {
            return datas!!.get(VEHICLE_NUMBER) as? String
        }

        fun setProductType(productType: String) {
            datas!!.put(PRODUCT_TYPE, productType)
        }

        fun getProductType(): String? {
            return datas!!.get(PRODUCT_TYPE) as? String
        }

        fun setSaleType(saletype: String) {
            datas!!.put(SALE_TYPE, saletype)
        }

        fun getSaleType(): String? {
            return datas!!.get(SALE_TYPE) as? String
        }

        fun setAmount(amount: String) {
            datas!!.put(AMOUNT, amount)
        }

        fun getAmount(): String? {
            return datas!!.get(AMOUNT) as? String
        }

        fun setCardNo(cardNo: String) {
            datas!!.put(CARD_NUM, cardNo)
        }

        fun getCardNo(): String? {
            return datas!!.get(CARD_NUM) as? String
        }

        fun setTransType(transTypeData: String) {
            datas!!.put(TRANSACTION_TYPE, transTypeData)
        }

        fun getTransType(): String? {
            return datas!!.get(TRANSACTION_TYPE) as? String
        }

        fun setMicrNo1(micr: String) {
            datas!!.put(MICR_NUMBER, micr)
        }

        fun getMicrNo1(): String {
            return datas!!.get(MICR_NUMBER) as String
        }

        fun setChequeNo1(chq: String) {
            datas!!.put(CHEQUE_NUMBER, chq)
        }

        fun getChequeNo1(): String {
            return datas!!.get(CHEQUE_NUMBER) as String
        }

        fun setMagStripOrChipCardType(boolean: Boolean) {
            status = boolean
        }

        fun getMagStripOrChipCardType(): Boolean {
            return status
        }

        @JvmName("setChequeNumber1")
        fun setChequeNumber(chequeNumberData: String) {
            datas!!.put(CHEQUE_NUMBER, chequeNumberData)
        }

        @JvmName("getChequeNumber1")
        fun getChequeNumber(): Any? {
            return datas!!.get(CHEQUE_NUMBER)
        }

        @JvmName("setMicrCode1")
        fun setMicrCode(micrCodeData: String) {
            datas!!.put(MICR_CODE, micrCodeData)
        }

        @JvmName("getMicrCode1")
        fun getMicrCode(): Any? {
            return datas!!.get(MICR_CODE)
        }

        @JvmName("setControlCardNumber1")
        fun setControlCardNumber(controlCardNumberData: String) {
            datas!!.put(CONTROL_CARD_NUMBER, controlCardNumberData)
        }

        @JvmName("getControlCardNumber1")
        fun getControlCardNumber(): String? {
            return datas!!.get(CONTROL_CARD_NUMBER) as? String
        }

        @JvmName("setAmountViewModel1")
        fun setAmountViewModel(amountViewModel: AmountViewModel) {
            datas!!.put(AMOUNT_VIEW_MODEL, amountViewModel)
        }

        @JvmName("getAmountViewModel1")
        fun getAmountViewModel(): AmountViewModel? {
            return datas!!.get(AMOUNT_VIEW_MODEL) as? AmountViewModel
        }

        @JvmName("setCardInfoEntity1")
        fun setCardInfoEntity(cardInfoEntity: CardInfoEntity?) {
            datas!!.put(CARD_INFO_ENTITY, cardInfoEntity)
        }

        @JvmName("getCardInfoEntity1")
        fun getCardInfoEntity(): CardInfoEntity? {
            return datas!!.get(CARD_INFO_ENTITY) as? CardInfoEntity
        }

        @JvmName("setPinData1")
        fun setPinData(pinData: String) {
            datas!!.put(PIN_DATA, pinData)
        }

        @JvmName("getPinData1")
        fun getPinData(): String? {
            return datas!!.get(PIN_DATA) as? String
        }

        fun setUTRNumber(utrNumber: String) {
            datas!!.put(UTR_NUMBER, utrNumber)
        }

        fun getUTRNumber(): String? {
            return datas!!.get(UTR_NUMBER) as? String
        }

        fun setTokenNumber(tokenNumber: String) {
            datas!!.put(TOKEN_NUMBER, tokenNumber)
        }

        fun getTokenNumber(): String? {
            return datas!!.get(TOKEN_NUMBER) as? String
        }

        fun setOtpNumber(otpNumber: String) {
            datas!!.put(OTP_NUMBER, otpNumber)
        }

        fun getOtpNumber(): String? {
            return datas!!.get(OTP_NUMBER) as? String
        }
    }
}