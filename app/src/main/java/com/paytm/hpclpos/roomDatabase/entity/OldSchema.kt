package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class OldSchema {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val schemaId: Int? = null
    private val status: Int? = null
    private val order: Int? = null
    private val schemaName: String? = null
    private val transType: Int? = null
    private val implementationDate: String? = null
    private val expiryDate: String? = null
    private val allowedCurrencyIDs: String? = null
    private val allowedServiceIDs: String? = null

    private val perTransMinValue: Int? = null
    private val perTransMaxValue: Int? = null
    private val perTransIncrements: Int? = null

    private val offlineMaxValue: Int? = null

    private val numOfFollowUpTxn: Int? = null
    private val txnType: Int? = null
    private val currencyType: Int? = null

    private val unknown: String? = null

}