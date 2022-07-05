package com.paytm.hpclpos.livedatamodels.generateapi

data class GenerateOtpRequest(
    var userId: String,
    var useragent: String,
    var userip: String,
    var merchantid: String,
    var terminalid: String,
    var mobileno: String,
    var oTPtype: Int,
    var createdBy: String
) 
    