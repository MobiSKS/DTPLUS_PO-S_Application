package com.paytm.hpclpos.posterminal.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun getCurrentDate(): String? {
            val formatter = SimpleDateFormat("yyMMdd")
            val currentDate = Calendar.getInstance().time
            return formatter.format(currentDate)
        }

        fun getCurrentTime(): String? {
            val formatter = SimpleDateFormat("HHmmss")
            val currentDate = Calendar.getInstance().time
            return formatter.format(currentDate)
        }
    }
}