package com.paytm.hpclpos.livedatamodels.registrationapi

sealed class ApiResponse {
    data class RegistrationResponse(
        var Success: Boolean,
        var Status_Code: Int,
        val Internel_Status_Code: Int,
        var Message: String,
        val Method_Name: String,
        val Data: Data,
        val Model_State: Any
    ) : ApiResponse()

    data class Error(var error: String) : ApiResponse()
}