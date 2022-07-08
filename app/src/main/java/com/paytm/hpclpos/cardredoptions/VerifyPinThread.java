package com.paytm.hpclpos.cardredoptions;

import android.os.Build;
import android.util.Log;
import com.pax.dal.exceptions.IccDevException;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.posterminal.cardChipRead.IccTester;
import com.paytm.hpclpos.posterminal.util.HexaUtils;
import com.paytm.hpclpos.posterminal.util.IApdu;
import com.paytm.hpclpos.posterminal.util.Packer;

import java.util.Objects;

public class VerifyPinThread extends Thread {

    private static final String TAG = "VerifyPinThread";
    private CardEventListener cardEventListener;
    private static final String STATUS_STRING = "StatusString:";
    private static final String STATUS = " Status:";
    private static final String DATA = " Data:";
    private static final String SET_PATH_FOR_USER_PAN = "";
    private String action;
    private String oldpin;
    private String newPin;

    VerifyPinThread(CardEventListener cardEventListener,String action) {
         this.action = action;
         this.cardEventListener = cardEventListener;
    }

    public void setOldAndNewPin(String oldpin,String newPin) {
        this.newPin = newPin;
        this.oldpin = oldpin;
    }

    @Override
    public void run() {
        super.run();
        IccTester.getInstance().light(true);
        while (!interrupted()) {
            try {
                if (IccTester.getInstance().detect((byte) 0)) {
                    byte[] res = IccTester.getInstance().init((byte) 0);
                    if (res == null) {
                        Log.i("Test", "init ic card,but no response");
                        return;
                    }
                    IccTester.getInstance().autoResp((byte) 0, true);

                    IApdu apdu = Packer.getInstance().getApdu();

                    boolean oldcard = false;

                    if (oldcard) {
                        IApdu.IApduReq apduReq = apdu.createReq((byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x00,
                                "1PAY.SYS.DDF01".getBytes(), (byte) 0);
                        Log.d("oldCard", "IccDetectThread");
                        break;
                    } else {

                        IApdu.IApduReq apduReqSelectAid = apdu.createReq((byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x0, HexaUtils.HexStringToByteArray("504159464C4558503553"));
                        byte[] req = apduReqSelectAid.pack();

                        byte[] isoRes = IccTester.getInstance().isoCommand((byte) 0, req);

                        if (isoRes != null) {
                            IApdu.IApduResp apduResp = apdu.unpack(isoRes);

                            try {
                                if (apduResp.getStatusString().equalsIgnoreCase("9000") || apduResp.getStatusString().equalsIgnoreCase("0061") || apduResp.getStatusString().equalsIgnoreCase("0920") || apduResp.getStatusString().equalsIgnoreCase("009F")) {
                                    //Read PAN Number and set MF and DF path BF

                                    String statusmf = setMF(apdu);

                                    if (statusmf.equalsIgnoreCase("9000")) {
                                        String statusdf = setDF(apdu);

                                        if (statusdf.equalsIgnoreCase("9000")) {

                                            //Set Path for user PAN
                                            IApdu.IApduReq apduReqPpan = apdu.createReq((byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, HexaUtils.HexStringToByteArray("2F02"));
                                            byte[] reqPpan = apduReqPpan.pack();
                                            byte[] isoResPpan = IccTester.getInstance().isoCommand((byte) 0, reqPpan);
                                            StringBuilder isoStr = new StringBuilder();
                                            if (isoResPpan != null) {
                                                IApdu.IApduResp apduRespPpan = apdu.unpack(isoResPpan);

                                                if (apduRespPpan.getStatusString().equalsIgnoreCase("9000")) {

                                                    try {
                                                        //Read PAN number
                                                        byte[] reqreadPan = {(byte) 0x00, (byte) 0xb2, (byte) 0x01, (byte) 0X04, (byte) 0X10};
                                                        byte[] isoResreadPan = IccTester.getInstance().isoCommand((byte) 0, reqreadPan);

                                                        if (isoResreadPan != null) {
                                                            IApdu.IApduResp apduRespreadPan = apdu.unpack(isoResreadPan);

                                                            if (apduRespreadPan.getStatusString().equalsIgnoreCase("9000")) {

                                                                buildIsoStr(isoStr, apduRespreadPan, apdu);
                                                            } else {
                                                                Log.i(TAG, "Can not read PAN nubber");
                                                                return;

                                                            }
                                                        }

                                                    } catch (Exception e) {
                                                        Log.e(TAG, e.getLocalizedMessage());
                                                    }
                                                } else {
                                                    Log.i("Test", "Not a valid PAN path");
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }

                            } catch (Exception ignored) {
                                Log.e(TAG, ignored.getLocalizedMessage());
                            }
                        }
                    }
                    break;
                }
            } catch (IccDevException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static String setMF(IApdu apdu) {

        String statustr = "";

        byte[] reqMF = {(byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x3F, (byte) 0x00};

        Log.i("SET MF", HexaUtils.ByteArrayToHexString(reqMF));
        byte[] isoResMF = IccTester.getInstance().isoCommand((byte) 0, reqMF);
        if (isoResMF != null) {
            IApdu.IApduResp apduRespMF = apdu.unpack(isoResMF);

            try {

                String isoStr = "isocommand response :" + DATA + HexaUtils.ByteArrayToHexString(apduRespMF.getData())
                        + STATUS + apduRespMF.getStatus() + " " + apduRespMF.getStatusString();
                Log.i("MF", isoStr);

                statustr = apduRespMF.getStatusString();

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
        return statustr;
    }

    public static String setDF(IApdu apdu) {
        String statusstr = "";

        byte[] reqDF = {(byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x7F, (byte) 0x02};

        Log.i("SET DF", HexaUtils.ByteArrayToHexString(reqDF));
        byte[] isoResDF = IccTester.getInstance().isoCommand((byte) 0, reqDF);

        if (isoResDF != null) {
            IApdu.IApduResp apduRespDF = apdu.unpack(isoResDF);

            try {
                String isoStr = "isocommand response :" + DATA + HexaUtils.ByteArrayToHexString(apduRespDF.getData())
                        + STATUS + apduRespDF.getStatus() + " " + STATUS_STRING + apduRespDF.getStatusString();
                Log.i("DF", isoStr);

                statusstr = apduRespDF.getStatusString();
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
        return statusstr;
    }

    StringBuilder buildIsoStr(StringBuilder isoStr, IApdu.IApduResp apduRespreadPan, IApdu apdu) {
        try {
            isoStr.append("PAN NUMBER (16 digit) :").append(HexaUtils.hexToAscii(
                    HexaUtils.ByteArrayToHexString(apduRespreadPan.getData())));
            readCardProfile(apdu);
            if(action.equals(Constants.VERIFY_PIN)) {
                verifyPin(apdu);
            } else if(action.equals(Constants.CHANGE_PIN)) {
                changePin(apdu);
            }
            String panNumner = HexaUtils.hexToAscii(HexaUtils.ByteArrayToHexString(apduRespreadPan.getData()));

            Log.d("Icc Detect Thread", "iso str" + panNumner);
            Log.d("Icc Detect Thread", "iso str" + isoStr);

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return isoStr;
    }

    public void verifyPin(IApdu apdu) {

        byte[] reqReadSetCard = {(byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x20};
        byte[] isoResSetCard = IccTester.getInstance().isoCommand((byte) 0, reqReadSetCard);

        if (isoResSetCard != null) {
            IApdu.IApduResp apduRespSetCard = apdu.unpack(isoResSetCard);

            try {
                if (apduRespSetCard.getStatusString().equalsIgnoreCase("9000")) {

                    byte[] pinByteArray = HexaUtils.HexStringToByteArray(HexaUtils.stringToHexDecimal(Objects.requireNonNull(GlobalMethods.Companion.getPinData1())));

                    byte[] pr = {(byte) 0x00, (byte) 0x20, (byte) 0x00, (byte) 0x10, (byte) 0x08};

                    byte[] appendF = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

                    byte[] reqReadReadCard = HexaUtils.concatByteArray(pr,pinByteArray,appendF);

                    byte[] isoResReadCard = IccTester.getInstance().isoCommand((byte) 0, reqReadReadCard);

                    if (isoResReadCard != null) {
                        IApdu.IApduResp apduRespReadCard = apdu.unpack(isoResReadCard);
                        try {
                            if (apduRespReadCard.getStatusString().equalsIgnoreCase("9000")) {
                                String cardProfileData = HexaUtils.ByteArrayToHexString(apduRespReadCard.getData());
                                Log.i("cardProfileData", cardProfileData);
                                Log.i("Card Profile Data", HexaUtils.hexToAscii(cardProfileData));
                                cardEventListener.onCardReadSuccess();
                            } else {
                                cardEventListener.onCardEvent(new CardEventState(CardEventState.INCORRECT_PIN));
                                Log.i(TAG, "Some problem with Card profile read");
                            }
                        } catch (Exception e) {
                            cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PIN_READ_ERROR));
                            Log.e(TAG, e.getLocalizedMessage());
                        }
                    } else {
                        cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PIN_READ_ERROR));
                        Log.i("Test", "Can not read  Card profile");
                    }
                } else {
                    cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PIN_READ_ERROR));
                    Log.i("Test", "Can not set path for Card profile");
                }
            } catch (Exception e) {
                cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PIN_READ_ERROR));
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }

    public void readCardProfile(IApdu apdu) {

        byte[] reqReadSetCard = {(byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x20};
        byte[] isoResSetCard = IccTester.getInstance().isoCommand((byte) 0, reqReadSetCard);

        if (isoResSetCard != null) {
            IApdu.IApduResp apduRespSetCard = apdu.unpack(isoResSetCard);

            try {
                if (apduRespSetCard.getStatusString().equalsIgnoreCase("9000")) {

                    byte[] reqReadReadCard = {(byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0xA2};

                    byte[] isoResReadCard = IccTester.getInstance().isoCommand((byte) 0, reqReadReadCard);

                    if (isoResReadCard != null) {
                        IApdu.IApduResp apduRespReadCard = apdu.unpack(isoResReadCard);
                        try {
                            if (apduRespReadCard.getStatusString().equalsIgnoreCase("9000")) {
                                String cardProfileData = HexaUtils.ByteArrayToHexString(apduRespReadCard.getData());
                                Log.i("cardProfileData", cardProfileData);
                                Log.i("Card Profile Data", HexaUtils.hexToAscii(cardProfileData));
                            } else {
                                cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                                Log.i(TAG, "Some problem with Card profile read");
                            }
                        } catch (Exception e) {
                            cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                            Log.e(TAG, e.getLocalizedMessage());
                        }
                    } else {
                        cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                        Log.i(TAG, "Can not read  Card profile");
                    }

                } else {
                    cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                    Log.i(TAG, "Can not set path for Card profile");
                }

            } catch (Exception e) {
                cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }

    private void changePin(IApdu apdu) {

        byte[] reqReadSetCard = {(byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x20};
        byte[] isoResSetCard = IccTester.getInstance().isoCommand((byte) 0, reqReadSetCard);

        if (isoResSetCard != null) {
            IApdu.IApduResp apduRespSetCard = apdu.unpack(isoResSetCard);

            try {
                if (apduRespSetCard.getStatusString().equalsIgnoreCase("9000")) {

                    byte[] appendF = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

                    byte[] newpinByteArray = HexaUtils.concatByteArray(HexaUtils.HexStringToByteArray(HexaUtils.stringToHexDecimal(Objects.requireNonNull(newPin))),appendF);

                    byte[] oldpinByteArray = HexaUtils.concatByteArray(HexaUtils.HexStringToByteArray(HexaUtils.stringToHexDecimal(Objects.requireNonNull(oldpin))),appendF);

                    byte[] pr = {(byte) 0x00, (byte) 0x24, (byte) 0x00, (byte) 0x10, (byte) 0x10};

                    byte[] oldandnewPinBytearray = HexaUtils.concatByteArray(oldpinByteArray,newpinByteArray);

                    byte[] reqReadReadCard = HexaUtils.concatByteArray(pr,oldandnewPinBytearray);

                    byte[] isoResReadCard = IccTester.getInstance().isoCommand((byte) 0, reqReadReadCard);

                    if (isoResReadCard != null) {
                        IApdu.IApduResp apduRespReadCard = apdu.unpack(isoResReadCard);
                        try {
                            if (apduRespReadCard.getStatusString().equalsIgnoreCase("9000")) {
                                String cardProfileData = HexaUtils.ByteArrayToHexString(apduRespReadCard.getData());
                                Log.i("cardProfileData", cardProfileData);
                                Log.i("Card Profile Data",HexaUtils.hexToAscii(cardProfileData));
                                cardEventListener.onCardReadSuccess();
                            } else {
                                cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                                Log.i("Test", "Some problem with Card profile read");
                            }
                        } catch (Exception e) {
                            cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                            Log.e(TAG, e.getLocalizedMessage());
                        }
                    } else {
                        cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                        Log.i("Test", "Can not read  Card profile");
                    }

                } else {
                    cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_PROFILE_READ_ERROR));
                    Log.i("Test", "Can not set path for Card profile");
                }

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }
}
