package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SettlementReportInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    lateinit var objTranscationsForBatchSettlement: String
    lateinit var productWiseAmountSum: String
}