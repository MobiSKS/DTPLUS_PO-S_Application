package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DailyBatchInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val BatchNum: String? = null
    private val time: Long = 0
    private val payAmount: Long = 0
    private val receiveAmount: Long = 0
    private val batchStatus = 0
}