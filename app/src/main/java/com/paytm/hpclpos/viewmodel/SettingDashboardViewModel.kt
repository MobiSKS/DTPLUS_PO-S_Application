package com.paytm.hpclpos.viewmodel

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.activities.broadcastreceiver.MyAlarm
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.RegistartionUtils
import com.paytm.hpclpos.constants.TransactionUtils
import com.paytm.hpclpos.constants.TransactionUtils.Companion.convertErrorBody
import com.paytm.hpclpos.constants.TransactionUtils.Companion.getStringFromError
import com.paytm.hpclpos.livedatamodels.registrationapi.ApiResponse
import com.paytm.hpclpos.livedatamodels.registrationapi.Data
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationRequest
import com.paytm.hppay.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit

class SettingDashboardViewModel : ViewModel() {

    var liveDataRegistration: MutableLiveData<ApiResponse>? = null
    fun getliveRegistration(): MutableLiveData<ApiResponse> {
        return liveDataRegistration!!
    }

    fun makeApiRegistration(genearteOtpRequest: RegistrationRequest) {
        liveDataRegistration = MutableLiveData()
        Constants.MainUrlChanged.forToken = "0"
        Constants.MainUrlChanged.urlChanged = "1"
        val retrofitInstance = ApiClient.getClient
        val retroService = retrofitInstance.getRegistration(genearteOtpRequest)
        retroService.enqueue(object : Callback<ApiResponse.RegistrationResponse> {

            override fun onResponse(call: Call<ApiResponse.RegistrationResponse?>, response: Response<ApiResponse.RegistrationResponse?>) {
                if (response.isSuccessful) {
                    liveDataRegistration!!.postValue(response.body())
                } else {
                    liveDataRegistration!!.value = ApiResponse.Error(getStringFromError(convertErrorBody(response.errorBody()!!)!!,response))
                }
            }

            override fun onFailure(call: Call<ApiResponse.RegistrationResponse>, t: Throwable) {
                liveDataRegistration?.postValue(ApiResponse.Error(TransactionUtils.handleExceptions(t)))
            }
        })
    }

    fun storeRegistrationDataIntoDb(data: Data, context: Context) {
        val appRepository = AppRepository(context)
        for (obj in data.ObjGetRegistrationProcessMerchant!!) {
            appRepository.insertMerchantDetails(RegistartionUtils.getObjTypeForRegistrationProcessMerchant(obj))
        }
        for (obj in data.ObjGetRegistrationProcessTrans!!) {
            appRepository.insertTransDetails(RegistartionUtils.getObjTypeForRegistrationProcessTrans(obj))
        }
        for (obj in data.ObjBanks!!) {
            appRepository.insertObjBanks(RegistartionUtils.getObjBanks(obj))
        }
        for (obj in data.ObjFormFactors!!) {
            appRepository.insertFormFactors(RegistartionUtils.getObjFormFactors(obj))
        }
        TransactionUtils.setTerminalRegistrationStatus(context,Constants.REGISTRATION_STATUS)
    }

    fun setAlarmForSettlement(activity: Activity) {

        //We need a calendar object to get the specified time in millis
        //as the alarm manager method takes time in millis to setup the alarm
        val calendar = Calendar.getInstance()
        setAlarm(calendar.timeInMillis + TimeUnit.MINUTES.toMillis(2),activity)
    }

    private fun setAlarm(time: Long, activity: Activity) {
        //getting the alarm manager
        val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //creating a new intent specifying the broadcast receiver
        val i = Intent(activity, MyAlarm::class.java)

        //creating a pending intent using the intent
        val pendingIntent = PendingIntent.getBroadcast(activity, 0, i, 0)

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, TimeUnit.HOURS.toMillis(2), pendingIntent)
        Toast.makeText(activity, "Alarm is set", Toast.LENGTH_SHORT).show()
    }
}