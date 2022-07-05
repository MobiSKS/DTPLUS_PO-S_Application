package com.paytm.hpclpos.cardredoptions

import lombok.Getter
import lombok.Setter
import java.io.Serializable

@Setter
@Getter
class CardInfoEntity : Serializable {
    var cardNo: String? = null
     var cardType : String? = null
     var tk1: String? = null
     var tk2: String? = null
     var tk3: String? = null
     var expiredDate: String? = null
     var serviceCode: String? = null
     var iccIssuerId: String? = null
     var cardHolderName: String? = null
     var vehicleNumber: String? = null
     var iccDob : String? = null
     var iccPurseMax : String? = null
     var iccPurseMin: String? = null
     var icctransMax: String? = null
     var icctransMin: String? = null
     var iccMonthLimit: String? = null
     var iccmonthSpend: String? = null
     var icccardActivationDate: String? = null
     var controlCardNumber: String? = null
     var isControlCardNumber: Boolean = false
     var isMobileTransaction: Boolean = false
     var mobileNumber : String? = null
}