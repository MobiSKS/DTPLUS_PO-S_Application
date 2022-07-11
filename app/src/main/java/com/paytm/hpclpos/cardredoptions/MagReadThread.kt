package com.paytm.hpclpos.cardredoptions

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.pax.dal.exceptions.MagDevException
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.posterminal.util.HexaUtils

class MagReadThread(con: Context,cardEventList: CardEventListener,cardSuccessList: CardSuccessListener) : Thread() {
    val context = con
    val cardEventListener = cardEventList
    val cardSuccessListener = cardSuccessList
    override fun run() {
        super.run()
        while (!interrupted()) {
            var resStr = ""
            val cardInfoEntity = CardInfoEntity()
            if (MagTester.instance!!.isSwiped) {
                try {
                    val trackData = MagTester.instance!!.read()
                    if (trackData != null) {
                        if (trackData.resultCode == 0) {
                            resStr = context.resources.getString(R.string.mag_card_error)
                            Log.e("MagReadThread", "Error=" + resStr)
                            continue
                        }
                        if (trackData.resultCode and 0x01 == 0x01) {
                            //resStr += resources.getString(R.string.mag_track1_data) + trackData.track1
                            resStr += trackData.track1
                            cardInfoEntity.tk1 = resStr
                            Log.e("MagReadThread", "Error1=" + resStr)

                        }
                        if (trackData.resultCode and 0x02 == 0x02) {
                            val track2 = context.resources.getString(R.string.mag_track2_data) + trackData.track2
                            cardInfoEntity.tk2 = trackData.track2.trim()
                            Log.e("MagReadThread", "Error2=" + trackData.track2.trim())
                        }
                        if (trackData.resultCode and 0x04 == 0x04) {
                            resStr += context.resources.getString(R.string.mag_track3_data) + trackData.track3
                            cardInfoEntity.tk3 = resStr
                            Log.e("MagReadThread", "Error3=" + resStr)
                        }
                        cardInfoEntity.cardNo = cardInfoEntity.tk2!!.substring(0,cardInfoEntity.tk2!!.indexOf("=")).trim()
                        val str = resStr.substring(resStr.length - 4, resStr.length)
                        val expirtDate = str.substring(0,2)+"/"+str.substring(2)
                        cardInfoEntity.expiredDate = expirtDate
                        cardInfoEntity.cardHolderName =
                            HexaUtils.getCardNameByTrack1(cardInfoEntity.tk1)
                        cardInfoEntity.vehicleNumber =
                            HexaUtils.getVehicleNumberByTrack1(cardInfoEntity.tk1)
                        cardInfoEntity.cardType = Constants.MAG_STRIPE
                        cardSuccessListener.performActionSuccess(cardInfoEntity)
                        cardEventListener.onCardReadSuccess()
                        break
                    } else {
                        cardEventListener.onCardEvent(CardEventState(CardEventState.SWIPE_INCORRECT))
                    }
                } catch (magdec : MagDevException ) {
                    interrupt()
                    Log.d("MagReadThread", magdec.toString())
                    cardEventListener.onCardEvent(CardEventState(CardEventState.SWIPE_INCORRECT))
                } catch (nullPointerException : NullPointerException) {
                    interrupt()
                    Log.d("MagReadThread", nullPointerException.toString())
                    cardEventListener.onCardEvent(CardEventState(CardEventState.SWIPE_INCORRECT))
                } catch (stringOutOfBoundException: StringIndexOutOfBoundsException) {
                    interrupt()
                    Log.d("MagReadThread", stringOutOfBoundException.toString())
                    cardEventListener.onCardEvent(CardEventState(CardEventState.SWIPE_INCORRECT))
                }
            }
            SystemClock.sleep(100)
        }
    }
}