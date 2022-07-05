package com.paytm.hpclpos.roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paytm.hpclpos.roomDatabase.entity.Operators

@Dao
interface OperatorsDao {

    @Insert
    fun insertOperatorInfo(operators: Operators)

    @Query("SELECT * FROM Operators")
    fun getAllOperators(): List<Operators?>?

    @Query("SELECT * FROM Operators WHERE operatorId = :operatorId AND password =:password")
    fun getOperator(operatorId: String,password: String): Operators

    @Query("DELETE FROM Operators WHERE operatorId = :operatorId")
    fun deleteOperator(operatorId: String)

    @Query("UPDATE Operators SET isLogon =:isLogin WHERE operatorId =:operatorId")
    fun updateOperator(isLogin: Boolean, operatorId: String)

    @Query("UPDATE Operators SET isLogon=:isLogin,lastLogonDate= :loginDate,lastLogonTime= :loginTime WHERE operatorId =:operatorId")
    fun updateOperatorLoginDateTime(
        isLogin: Boolean,
        operatorId: String,
        loginDate: String,
        loginTime: String
    )

    @Query("SELECT * FROM Operators WHERE isLogon =:isLogin")
    fun getLgoinOperator(isLogin: Boolean): Operators
}
