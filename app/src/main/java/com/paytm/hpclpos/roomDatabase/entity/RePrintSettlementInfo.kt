package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.PrimaryKey

class RePrintSettlementInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val hdtIndex: String? = null

    private val printerParamIns: String? = null
}