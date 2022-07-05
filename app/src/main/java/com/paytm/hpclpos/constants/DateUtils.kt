package com.paytm.hpclpos.constants

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {

        val dateTimeFormatForApiRequest = "yyyy-MM-dd HH:mm:ss.SSS"
        val dateTimeFormatForPrintReceipt = "dd/MM/yy HH:mm:ss"

        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateTime() :String {
            val sdf = SimpleDateFormat(dateTimeFormatForApiRequest)
            return sdf.format(Date())
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateTimeForSettlement() :String {
            val sdf = SimpleDateFormat(dateTimeFormatForPrintReceipt)
            return sdf.format(Date())
        }

        fun getFormatedDateTime(date: String) :String {
            val originalFormat: DateFormat = SimpleDateFormat(dateTimeFormatForApiRequest, Locale.ENGLISH)
            val targetFormat: DateFormat = SimpleDateFormat(dateTimeFormatForPrintReceipt)
            val date: Date = originalFormat.parse(date)
            return targetFormat.format(date)
        }
    }
}