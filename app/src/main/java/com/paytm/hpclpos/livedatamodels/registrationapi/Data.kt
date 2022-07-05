package com.paytm.hpclpos.livedatamodels.registrationapi

data class Data(
    var ObjGetRegistrationProcessMerchant: ArrayList<ObjGetRegistrationProcessMerchant>? = null,
    var ObjGetRegistrationProcessTrans: ArrayList<ObjGetRegistrationProcessTrans>? = null,
    var ObjBanks: ArrayList<ObjBanks>? = null,
    var ObjFormFactors: ArrayList<ObjFormFactors>? = null
)