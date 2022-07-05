package com.paytm.hpclpos.roomDatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paytm.hpclpos.roomDatabase.dao.TransactionDao
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

@Database(entities = [HpclTransaction::class], version = 2, exportSchema = false)
abstract class TransactionDataBase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}