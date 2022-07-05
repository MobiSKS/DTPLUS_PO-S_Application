package com.paytm.hpclpos.roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction
import com.paytm.hpclpos.roomDatabase.entity.SumAveragePojo

@Dao
interface TransactionDao {
    @Query("SELECT * FROM HpclTransaction")
    fun getAll(): List<HpclTransaction?>?

    @Query("SELECT * FROM HpclTransaction WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<HpclTransaction?>?

    @Query("SELECT * FROM HpclTransaction WHERE   uid = (SELECT MAX(uid)  FROM HpclTransaction)")
    fun lastTransaction(): HpclTransaction?

    @Insert
    fun insertAll(users: HpclTransaction)

    @Query("DELETE FROM HpclTransaction")
    fun deleteAll()

    @Query("SELECT * FROM HPCLTRANSACTION WHERE TransactionType = :transType AND batch_Id = :batchNum")
    fun getActiveTransactions(transType: String, batchNum: Int) : List<HpclTransaction?>?

    @Query("SELECT * FROM HPCLTRANSACTION WHERE transaction_Id = :transaction_Id")
    fun getTransactionBasedOnId(transaction_Id: Int): HpclTransaction

    @Query("SELECT * FROM HPCLTRANSACTION WHERE product = :product AND batch_Id = :batchNum")
    fun getTransactionsFromProductId(product: String, batchNum: Int) : List<HpclTransaction?>?

    @Query("SELECT * FROM HPCLTRANSACTION WHERE batch_Id = :batchNum")
    fun getTransactionsForBatchNUmber(batchNum: Int) : List<HpclTransaction?>

    @Query("SELECT SUM(transaction_Amount) as total, AVG(transaction_Amount) as average" +
            " FROM HpclTransaction WHERE Category = :category AND batch_Id = :batchNum")
    fun getCountAndSumForTransactions(category: String, batchNum: Int): SumAveragePojo
}