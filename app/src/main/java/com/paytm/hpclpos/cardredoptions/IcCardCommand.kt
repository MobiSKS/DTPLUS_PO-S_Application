package com.paytm.hpclpos.cardredoptions

import android.app.Activity
import android.util.Log
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.posterminal.cardChipRead.IccTester
import com.paytm.hpclpos.posterminal.util.HexaUtils

class IcCardCommand(val activity: Activity,val cardEventListener: CardEventListener) {

    val appletIdCmd = "00A404000A504159464C4558503553"
    val mfCommand = "00A40000023F00"
    val dfCommand = "00A40000027F02"
    val userCardProfileCommand = "00A40000020020"
    val verifyPinCommand = "0020001008"
    val changePinCommand = "0024001010"
    val appendFF = "FFFFFFFF"
    val readCardProfile = "00B00000A2"

    fun initCard() : Boolean {
        val ZERO = 0
        IccTester.instance?.light(true)
        if (IccTester.instance?.detect(0.toByte())!!) {
            val res = IccTester.instance!!.init(0.toByte())
            if (res == null) {
                ToastMessages.customMsgToast(activity,"Init ic card,but no response")
                Log.i("Test", "init ic card,but no response")
                return false
            }
            IccTester.instance!!.autoResp( ZERO.toByte(), true)
            val iccTester = (HexaUtils.byte2HexStr(IccTester.instance?.isoCommand(0.toByte(),HexaUtils.hexStr2Bytes(appletIdCmd))))
            if (iccTester.substring(iccTester.length - 4).equals("9000")) {
                //ToastMessages.customMsgToast(activity, "Sucess")
                val mfResult = setMF()
                if (mfResult.substring(mfResult.length - 4).equals("9000")) {
                    val dfResult = setDF()
                    if (dfResult.substring(dfResult.length - 4).equals("9000")) {
                        val userCardProfileResult = setPathUserCardProfile()
                        if (userCardProfileResult.substring(userCardProfileResult.length - 4).equals("9000")) {
                            val readCardProfile = readCardProfile()
                            if(readCardProfile.substring(readCardProfile.length - 4).equals("9000")) {
                               return true
                            }
                        }
                    }
                }
            } else {
                ToastMessages.customMsgToast(activity, "Failure ${iccTester}")
            }
        } else {
            IccTester.instance?.light(false)
            cardEventListener.onCardEvent(CardEventState(CardEventState.CHIP_CARD_NOT_DETECTED))
        }
        return false
    }

    fun setMF() : String {
        return HexaUtils.byte2HexStr(IccTester.instance?.isoCommand(0.toByte(),HexaUtils.hexStr2Bytes(mfCommand)))
    }

    fun setDF() : String {
        return HexaUtils.byte2HexStr(IccTester.instance?.isoCommand(0.toByte(),HexaUtils.hexStr2Bytes(dfCommand)))
    }

    fun setPathUserCardProfile(): String {
        return HexaUtils.byte2HexStr(IccTester.instance?.isoCommand(0.toByte(),HexaUtils.hexStr2Bytes(userCardProfileCommand)))
    }

    fun verifyPinCommand(pinData: String): String {
        val verifyPinCommandByte = HexaUtils.hexStr2Bytes(verifyPinCommand)
        val pindataByte = HexaUtils.hexStr2Bytes(HexaUtils.stringToHexDecimal(pinData))
        val FFByte = HexaUtils.hexStr2Bytes(appendFF)
        return HexaUtils.byte2HexStr(IccTester.instance?.isoCommand
            (0.toByte(), HexaUtils.concatByteArray(verifyPinCommandByte, pindataByte, FFByte)))
    }

    fun readCardProfile(): String {
        return HexaUtils.byte2HexStr(IccTester.instance?.isoCommand(0.toByte(),HexaUtils.hexStr2Bytes(readCardProfile)))
    }

    fun  verifyPinData(pinData: String) {
        if (initCard()) {
            val verifyPinResult = verifyPinCommand(pinData)
            if (verifyPinResult.substring(verifyPinResult.length - 4).equals("9000")) {
                IccTester.instance?.light(false)
                cardEventListener.onCardReadSuccess()
            } else {
                IccTester.instance?.light(false)
                cardEventListener.onCardEvent(CardEventState(CardEventState.INCORRECT_PIN))
            }
        }
    }

    fun changeCardPin(oldPin: String,newPin: String) {
        if (initCard()) {
            val verifyPinResult = verifyPinCommand(oldPin)
            if (verifyPinResult.substring(verifyPinResult.length - 4).equals("9000")) {
                 val changePinResult = changePinCommand(oldPin,newPin)
                 if(changePinResult.substring(changePinResult.length - 4).equals("9000")) {
                     IccTester.instance?.light(false)
                     cardEventListener.onCardReadSuccess()
                 } else {
                     IccTester.instance?.light(false)
                     cardEventListener.onCardEvent(CardEventState(CardEventState.INCORRECT_PIN))
                 }
            } else {
                IccTester.instance?.light(false)
                cardEventListener.onCardEvent(CardEventState(CardEventState.CHANGE_CARDPIN_FAILED))
            }
        }
    }

    fun changePinCommand(oldPin: String,newPin: String): String {
        val FFByte = HexaUtils.hexStr2Bytes(appendFF)
        val chnagePinCommandByte = HexaUtils.hexStr2Bytes(changePinCommand)
        val oldPindataByte = HexaUtils.concatByteArray(HexaUtils.hexStr2Bytes(HexaUtils.stringToHexDecimal(oldPin)),FFByte)
        val newPinDataByte = HexaUtils.concatByteArray(HexaUtils.hexStr2Bytes(HexaUtils.stringToHexDecimal(newPin)),FFByte)
        val oldnewPinDataByteArray = HexaUtils.concatByteArray(oldPindataByte,newPinDataByte)
        return HexaUtils.byte2HexStr(IccTester.instance?.isoCommand
            (0.toByte(), HexaUtils.concatByteArray(chnagePinCommandByte, oldnewPinDataByteArray)))
    }
}