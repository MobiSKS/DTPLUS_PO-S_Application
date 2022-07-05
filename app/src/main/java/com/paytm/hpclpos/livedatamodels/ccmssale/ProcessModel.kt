package com.paytm.hpclpos.livedatamodels.ccmssale

import com.paytm.hpclpos.cardredoptions.CardInfoEntity
import lombok.Getter
import lombok.Setter
import java.io.Serializable

@Getter
@Setter
class ProcessModel() : Serializable {
    var cardInfoEntity: CardInfoEntity? = null
    val Invoiceamount: Int = 0
    val Transtype: String? = null
    val Mobileno: String? = null
    val Odometerreading: String? = null
    val otp: String? = null
    val pin: String? = null
}