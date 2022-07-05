package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AquirerTable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    private val acquirerNum = 0

    /**
     * Acquirer table Id
     * [com.verifone.india.hpcl.data.datastore.cardTable.CardTable.acquirerId] <br></br>>
     */
    private val acquirerId = 0

    /**
     * Acquirer Program name
     */
    private val acquirerProgramName: String? = null

    /**
     * Acquirer name
     */
    private val acquirerName: String? = null

    /**
     * Carrier time out 1
     */
    private val carrierTimeout1 = 0

    /**
     * Carrier time out 2
     */
    private val carrierTimeout2 = 0

    /**
     * NII
     */
    private val NII: String? = null

    /**
     * TID
     */
    private val TID: String? = null

    /**
     * Merchant ID
     */
    private val MID: String? = null

    /**
     * Response timeout
     */
    private val timeout = 0

    /**
     * Next batch number
     */
    private val nextBatchNum: String? = null

    /**
     * Settle time
     */
    private val settleTime: String? = null

    /**
     * Settle day
     */
    private val settleDay: String? = null

    /**
     * Encrypted PIN Key
     */
    private val pinKey: ByteArray? = null

    /**
     * Encrypt PIN key index
     */
    private val pinKeyIndex = 0

    /**
     * Encrypted MAC key
     */
    private val macKey: ByteArray? = null

    /**
     * Encrypt MAC key index
     */
    private val macKeyIndex = 0

    /**
     * Visa I Terminal ID
     */
    private val visaITerminalId: String? = null

    /****************** International parameter below *********************************************/
    /****************** International parameter below  */
    /**
     * IP address
     */
    private val ip: String? = null

    /**
     * Port
     */
    private val port = 0

    /**
     * invoice
     */
    private val invoiceNum: String? = null

    /**
     * Host is logon or not
     */
    private val isLogon = false

    /**
     * force settlement
     */
    private val settlementFlag = false

}