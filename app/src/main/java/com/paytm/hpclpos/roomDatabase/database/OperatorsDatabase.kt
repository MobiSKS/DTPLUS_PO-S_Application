package com.paytm.hpclpos.roomDatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paytm.hpclpos.roomDatabase.dao.OperatorsDao
import com.paytm.hpclpos.roomDatabase.entity.Operators

@Database(entities = [Operators::class], exportSchema = false, version = 1)
abstract class OperatorsDatabase : RoomDatabase() {
    abstract fun operatorsDao(): OperatorsDao
}
