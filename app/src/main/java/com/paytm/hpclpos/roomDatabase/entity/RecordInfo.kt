package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.PrimaryKey

class RecordInfo {

    /**
     * transaction type
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val transName: String? = null

    /**
     * original transaction type
     */
    private val orgTransName: String? = null

    /**
     * field 02 account
     */
    private val pan: String? = null

    /**
     * field 03 Processing code
     */
    private val processCode: String? = null

    /**
     * field 04 amount
     * payback 交易 response 中4域代表points redeem，用于打印
     */
    private val amount: String? = null
    private val pointsRedeem: String? = null

    /**
     * TIP or cash back
     */
    private val tip: String? = null

    /**
     * Field 11 system trace
     */
    private val sysTrace: String? = null

    /**
     * Field 11 original system trace
     */
    private val orgSysTrace: String? = null

    /**
     * *  <br></br>>
     */
    private val hdtIndex = 0

    /**
     * CDT INDEX
     */
    private val cdtIndex = 0

    /**
     * CDT INDEX
     */
    private val currencyIndex = 0

    /**
     * invoice hum
     */
    private val invoiceNum: String? = null

    /**
     * invoice hum
     */
    private val orgInvoiceNum: String? = null

    /**
     * Field 12 transaction time
     */
    private val transTime: String? = null

    /**
     * Field 13 transaction date
     */
    private val transDate: String? = null

    /**
     * Field 13 original transaction date
     */
    private val orgTransDate: String? = null

    /**
     * Field 14 valid period of card
     */
    //private String cardExpiryDate;

    /**
     * Field 14 valid period of card
     */
    //private String cardExpiryDate;
    /**
     * Field 15 清算日期(format:MMSS)
     */
    private val batchSettleDate: String? = null

    /**
     * Field 23 Card serial number
     */
    private val SequenceNum: String? = null

    /**
     * Field 22  CardInfo.inputMode
     */
    private val inputMode = 0

    /**
     * Field 22 Does the card input mode have input Pin
     */
    private val isInputPin = false

    /**
     * Field 35 Track2 information
     */
    private val track2: String? = null

    /**
     * Field 36 Track3 information
     */
    private val track3: String? = null

    /**
     * Field 37 reference number
     */
    private val refNo: String? = null

    /**
     * Field 37 org reference number
     */
    private val orgRefNo: String? = null

    /**
     * Field 38 auth code
     */
    private val authCode: String? = null

    /**
     * Field 38 org auth code
     */
    private val orgAuthCode: String? = null

    /**
     * Field 39 Response Code
     */
    private val rspCode: String? = null

    /**
     * issuer
     */
    private val issuer: String? = null

    /**
     * acquirer
     */
    private val acquirer: String? = null

    /**
     * Field 55
     */
    private val field55: String? = null

    /**
     * Field 55 for TC
     */
    private val field55ForTC: String? = null

    /**
     * Field 60 batch number
     */
    private val batchNo: String? = null

    /**
     * Field 61 org batch number
     */
    private val orgBatchNo: String? = null

    /**
     * Field 63.1  card organizationName
     */
    private val cardOrgCode: String? = null

    /**
     * 63.2 Note from Host
     */
    private val hostNote: String? = null

    /**
     * IC card transaction data, cardholder information
     */
    private val cardHolderName: String? = null

    /**
     * Scan the payment voucher code
     */
    private val qrCodePayNo: String? = null

    /**
     * org scan the payment voucher code
     */
    private val ogrQrCodePayNo: String? = null

    /**
     * Batch status：Not giving（0） Has been giving（1）
     */
    private val batchUploadStatus = 0

    /**
     * Send status for offline transactions： Not giving（0） Has been giving（1）
     */
    private val offlineBatchUploadStatus = 0

    /**
     * EMV IC Card transaction online（0），Transaction approval（1），Transaction declined to（2）
     */
    private val emvTransResult = 0

    /**
     * app label tag 50
     */
    //@BoAnnotations.TLV(tag = "50")
    private val AppLabel: String? = null

    /**
     * app label tag 9F12
     */
    //@BoAnnotations.TLV(tag = "9F12")
    private val AppPrefName: String? = null

    /**
     * aid tag 84
     */
    //@BoAnnotations.TLV(tag = "84")
    private val AID: String? = null

    /**
     *
     */
    private val EmvTransType: String? = null

    /**
     * Application Cryptogram   9F26
     */
    //@BoAnnotations.TLV(tag = "9F26")
    private val ARQC: String? = null

    /**
     * Terminal Verificaion Result  95
     */
     //@BoAnnotations.TLV(tag = "95")
    private val TVR: String? = null

    /**
     * Transaction status information 9B
     */
    //@BoAnnotations.TLV(tag = "9B")
    private val TSI: String? = null

    /**
     * Application Transaction Counter  9F36
     */
    //@BoAnnotations.TLV(tag = "9F36")
    private val ATC: String? = null

    /**
     * Unpredictable Number  9F37
     */
    //@BoAnnotations.TLV(tag = "9F37")
    private val RANDOM: String? = null

    /**
     * Issuer Application Data  9F10
     */
    //@BoAnnotations.TLV(tag = "9F10")
    private val IAD: String? = null

    /**
     * Application Interchange Profile  82
     */
    //@BoAnnotations.TLV(tag = "82")
    private val AIP: String? = null

    /**
     * Cryptogram Information Data  9F27
     */
    //@BoAnnotations.TLV(tag = "9F27")
    private val CryptInfo: String? = null

    /**
     * Holder name
     */
    //@BoAnnotations.TLV(tag = "5F20")
    private val holderName: String? = null

    /**
     * Field 49 CurrencyData 5F2A
     */
    //@BoAnnotations.TLV(tag = "5F2A")
    private val CurrencyCode: String? = null

    /**
     * CountryCode 9F1A
     */
    //@BoAnnotations.TLV(tag = "9F1A")
    private val CountryCode: String? = null

    /**
     * Terminal Capabilities 9F33
     */
    //@BoAnnotations.TLV(tag = "9F33")
    private val terminalCapacity: String? = null

    /**
     * Cardholder Verfication Method Results 9F34
     */
    //@BoAnnotations.TLV(tag = "9F34")
    private val CVMResult: String? = null

    /**
     * Interface Device Serial Number  9F1E
     */
    //@BoAnnotations.TLV(tag = "9F1E")
    private val IFDNumber: String? = null

    /**
     * Terminal Application Version Number  9F09
     */
    //@BoAnnotations.TLV(tag = "9F09")
    private val AppVer: String? = null

    /**
     * Issuer Script Results  DF31
     */
    //@BoAnnotations.TLV(tag = "DF31")
    private val IssScriptResultData: String? = null

    /**
     * Key Index 8F
     */
    //@BoAnnotations.TLV(tag = "8F")
    private val PKindex: String? = null

    /**
     * EMV Response Code
     */
    private val EMVRspCode: String? = null

    /**
     * original transaction is void
     */
    private val isVoided = false

    /**
     * original transaction is loyalty
     */
    private val isLoyaltyed = false

    /**
     * transaction is print
     */
    private val isPrinted = false

    /**
     * Whether partial acceptance marks
     */
    private val isPartPurchase = false

    /**
     * Whether to downgrade FALLBACK transactions
     */
    private val isFallBack = false

    /**
     * Whether the current transaction track is encrypted
     */
    private val isTrackEncrypt = false

    /**
     * For UnionPay. Just used to print reception, Received from host in field06
     */
    private val cupRefAmount: String? = null

    /**
     * Only print, refer to ADD1 ADD2 ADD3
     */
    private val additional: String? = null

    private val tcUploadStatus = 0

    private val tipAdjusted = false

    private val cvv2: String? = null

    ///////////////////////hpcl
    private val schemaId: Int? = null
    private val currencyId: String? = null
    private val transType: Int? = null
    private val balance: String? = null
    private val odometer: String? = null
    private val otp: String? = null
    private val tokenNum: String? = null
    private val payCode: String? = null

    //card info start
    private val cardCustomerName: String? = null
    private val cardVehicleNum: String? = null
    private val cardExpiryDate //format 300622
            : String? = null
    //card info end

    //card info end
    private val controlCardNum: String? = null

    private val chequeNum: String? = null
    private val micrCode: String? = null

    private val formNum: String? = null
    private val cardsNum: String? = null

    private val UTRNum: String? = null
    private val operatorId = 0

    /**---------------------------IPTS------------------------------------------------------------ */
    private val siteId = 0

    private val transactionEndDateTime: String? = null

    private val pumpNo = 0

    private val nozzleNo = 0

    private val fCCTransactionID: String? = null

    private val product: String? = null

    private val quantity: String? = null

    private val rSP: String? = null

    private val PaymentMode: String? = null

    private val PaymentSource: String? = null

    private val TransactionRefNo: String? = null

    private val VehicleNumber: String? = null

    private val MobileNumber: String? = null

    private val tid: String? = null

    private val oldPan: String? = null

}