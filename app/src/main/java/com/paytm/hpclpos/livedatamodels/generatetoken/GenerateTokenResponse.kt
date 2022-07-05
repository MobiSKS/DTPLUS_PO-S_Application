package com.paytm.hpclpos.livedatamodels.generatetoken

data class GenerateTokenResponse(
    val Message: String,
    val Method_Name: String,
    val Model_State: ModelState,
    val Status_Code: Int,
    val Success: Boolean,
    val Token: String,
    val Internel_Status_Code : Int
)