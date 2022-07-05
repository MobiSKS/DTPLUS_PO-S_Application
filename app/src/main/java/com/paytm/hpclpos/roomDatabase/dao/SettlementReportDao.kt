package com.paytm.hpclpos.roomDatabase.dao

import androidx.room.*
import com.paytm.hpclpos.roomDatabase.entity.SettlementReportInfo

@Dao
interface SettlementReportDao {

    @Insert
    fun insertSettlementReportInfo(users: SettlementReportInfo)
}