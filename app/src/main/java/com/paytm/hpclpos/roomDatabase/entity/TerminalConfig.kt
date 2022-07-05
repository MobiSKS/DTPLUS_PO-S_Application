package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.PrimaryKey

class TerminalConfig {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val terminalPassword: String? = null

    /**
     * 连续输错terminal pin 的次数，三次将锁定
     */
    private val enterTerminalAttempts = 0

    /**
     * terminal pin 状态
     */
    private val terminalPinStatus = 0

    /**
     * clear batch password
     */
    private val clearBatchPassword: String? = null

    /**
     * manual password
     */
    private val manualPassword: String? = null

    /**
     * admin password
     */
    private val adminPassword: String? = null

    /**
     * default approval code for printing(offline txns)
     */
    private val defaultApprCode: String? = null

    /**
     * System trace field 11
     */
    private val sysTrace: String? = null

    private val batchNum: String? = null

    /**
     * Time start accept txn
     */
    private val startTime: String? = null

    /**
     * Time Stop accept txn
     */
    private val stopTime: String? = null

    /**
     * auto settlement time<br></br>>
     * [.autoSettlementFlag]
     */
    private val autoSettlementTime: String? = null

    private val lastSettlementTime: String? = null

    private val timeToNextTime: String? = null

    private val lastExitAppTime: String? = null

    /**
     * Max Sale Transaction Limit per txn
     */
    private val maxSaleLimit: String? = null

    /**
     * Max Offline Transaction Limit per txn
     */
    private val maxOfflineLimit: String? = null

    /**
     * Max Refund Transaction Limit per Txn.
     */
    private val maxRefundLimit: String? = null

    /**
     * Max Cash Transaction Limit per Txn.
     */
    private val maxCashLimit
            : String? = null

    /**
     * Additional Prompt Label 1<br></br>>
     */
    private val ADD1: String? = null

    /**
     * Additional Prompt Label 2<br></br>>
     */
    private val ADD2: String? = null

    /**
     * Additional Prompt Label 3<br></br>>
     */
    private val ADD3: String? = null

    /**
     * TLE slot, Master Key seed index
     */
    private val TDKIndex = 0

    /**
     * Connection timeout for host response. Unit: second
     */
    private val connTimeOutSecond: Int? = null

    /**
     * apn
     */
    private val apn: String? = null

    /**
     * apn user Name
     */
    private val userName: String? = null

    /**
     * password
     */
    private val password: String? = null

    /**
     * add prompt min char
     */
    private val addPromptMinLen = 0

    /**
     * TIP Capping % to CFG to configure max tip %
     */
    private val tipCapping = 0

    /**
     * timeout value to print
     */
    private val printTimeOutSecond = 0

    /**
     * Count off offline to store
     */
    private val offlineMaxCount = 0

    /**
     * Number of retry for EMV before fallback
     */
    private val fallbackNumber = 0

    /**
     * Cashback in multiples of value, For MCCS only
     */
    private val cashbackMultiples: Long = 0

    /**
     * ZIPOPEN param in CFG from a flag 0/1 to an amount value of 8 digit. 0 value means open zip without min limit, value 99999999=> RM9,999,999.99
     */
    private val zipOpen
            : Long = 0

    /**
     * fallback control
     */
    private val fallback = false

    /**
     * True: OPT IN;  False: OPT OUT  MyDebit Priority selection enable/disable MyDebit
     */
    private val myDebitPriority = false

    /**
     * TIP Support
     */
    private val tipSupport = false

    /**
     * auto settlement control<br></br>>
     * [.autoSettlementTime]
     */
    private val autoSettlementFlag = false

    /**
     * force settlement control  Daily FORCE SETTLEMENT
     */
    private val forceSettlementFlag = false

    private val settlementProcess = false

    /**
     * max daily batch limit control<br></br>>
     * [com.verifone.india.hpcl.data.datastore]
     */
    private val maxTotalControl = false

    /**
     * Allow pin bypass emv
     */
    private val pinBypass = false

    /**
     * UPI Ref Amount print
     */
    private val cupRefAmtSupport = false

    /**
     * retry message for Debit Timeout
     */
    private val retrySupport = false

    private val operatorTimeout = 0

    private val appVersion: String? = null

    private val terminalId: String? = null
    private val vhqId: String? = null
    private val merchantId: String? = null
    private val ip: String? = null
    private val ip2: String? = null
    private val port: Int? = null
    private val port2: Int? = null
    private val nii: String? = null
    private val changeNumber: String? = null
    private val keyExchangeTmk: String? = null

    //print
    private val head1: String? = null
    private val head2: String? = null
    private val footer1: String? = null
    private val footer2: String? = null
    private val SALE_ENABLED: String? = null
    private val UTLTY_ENABLED: String? = null
    private val FLEET_ENABLED: String? = null
    private val ETAILING_ENABLED: String? = null
    private val RELOAD_ENABLED: String? = null
    private val LTYAWARD_ENABLED: String? = null
    private val LTYREDEEM_ENABLED: String? = null
    private val UNBLOCKUSERPIN_ENABLED: String? = null
    private val merchantCardNameToPrint: String? = null
    private val MerchantCity: String? = null

    //After registration, terminal should not allow to change TID.
    private val hasRegistration = false
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    //-----------hpcl 批次状态-------------------
    private val batchStatus = 0
    private val lastbatchStatus = 0
    private val lastBatchTime: Long = 0
    //--------------------------------------------

    //--------------------------------------------
    //-----------hpcl ITPS switch-------------------
    private val supportItpsMode = false
    private val supportMultiNetwork = false
    //--------------------------------------------

    //--------------------------------------------
    //-----------hpcl data/wifi switch-------------------
    private val wifiMode = false
    private val cellularMode = false
    //--------------------------------------------

    //--------------------------------------------
    private val batchSize: String? = null
    private val batchSaleLimit: String? = null
    private val batchReloadLimit: String? = null

    /** Display Parameter */
    private val settlementPwdEnabled: String? = null
    private val complaintEnabled: String? = null
    private val dormancyDays: String? = null
    private val fleetReloadLimit: String? = null
    private val deHotlistEnabled: String? = null
    private val unblockSamPinEnabled: String? = null
    private val fccReloadEnabled: String? = null
    private val paramChangeNo: String? = null

}