package com.paytm.hpclpos.cardredoptions

import android.content.Context
import com.paytm.hpclpos.posterminal.cardChipRead.IccTester

class CardOptions(
    context: Context,
    cardEventListener: CardEventListener,
    cardSuccessListener: CardSuccessListener,
    enableIcc: Boolean,
    enableMag: Boolean) {

    companion object {

        private var magReadThread: MagReadThread? = null
        private var iccDetectThread: IccDetectThread? = null
        lateinit var cardEntListnr: CardEventListener

        fun closeIccAndMag() {
            magReadThread?.interrupt()
            iccDetectThread?.interrupt()
            IccTester.instance?.close(0.toByte())
            IccTester.instance?.light(false)
            MagTester.instance?.close()
            MagTester.instance?.reset()
        }

        fun closeIcc() {
            iccDetectThread?.interrupt()
            IccTester.instance?.close(0.toByte())
            IccTester.instance?.light(false)
        }

        object cardeventListener : CardEventListener {
            override fun onCardEvent(state: CardEventState?) {
                closeIccAndMag()
                cardEntListnr.onCardEvent(state)
            }

            override fun onCardReadSuccess() {
                // OnSucess of Card Reading called interupt method to stop thread running in background
                magReadThread?.interrupt()
                iccDetectThread?.interrupt()
                // calling card cardEventListener for Ui updations
                cardEntListnr.onCardReadSuccess()
            }
        }
    }

    init {
        // assigning Listener from Confirm Amount Fragment to send Success and Failure Results
        cardEntListnr = cardEventListener
        enableIcc(enableIcc,cardSuccessListener)
        enableMag(context, cardSuccessListener,enableMag)
    }

    fun enableIcc(enableIcc: Boolean, cardSuccessListener: CardSuccessListener) {
        if (enableIcc) {
            iccDetectThread = IccDetectThread(cardeventListener, cardSuccessListener)
            iccDetectThread?.start()
        }
    }

    fun enableMag(context: Context,cardSuccessListener: CardSuccessListener,enableMag: Boolean) {
        if (enableMag) {
            magReadThread = MagReadThread(context, cardeventListener, cardSuccessListener)
            MagTester.instance?.open()
            MagTester.instance?.reset()
            magReadThread?.start()
        }
    }
}
