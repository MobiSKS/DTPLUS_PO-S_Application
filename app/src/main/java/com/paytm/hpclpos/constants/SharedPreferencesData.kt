package com.paytm.hpclpos.constants

import android.content.Context
import android.content.SharedPreferences

/**
 * This class is used for store data in Shared Preferences i.e small local storage for whole application
 *
 * @author Arif Ali
 */
class SharedPreferencesData
/**
 * This is the constructor of this class used to initilize context and create object.
 * @param context - It is an application context
 */(private val context: Context?) {
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    /**
     * This is a method of this class for create New SharedPreference
     * @param sharedPreferenceName - This is the name of SharedPreference
     */
    fun createNewSharedPreferences(sharedPreferenceName: String?) {
        sharedPreferences = context!!.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
    }

    /**
     * This is a method to Insert value inside created SharedPreference
     * @param sharedPreferenceName - This is the name of SharedPreference
     * @param fieldName - This is the key name of SharedPreference
     * @param data - This is the data for save in SharedPreference
     */
    fun setSharedPreferenceData(sharedPreferenceName: String?, fieldName: String?, data: String?) {
        sharedPreferences = context!!.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.putString(fieldName, data)
        editor!!.commit()
    }

    /**
     * This method is used for Getting the value of stored data in SharedPreference
     * @param sharedPreferenceName
     * @param fieldName
     * @return
     */
    fun getSharedPreferenceData(sharedPreferenceName: String?, fieldName: String?): String? {
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
            return sharedPreferences!!.getString(fieldName, "")
        }
        return ""
    }

    /**
     * This is used for Remove or Clear SharedPreference All data stored
     * @param sharedPreferenceName
     */
    fun clearSharedPreferenceData(sharedPreferenceName: String?) {
        sharedPreferences = context!!.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.clear()
        editor!!.apply()
    }

    /**
     * This method is used for clear single field data of SharedPreference
     * @param sharedPreferenceName
     * @param fieldName
     */
    fun clearSingleFieldSharedData(sharedPreferenceName: String?, fieldName: String?) {
        sharedPreferences = context!!.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.remove(fieldName)
        editor!!.apply()
    }
}