package com.paytm.hpclpos.ui.OperatorOptions

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.roomDatabase.entity.Operators

class OperatorOptionsViewModel: ViewModel() {

    private val getOperators = MutableLiveData<List<Operators?>?>()

    fun getAllOperatorsList(context: Context?): MutableLiveData<List<Operators?>?> {
        val appRepository = AppRepository(context!!)
        val operators = appRepository.getAllOperators()
        getOperators.postValue(operators)
        return getOperators
    }
}