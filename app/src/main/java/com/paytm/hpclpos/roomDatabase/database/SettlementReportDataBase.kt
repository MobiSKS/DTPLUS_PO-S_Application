package com.paytm.hpclpos.roomDatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paytm.hpclpos.roomDatabase.dao.SettlementReportDao
import com.paytm.hpclpos.roomDatabase.entity.SettlementReportInfo

@Database(entities = [SettlementReportInfo::class], version = 1, exportSchema = false)
abstract class SettlementReportDataBase : RoomDatabase() {
    abstract fun SettlementReportDao(): SettlementReportDao
}