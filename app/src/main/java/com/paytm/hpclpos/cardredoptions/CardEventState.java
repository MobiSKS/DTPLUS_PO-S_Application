package com.paytm.hpclpos.cardredoptions;

import lombok.Getter;

public class CardEventState {
    public static final String CLSS_LIGHT_STATUS_NOT_READY = "CLSS_LIGHT_STATUS_NOT_READY";
    public static final String CLSS_LIGHT_STATUS_IDLE = "CLSS_LIGHT_STATUS_IDLE";
    public static final String CLSS_LIGHT_STATUS_READY_FOR_TXN = "CLSS_LIGHT_STATUS_READY_FOR_TXN";
    public static final String CLSS_LIGHT_STATUS_PROCESSING = "CLSS_LIGHT_STATUS_PROCESSING";
    public static final String CLSS_LIGHT_STATUS_REMOVE_CARD = "CLSS_LIGHT_STATUS_REMOVE_CARD";
    public static final String CLSS_LIGHT_STATUS_COMPLETE = "CLSS_LIGHT_STATUS_COMPLETE";
    public static final String CLSS_LIGHT_STATUS_ERROR = "CLSS_LIGHT_STATUS_ERROR";
    public static final String FALLBACK_SWIPE = "FALLBACK_SWIPE";
    public static final String FALLBACK_SWIPE_CHIP_NOT_ACCEPTED = "FALLBACK_SWIPE_CHIP_NOT_ACCEPTED";
    public static final String RETRY_INSERT = "RETRY_INSERT";
    public static final String ERROR_READ = "ERROR_READ";
    public static final String SWIPE_INCORRECT="SWIPE_INCORRECT";
    public static final String CARD_REMOVED_READ_WHILE_READING = "CARD_REMOVED_READ_WHILE_READING";
    public static final String TAP_MULTIPLE_CARDS="TAP_MULTIPLE_CARDS";

    @Getter
    public String state;

    public CardEventState(String state) {
        this.state = state;
    }
}
