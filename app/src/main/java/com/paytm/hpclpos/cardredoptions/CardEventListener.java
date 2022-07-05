package com.paytm.hpclpos.cardredoptions;

public interface CardEventListener {
    void onCardEvent(CardEventState state);

    void onCardReadSuccess();
}