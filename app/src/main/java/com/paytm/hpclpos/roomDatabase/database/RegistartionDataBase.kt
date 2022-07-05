package com.paytm.hpclpos.roomDatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paytm.hpclpos.roomDatabase.dao.RegistrationDao
import com.paytm.hpclpos.roomDatabase.entity.ObjBanks
import com.paytm.hpclpos.roomDatabase.entity.ObjFormFactors
import com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessMerchant
import com.paytm.hpclpos.roomDatabase.entity.ObjGetRegistrationProcessTrans

@Database(
    entities = [ObjGetRegistrationProcessMerchant::class, ObjGetRegistrationProcessTrans::class,
        ObjBanks::class, ObjFormFactors::class],
    version = 1,
    exportSchema = false
)
abstract class RegistartionDataBase : RoomDatabase() {
    abstract fun registartionDao(): RegistrationDao
}
