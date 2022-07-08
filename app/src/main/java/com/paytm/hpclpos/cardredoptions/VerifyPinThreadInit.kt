package com.paytm.hpclpos.cardredoptions

import com.paytm.hpclpos.posterminal.cardChipRead.IccTester

class VerifyPinThreadInit( cardEventListener: CardEventListener,action: String,oldPin: String? = null,newPin: String?= null) {

    companion object {

        private var verifyPinThread: VerifyPinThread? = null
        lateinit var cardEntListnr: CardEventListener

        fun closeIcc() {
            IccTester.instance?.close(0.toByte())
            IccTester.instance?.light(false)
        }

        object cardeventListener : CardEventListener {
            override fun onCardEvent(state: CardEventState?) {
                closeIcc()
                cardEntListnr.onCardEvent(state)
            }

            override fun onCardReadSuccess() {
                // calling card cardEventListener for Ui updations
                cardEntListnr.onCardReadSuccess()
            }
        }
    }

    init {
        // assigning Listener from Confirm Amount Fragment to send Success and Failure Results
        cardEntListnr = cardEventListener
        verifyPinThread = VerifyPinThread(cardeventListener,action)
        // Sets new and old pin for PinChange
        if (oldPin != null && newPin != null) { verifyPinThread?.setOldAndNewPin(oldPin,newPin) }
        verifyPinThread?.start()
    }
}