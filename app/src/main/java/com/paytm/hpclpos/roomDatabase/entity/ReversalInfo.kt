package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.PrimaryKey

class ReversalInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val transName: String? = null

    /**
     * Field 02 account
     */
    private val pan: String? = null

    /**
     * Field 04 amount
     */
    private val amount: String? = null

    private val tip: String? = null

    private val odemeter: String? = null

    /**
     * Field 11 system trace
     */
    private val sysTrace: String? = null

    /**
     * Field 11 original system trace
     */
    private val orgSysTrace: String? = null

    /**
     * HDT INDEX
     */
    private val hdtIndex = 0

    /**
     * CDT INDEX
     */
    private val cdtIndex = 0

    /**
     * invoice hum
     */
    private val invoiceNum: String? = null

    /**
     * original invoice num
     */
    private val orgInvoiceNum: String? = null

    /**
     * Field 13 transaction date
     */
    private val orgTransDate: String? = null

    /**
     * Field 14 valid period of card
     */
    private val cardExpiryDate: String? = null

    /**
     * Field 22  CardInfo.inputMode
     */
    private val inputMode = 0

    /**
     * Field 22 Does the card input mode have input Pin
     */
    private val isInputPin = false

    /**
     * Field 23 Card serial number
     */
    private val SequenceNum: String? = null

    /**
     * Field 25 schema id
     */
    private val schemaId: String? = null

    /**
     * Field 38 auth code
     */
    private val authCode: String? = null

    /**
     * Field 39 Response Code
     */
    private val rspCode: String? = null

    /**
     * Field 49 CurrencyData 5F2A
     */
    private val CurrencyCode: String? = null

    /**
     * hpcl Field 49 CurrencyData 5F2A
     */
    private val currencyId: String? = null

    /**
     * Field 55 reversal
     */
    private val field55: String? = null

    /**
     * Field 57
     */
    private val field57: String? = null

    /**
     * Field 60 batch number
     */
    private val batchNo: String? = null

    /**
     * Field 61 org batch num
     */
    private val orgBatchNo: String? = null

    /**
     * Field 64
     */
    private val mac: String? = null

    /**
     * Terminal Verificaion Result 95
     */
    private val TVR: String? = null

    /**
     * Application Transaction Counter  9F36
     */
    private val ATC: String? = null

    /**
     * Issuer Application Data  9F10
     */

    private val IAD: String? = null

    /**
     * Interface Device Serial Number  9F1E
     */

    private val IFDNumber: String? = null

    /**
     * Whether the current transaction track is encrypted
     */
    private val isFallBackTran = false

    /**
     * Whether to downgrade FALLBACK transactions
     */
    private val isARPCError = false

    /**
     * Whether partial acceptance marks
     */
    private val isPartPurchase = false

}