package com.paytm.hpclpos.livedatamodels.validatepin

data class ValidatePinResponse(
    val `data`: List<Data>,
    val message: String,
    val method_Name: String,
    val model_State: Any,
    val status_Code: Int,
    val success: Boolean
)