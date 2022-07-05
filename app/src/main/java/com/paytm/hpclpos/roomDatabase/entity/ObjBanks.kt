package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ObjBanks {
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0
    var Value: Int = 0
    var Name: String? = null
}
