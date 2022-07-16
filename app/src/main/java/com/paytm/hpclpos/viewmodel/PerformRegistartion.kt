package com.paytm.hpclpos.viewmodel

import android.content.Context
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationRequest

class PerformRegistartion(val context: Context) {

    fun constructRegistrationRequest() : RegistrationRequest{
        val registartion = RegistrationRequest()
        registartion.UserId = Constants.UserId
        registartion.Useragent = Constants.ANDROIDAGENT
        registartion.Userip = GlobalMethods.getDeviceId(context)
        registartion.IACId = "123456"
        registartion.TerminalId = GlobalMethods.getTerminalId(context).toString()
        return registartion
    }
}