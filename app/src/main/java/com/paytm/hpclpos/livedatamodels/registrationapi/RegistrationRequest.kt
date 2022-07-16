package com.paytm.hpclpos.livedatamodels.registrationapi

import lombok.Getter
import lombok.Setter

@Getter
@Setter
 class RegistrationRequest {
    var UserId: String? = null
    var Useragent: String? = null
    var Userip: String? = null
    var IACId: String? = null
    var TerminalId: String? = null
}