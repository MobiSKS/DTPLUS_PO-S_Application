package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.PrimaryKey

class ReprintSettlementRecordInfo {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val transType = 0

    private val orgTransName: String? = null

    private val currencyId: String? = null

    private val isVoided = false

    private val amount: String? = null

    private val schemaId = 0
}