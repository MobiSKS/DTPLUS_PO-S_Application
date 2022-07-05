package com.paytm.hpclpos.livedatamodels.registrationapi

data class ObjGetRegistrationProcessTrans (
    var TransType: Int = 0,
    var TransName: String? = null,
    var MaxVal: Int = 0,
    var MinVal: Int = 0
)