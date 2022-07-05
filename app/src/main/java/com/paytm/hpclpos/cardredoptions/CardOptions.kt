package com.paytm.hpclpos.cardredoptions

import android.content.Context
import com.paytm.hpclpos.posterminal.cardChipRead.IccTester

class CardOptions(context: Context, cardEventListener: CardEventListener, cardSuccessListener: CardSuccessListener) {

    companion object {

        private var magReadThread: MagReadThread? = null
        private var iccDetectThread: IccDetectThread? = null
        lateinit var cardEntListnr: CardEventListener

        fun closeIccAndMag() {
            magReadThread?.interrupt()
            iccDetectThread?.interrupt()
            IccTester.getInstance().close(0.toByte())
            IccTester.getInstance().light(false)
            MagTester.getInstance().close()
            MagTester.getInstance().reset()
        }

        object cardeventListener : CardEventListener {
            override fun onCardEvent(state: CardEventState?) {
                closeIccAndMag()
                cardEntListnr.onCardEvent(state)
            }

            override fun onCardReadSuccess() {
                // OnSucess of Card Reading called interupt method to stop thread running in background
                magReadThread!!.interrupt()
                iccDetectThread!!.interrupt()
                // calling card cardEventListener for Ui updations
                cardEntListnr.onCardReadSuccess()
            }
        }
    }

    init {
        // assigning Listener from Confirm Amount Fragment to send Success and Failure Results
        cardEntListnr = cardEventListener
        magReadThread = MagReadThread(context, cardeventListener, cardSuccessListener)
        MagTester.getInstance().open()
        MagTester.getInstance().reset()
        magReadThread!!.start();

        iccDetectThread = IccDetectThread(cardeventListener, cardSuccessListener)
        iccDetectThread!!.start()
    }
}
