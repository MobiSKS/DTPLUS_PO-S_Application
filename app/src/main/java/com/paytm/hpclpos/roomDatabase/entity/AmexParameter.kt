package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AmexParameter {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    private val NII: String? = null
    private val VendorId: String? = null
    private val LTMK_Acquirer_ID: String? = null
    private val LTID: String? = null
    private val LMID: String? = null
}