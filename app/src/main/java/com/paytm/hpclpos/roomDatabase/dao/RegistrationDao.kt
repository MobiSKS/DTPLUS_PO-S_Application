package com.paytm.hpclpos.roomDatabase.dao

import androidx.room.*
import com.paytm.hpclpos.roomDatabase.entity.ObjBanks
import com.paytm.hpclpos.roomDatabase.entity.ObjFormFactors
import com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessMerchant
import com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessTrans

@Dao
interface RegistrationDao {

    @Insert
    fun insertObjGetRegistrationProcessMerchant(objGetRegistrationProcessMerchant: ObjGetRegistrationProcessMerchant)

    @Insert
    fun insertObjGetRegistrationProcessTrans(objGetRegistrationProcessTrans: ObjGetRegistrationProcessTrans)

    @Delete
    fun deleteObjGetRegistrationProcessMerchant(objGetRegistrationProcessMerchant: ObjGetRegistrationProcessMerchant)

    @Delete
    fun deleteObjGetRegistrationProcessTrans(objGetRegistrationProcessTrans: ObjGetRegistrationProcessTrans)

    @Insert
    fun insertObjBanks(objBanks: ObjBanks)

    @Insert
    fun insertFormFactors(objFormFactors: ObjFormFactors)

    @Query("SELECT * FROM ObjGetRegistrationProcessMerchant")
    fun fetchMerchantDetails() : ObjGetRegistrationProcessMerchant
}