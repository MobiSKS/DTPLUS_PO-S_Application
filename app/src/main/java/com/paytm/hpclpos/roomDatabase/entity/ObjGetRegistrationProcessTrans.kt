package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ObjGetRegistrationProcessTrans {
    @PrimaryKey(autoGenerate = true)
    var id :Int = 0
    var TransType: Int = 0
    var TransName: String? = null
    var MaxVal: Int = 0
    var MinVal: Int = 0
}