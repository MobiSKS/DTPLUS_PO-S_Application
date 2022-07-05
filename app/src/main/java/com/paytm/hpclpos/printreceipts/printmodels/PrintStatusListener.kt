package com.paytm.hpclpos.printreceipts.printmodels

interface PrintStatusListener {
    fun onSuccess()

    fun onError(error: Int)
}