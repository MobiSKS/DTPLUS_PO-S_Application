package com.example.apphpcldb.entity.repository

import android.content.Context
import androidx.room.Room
import com.example.apphpcldb.entity.database.SchemaDatabase
import com.example.apphpcldb.entity.entity.Schema
import com.paytm.hpclpos.roomDatabase.database.OperatorsDatabase
import com.paytm.hpclpos.roomDatabase.database.RegistartionDataBase
import com.paytm.hpclpos.roomDatabase.database.SettlementReportDataBase
import com.paytm.hpclpos.roomDatabase.database.TransactionDataBase
import com.paytm.hpclpos.roomDatabase.entity.*

class AppRepository(context: Context) {

    private var schemaDatabase: SchemaDatabase
    private var transactionDataBase: TransactionDataBase
    private var registrationDatabase: RegistartionDataBase
    private var settlementReportDataBase: SettlementReportDataBase
    private var operatorsDatabase: OperatorsDatabase
    private val schema = "schema_db"
    private val transaction = "trans_db"
    private val registration = "registration"
    private val settlementReportInfo = "settlementReportInfo"
    private val operators = "operators"

    init {
        schemaDatabase = Room.databaseBuilder(context.applicationContext,
                SchemaDatabase::class.java, schema).allowMainThreadQueries().build()
        transactionDataBase = Room.databaseBuilder(context.applicationContext,
                TransactionDataBase::class.java, transaction).allowMainThreadQueries().build()
        registrationDatabase = Room.databaseBuilder(context.applicationContext,
                RegistartionDataBase::class.java,registration).allowMainThreadQueries().build()
        settlementReportDataBase = Room.databaseBuilder(context.applicationContext,
            SettlementReportDataBase::class.java,settlementReportInfo).allowMainThreadQueries().build()
        operatorsDatabase = Room.databaseBuilder(context.applicationContext,
            OperatorsDatabase::class.java,operators).allowMainThreadQueries().build()
    }

    // Insert new user
    fun insertUser(schema: Schema) {
        schemaDatabase.schemaDao().insert(schema)
    }

    // update user
    fun updateUser(schema: Schema) {
        schemaDatabase.schemaDao().update(schema)
    }

    // Delete user
    fun deleteUser(schema: Schema) {
        schemaDatabase.schemaDao().delete(schema)
    }

    fun getAllRecordsFromSchema(): List<Schema> {
        return schemaDatabase.schemaDao().getAllFromSchema()
    }

    fun getAllTransaction(): List<HpclTransaction?>? {
        return transactionDataBase.transactionDao().getAll()
    }

    fun getAllTransactionById(userIds: IntArray?): List<HpclTransaction?>? {
        return transactionDataBase.transactionDao().loadAllByIds(userIds)
    }

    fun getLastTransaction(): HpclTransaction? {
        return transactionDataBase.transactionDao().lastTransaction()
    }

    fun insertTransaction(users: HpclTransaction?) {
        transactionDataBase.transactionDao().insertAll(users!!)
    }

    fun deleteAllTransaction() {
        transactionDataBase.transactionDao().deleteAll()
    }

    fun getActiveTransactions(transType: String, batchNum: Int) : List<HpclTransaction?>? {
        return transactionDataBase.transactionDao().getActiveTransactions(transType, batchNum)
    }

    fun getTransactionsForBatchNUmber(batchNum: Int) : List<HpclTransaction?> {
        return transactionDataBase.transactionDao().getTransactionsForBatchNUmber(batchNum)
    }

    fun insertMerchantDetails(objGetRegistrationProcessMerchant :ObjGetRegistrationProcessMerchant) {
        registrationDatabase.registartionDao().insertObjGetRegistrationProcessMerchant(objGetRegistrationProcessMerchant)
    }

    fun insertTransDetails(objGetRegistrationProcessTrans: ObjGetRegistrationProcessTrans) {
        registrationDatabase.registartionDao().insertObjGetRegistrationProcessTrans(objGetRegistrationProcessTrans)
    }

    fun insertObjBanks(objBanks: ObjBanks) {
        registrationDatabase.registartionDao().insertObjBanks(objBanks)
    }

    fun insertFormFactors(objFormFactors: ObjFormFactors) {
        registrationDatabase.registartionDao().insertFormFactors(objFormFactors)
    }

    fun getMerchantDetails() : ObjGetRegistrationProcessMerchant {
        return registrationDatabase.registartionDao().fetchMerchantDetails()
    }

    fun getTransactionBasedOnId(invoiceId: Int) : HpclTransaction {
        return transactionDataBase.transactionDao().getTransactionBasedOnId(invoiceId)
    }

    fun getTransactionsFromProductId(product: String, batchNum: Int) : List<HpclTransaction?>? {
        return transactionDataBase.transactionDao().getTransactionsFromProductId(product, batchNum)
    }

    fun insertSettlementReportData(settlementReportInfo: SettlementReportInfo) {
        settlementReportDataBase.SettlementReportDao().insertSettlementReportInfo(settlementReportInfo)
    }

    fun getSumOfTrans(category: String, batchNum: Int) : SumAveragePojo {
        return transactionDataBase.transactionDao().getCountAndSumForTransactions(category, batchNum)
    }

    fun getAllOperators() : List<Operators?>? {
        return operatorsDatabase.operatorsDao().getAllOperators()
    }

    fun insertOperator(operators: Operators) {
        operatorsDatabase.operatorsDao().insertOperatorInfo(operators)
    }

    fun getOperator(operatorId: String, password: String) : Operators {
        return operatorsDatabase.operatorsDao().getOperator(operatorId, password)
    }

    fun deleteOperator(operatorId: String) {
        operatorsDatabase.operatorsDao().deleteOperator(operatorId)
    }

    fun updateOperator(isLogin: Boolean, operatorId: String) {
        operatorsDatabase.operatorsDao().updateOperator(isLogin, operatorId)
    }

    fun updateOperatorLoginDateTime(isLogin: Boolean, operatorId: String,loginDate: String,loginTime: String) {
        operatorsDatabase.operatorsDao().updateOperatorLoginDateTime(isLogin, operatorId,loginDate,loginTime)
    }

    fun getLgoinOperator(isLogin: Boolean) : Operators {
        return operatorsDatabase.operatorsDao().getLgoinOperator(isLogin)
    }
}
