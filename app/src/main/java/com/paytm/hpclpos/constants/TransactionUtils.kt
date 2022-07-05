package com.paytm.hpclpos.constants

import android.content.Context

class TransactionUtils {
    companion object {
        private val PREFS = "cposprefs"
        private val BATCH_FORMAT = "%03d"

        fun incrementBatch(context: Context, batchName: String?): String {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            var batch = pref.getInt(batchName, 1)
            batch++
            if (batch == 999) {
                batch = 1
            }
            val editor = pref.edit()
            editor.putInt(batchName, batch)
            editor.apply()
            return String.format(BATCH_FORMAT, batch)
        }

        fun getCurrentBatch(context: Context, batchName: String?): String {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val currentBatchNumber = pref.getInt(batchName, 1)
            return String.format(BATCH_FORMAT, currentBatchNumber)
        }

        fun setBatchNumber(context: Context,batchName: String?,batchNumber: String) {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val editor = pref.edit()
            editor.putInt(batchName, batchNumber.toInt())
            editor.apply()
        }

        fun isTerminalRegistered(context: Context, prefName: String): Boolean {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val currentBatchNumber = pref.getBoolean(prefName, false)
            return currentBatchNumber
        }

        fun setTerminalRegistrationStatus(context: Context, prefName: String) {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val editor = pref.edit()
            editor.putBoolean(prefName, true)
            editor.apply()
        }

        fun setTerminalPin(context: Context, terminalPin: String,prefName: String) {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            val editor = pref.edit()
            editor.putString(prefName, terminalPin)
            editor.apply()
        }

        fun getTerminalPin(context: Context,prefName: String): String {
            val pref = context.getSharedPreferences(PREFS, 0) // 0 for private
            return pref.getString(prefName, "")!!
        }
    }
}