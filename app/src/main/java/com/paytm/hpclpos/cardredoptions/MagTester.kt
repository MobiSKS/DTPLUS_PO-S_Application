package com.paytm.hpclpos.cardredoptions

import android.util.Log
import com.paytm.hpclpos.posterminal.util.BaseTester
import com.pax.dal.IMag
import com.pax.dal.entity.TrackData
import com.pax.dal.exceptions.MagDevException
import kotlin.Throws
import com.paytm.hpclpos.posterminal.base.DemoApp

class MagTester private constructor() : BaseTester() {
    private val iMag: IMag
    fun open() {
        try {
            iMag.open()
            logTrue("open")
        } catch (e: MagDevException) {
            Log.d("Error", e.message!!)
            logErr("open", e.toString())
        }
    }

    fun close() {
        try {
            iMag.close()
            logTrue("close")
        } catch (e: MagDevException) {
            logErr("Error", e.message)
            logErr("close", e.toString())
        }
    }

    // Reset magnetic stripe card reader, and clear buffer of magnetic stripe card.
    fun reset() {
        try {
            iMag.reset()
            logTrue("reSet")
        } catch (e: MagDevException) {
            logErr("Error", e.message)
            logErr("reSet", e.toString())
        }
    }

    // Check whether a card is swiped
    val isSwiped: Boolean
        get() {
            var b = false
            try {
                b = iMag.isSwiped
            } catch (e: MagDevException) {
                logErr("Error", e.message)
                logErr("isSwiped", e.toString())
            }
            return b
        }

    @Throws(MagDevException::class)
    fun read(): TrackData {
        val trackData = iMag.read()
        logTrue("read")
        return trackData
    }

    companion object {
        private var magTester: MagTester? = null
        val instance: MagTester?
            get() {
                if (magTester == null) {
                    magTester = MagTester()
                }
                return magTester
            }
    }

    init {
        iMag = DemoApp.getDal()!!.mag
    }
}