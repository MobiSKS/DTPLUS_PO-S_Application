package com.paytm.hpclpos.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ObjGetRegistrationProcessMerchant {

    var MerchantId: String? = null
    @PrimaryKey
    var TerminalId: Long? = 0
    var MerchantName: String? = null
    var MerchantAddress: String? = null
    var Header1: String? = null
    var Header2: String? = null
    var Footer1: String? = null
    var Footer2: String? = null
    var BatchSaleLimit: String? = null
    var BatchReloadLimit: String? = null
    var BatchSize: String? = null
    var SettlementTime: String? = null
    var RemoteDownload: String? = null
    var URL: String? = null
    var BatchNo: String? = null
}