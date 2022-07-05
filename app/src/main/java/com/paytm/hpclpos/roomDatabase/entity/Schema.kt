package com.example.apphpcldb.entity.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schema(
        val schemaId: Int? = null,
        val status: Int? = null,
        val order: Int? = null,
        val schemaName: String? = null,
        val transType: Int? = null,
        val implementationDate: String? = null,
        val expiryDate: String? = null,
        val allowedCurrencyIDs: String? = null,
        val allowedServiceIDs: String? = null,
        val perTransMinValue: Int? = null,
        val perTransMaxValue: Int? = null,
        val perTransIncrements: Int? = null,
        val offlineMaxValue: Int? = null,
        val numOfFollowUpTxn: Int? = null,
        val txnType: Int? = null,
        val currencyType: Int? = null,
        val unknown: String? = null, ) {
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
}