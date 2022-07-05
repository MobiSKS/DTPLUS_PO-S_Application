package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.PrimaryKey

class ServiceMaster {

    @PrimaryKey(autoGenerate = true)
    private val id: Int = 0
    private val serviceId: String? = null
    private val status: Int? = null
    private val serviceName: String? = null
    private val userInputNameBit1: String? = null
    private val userInputDataTypeBit1: Int? = null
    private val userInputNameBit2: String? = null
    private val userInputDataTypeBit2: Int? = null

    private val unknown: String? = null
}