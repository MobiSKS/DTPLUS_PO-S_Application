package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Operators {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var operatorId: String? = null
    var password: String? = null
    var isLogon = false
    var lastLogonDate: String? = null
    var lastLogonTime: String? = null
}