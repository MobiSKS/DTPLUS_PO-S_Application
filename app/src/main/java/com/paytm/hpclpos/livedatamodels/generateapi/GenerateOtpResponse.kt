package com.paytm.hpclpos.livedatamodels.generateapi

import com.paytm.hpclpos.livedatamodels.ccmssale.Data

data class GenerateOtpResponse(
    val success : Boolean,
    val statusCode : Int,
    val internelStatusCode : Int,
    val message : String,
    val methodName : String,
    val data : ArrayList<Data>,
    val modelState :Any
)