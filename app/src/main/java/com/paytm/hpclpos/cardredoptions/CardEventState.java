package com.paytm.hpclpos.cardredoptions;

import lombok.Getter;

public class CardEventState {
    public static final String ERROR_READ = "ERROR_READ";
    public static final String SWIPE_INCORRECT="SWIPE_INCORRECT";
    public static final String CARD_REMOVED_READ_WHILE_READING = "CARD_REMOVED_READ_WHILE_READING";

    public static final String CARD_PROFILE_READ_ERROR = "CARD_PROFILE_READ_ERROR";
    public static final String INCORRECT_PIN = "INCORRECT_PIN";
    public static final String CARD_PIN_READ_ERROR = "CARD_PIN_READ_ERROR";
    public static final String CHIP_CARD_NOT_DETECTED = "CHIP NOT DETECTED";

    @Getter
    public String state;

    public CardEventState(String state) {
        this.state = state;
    }
}
