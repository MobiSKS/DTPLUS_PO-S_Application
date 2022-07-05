package com.paytm.hpclpos.cardredoptions;

import android.util.Log;

import com.pax.dal.exceptions.IccDevException;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.posterminal.cardChipRead.IccTester;
import com.paytm.hpclpos.posterminal.util.HexaUtils;
import com.paytm.hpclpos.posterminal.util.IApdu;
import com.paytm.hpclpos.posterminal.util.Packer;

public class IccDetectThread extends Thread {
    private CardEventListener cardEventListener;
    private String issuerId;
    private String customerName;
    private String vNumber;
    private String dob;
    private String epurseMax;
    private String epurseMin;
    private String transMax;
    private String transMin;
    private String monthLimit;
    private String monthSpend;
    private String cardActivationDate;
    private String cardExpiry;
    private CardInfoEntity cardInfoEntity;
    private CardSuccessListener cardSuccessListener;
    private static final String STATUS_STRING = "StatusString:";
    private static final String STATUS = " Status:";
    private static final String ICC_DETECT_THREAD = "IccDetect Thread";
    private static final String DATA = " Data:";

    IccDetectThread(CardEventListener cardEventListener,CardSuccessListener cardSuccessListener) {
        this.cardEventListener = cardEventListener;
        this.cardSuccessListener = cardSuccessListener;
    }

    @Override
    public void run() {
        super.run();
        IccTester.getInstance().light(true);
        while (!interrupted()) {
            try {
                cardInfoEntity = new CardInfoEntity();
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
                        Log.d("oldCard","IccDetectThread");
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

                                                                buildIsoStr(isoStr,apduRespreadPan,apdu);
                                                            } else {
                                                                cardEventListener.onCardEvent(new CardEventState(CardEventState.ERROR_READ));
                                                                Log.i("Test", "Can not read PAN nubber");
                                                                return;

                                                            }
                                                        }

                                                    } catch (Exception e) {
                                                        cardEventListener.onCardEvent(new CardEventState(CardEventState.ERROR_READ));
                                                        Log.e(ICC_DETECT_THREAD, e.getLocalizedMessage());
                                                    }
                                                } else {
                                                    cardEventListener.onCardEvent(new CardEventState(CardEventState.ERROR_READ));
                                                    Log.i("Test", "Not a valid PAN path");
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }

                            } catch (Exception ignored) {
                                Log.e(ICC_DETECT_THREAD, ignored.getLocalizedMessage());
                            }
                        }
                    }
                    break;
                }
            } catch (IccDevException e){
                Log.e(ICC_DETECT_THREAD, e.getMessage());
                cardEventListener.onCardEvent(new CardEventState(CardEventState.CARD_REMOVED_READ_WHILE_READING));
            }
        }
    }

    StringBuilder buildIsoStr(StringBuilder isoStr,IApdu.IApduResp apduRespreadPan,IApdu apdu) {
        try {
            isoStr.append("PAN NUMBER (16 digit) :").append(HexaUtils.hexToAscii(
                    HexaUtils.ByteArrayToHexString(apduRespreadPan.getData())));
            readCardProfile(apdu);
            String panNumner = HexaUtils.hexToAscii(HexaUtils.ByteArrayToHexString(apduRespreadPan.getData()));
            cardInfoEntity.setCardNo(panNumner);
            isoStr.append("\nIssueID : ").append(issuerId);

            isoStr.append("\n").append(customerName);

            isoStr.append("\nVehicleNumner :").append(vNumber);

            isoStr.append("\nDob :").append(dob);

            isoStr.append("\nePurseMax :").append(epurseMax);

            isoStr.append("\nepurseMin :").append(epurseMin);

            isoStr.append("\ntransMax :").append(transMax);

            isoStr.append("\ntransMin :").append(transMin);

            isoStr.append("\nMonthLimit :").append(monthLimit);

            isoStr.append("\nmonthSpend :").append(monthSpend);

            isoStr.append("\ncardActivationDate :").append(cardActivationDate);

            isoStr.append("\nCardExpiry :").append(cardExpiry.substring(0,2)+"/"+cardExpiry.substring(2));

            Log.d("Icc Detect Thread", "iso str" + isoStr);

            cardSuccessListener.performActionSuccess(cardInfoEntity);
            cardEventListener.onCardReadSuccess();

        } catch (Exception e) {
            Log.e(ICC_DETECT_THREAD, e.getLocalizedMessage());
        }
        return isoStr;
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
                        + STATUS + apduRespMF.getStatus() + " "+  apduRespMF.getStatusString();
                Log.i("MF", isoStr);

                statustr = apduRespMF.getStatusString();

            } catch (Exception e) {
                Log.e(ICC_DETECT_THREAD, e.getLocalizedMessage());
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
                        + STATUS + apduRespDF.getStatus() + " "+ STATUS_STRING + apduRespDF.getStatusString();
                Log.i("DF", isoStr);

                statusstr = apduRespDF.getStatusString();
            } catch (Exception e) {
                Log.e(ICC_DETECT_THREAD, e.getLocalizedMessage());
            }
        }
        return statusstr;
    }

    public void readCardProfile(IApdu apdu) {

        String isoStr = "";
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
                                Log.i("Card Profile Data",HexaUtils.hexToAscii(cardProfileData));
                                //Get Issue ID 20 Char
                                issuerId = HexaUtils.hexToAscii(cardProfileData.substring(0, 20));
                                Log.i("issuerId", issuerId);
                                cardInfoEntity.setIccIssuerId(issuerId);
                                customerName = HexaUtils.hexToAscii(cardProfileData.substring(20, 68));
                                cardInfoEntity.setCardHolderName(customerName);

                                Log.i("customerName", customerName);

                                vNumber = HexaUtils.hexToAscii(cardProfileData.substring(68, 108));
                                cardInfoEntity.setVehicleNumber(vNumber);

                                dob = HexaUtils.hexToAscii(cardProfileData.substring(108, 120));
                                cardInfoEntity.setIccDob(dob);

                                epurseMax = HexaUtils.hexToAscii(cardProfileData.substring(124, 148));
                                cardInfoEntity.setIccPurseMax(epurseMax);

                                epurseMin = HexaUtils.hexToAscii(cardProfileData.substring(148, 172));
                                cardInfoEntity.setIccPurseMin(epurseMin);

                                transMax = HexaUtils.hexToAscii(cardProfileData.substring(172, 196));
                                cardInfoEntity.setIcctransMax(epurseMin);

                                transMin = HexaUtils.hexToAscii(cardProfileData.substring(196, 220));
                                cardInfoEntity.setIcctransMin(transMin);

                                monthLimit = HexaUtils.hexToAscii(cardProfileData.substring(220, 224));
                                cardInfoEntity.setIccMonthLimit(monthLimit);

                                monthSpend = HexaUtils.hexToAscii(cardProfileData.substring(224, 248));
                                cardInfoEntity.setIccmonthSpend(monthSpend);

                                cardActivationDate = HexaUtils.hexToAscii(cardProfileData.substring(248, 260));
                                cardInfoEntity.setIcccardActivationDate(cardActivationDate);

                               // cardExpiry = HexaUtils.hexToAscii(cardProfileData.substring(260, 272));
                                cardExpiry = HexaUtils.hexToAscii(cardProfileData.substring(300, 308));
                                cardInfoEntity.setExpiredDate(cardExpiry.substring(0,2)+"/"+cardExpiry.substring(2));
                                cardInfoEntity.setCardType(Constants.ICC);

                            } else {

                                Log.i("Test", "Some problem with Card profile read");

                            }
                        } catch (Exception e) {
                            Log.e(ICC_DETECT_THREAD, e.getLocalizedMessage());
                        }
                    } else {
                        Log.i("Test", "Can not read  Card profile");
                    }

                } else {
                    Log.i("Test", "Can not set path for Card profile");
                }

            } catch (Exception e) {
                Log.e(ICC_DETECT_THREAD, e.getLocalizedMessage());
            }
        }
    }
}
