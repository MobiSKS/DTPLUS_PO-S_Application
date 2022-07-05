package com.paytm.hpclpos.posterminal.cardChipRead

import com.paytm.hpclpos.posterminal.util.BaseTester
import com.pax.dal.IIcc
import kotlin.Throws
import com.pax.dal.exceptions.IccDevException
import com.paytm.hpclpos.posterminal.cardChipRead.IccTester
import com.paytm.hpclpos.posterminal.base.DemoApp

class IccTester private constructor() : BaseTester() {
    private val icc: IIcc
    @Throws(IccDevException::class)
    fun init(slot: Byte): ByteArray? {
        var initRes: ByteArray? = null
        initRes = icc.init(slot)
        logTrue("init")
        return initRes
    }

    fun detect(slot: Byte): Boolean {
        var res = false
        return try {
            res = icc.detect(slot)
            logTrue("detect")
            res
        } catch (e: IccDevException) {
            e.printStackTrace()
            logErr("detect", e.toString())
            res
        }
    }

    fun close(slot: Byte) {
        try {
            icc.close(slot)
            logTrue("close")
        } catch (e: IccDevException) {
            e.printStackTrace()
            logErr("close", e.toString())
        }
    }

    fun autoResp(slot: Byte, autoresp: Boolean) {
        try {
            icc.autoResp(slot, autoresp)
            logTrue("autoResp")
        } catch (e: IccDevException) {
            e.printStackTrace()
            logErr("autoResp", e.toString())
        }
    }

    fun isoCommand(slot: Byte, send: ByteArray?): ByteArray {
        var resp = byteArrayOf()
        return try {
            resp = icc.isoCommand(slot, send)
            logTrue("isoCommand")
            resp
        } catch (e: IccDevException) {
            e.printStackTrace()
            logErr("isoCommand", e.toString())
            resp
        }
    }

    fun light(flag: Boolean) {
        try {
            icc.light(flag)
            logTrue("light")
        } catch (e: IccDevException) {
            e.printStackTrace()
            logErr("light", e.toString())
        }
    }

    companion object {
        private var iccTester: IccTester? = null
        @JvmStatic
        val instance: IccTester?
            get() {
                if (iccTester == null) {
                    iccTester = IccTester()
                }
                return iccTester
            }
    }

    init {
        icc = DemoApp.getDal()!!.icc
    }
}