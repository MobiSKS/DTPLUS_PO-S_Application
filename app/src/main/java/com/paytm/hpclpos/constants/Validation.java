package com.paytm.hpclpos.constants;


import android.util.Log;


/**
 * This class is used for validation in this project
 * @author Arif Ali
 */


public class Validation {

    private Validation(){
        // Do nothing
    }

    public static boolean isValidEnterAmount(String amount) {

        Log.e("Amount=", amount.length()+"");

        amount = amount.trim();
        return !amount.trim().equalsIgnoreCase("") && !amount.trim().equalsIgnoreCase("0.00");
    }

    /**
     * It is a method used for verify mobile number digits
     * @param mobNo
     * @return
     */
    public static boolean isValidMobileNo(String mobNo) {
        return (mobNo.length() == 10 && !mobNo.startsWith("0"));
    }

    /**
     * It is a method used for verify OTP length which is valid or not
     * @param otp
     * @return
     */
    public static boolean isValidOtp(String otp) {
        return (otp != null && otp.length() == 4);
    }

    /**
     * This method is used for mobile number ten digit validate and also validate not entering null value
     * @param mobNo
     * @return
     */
    public static boolean isvalidMobNo(String mobNo) {
        return (mobNo != null && mobNo.trim().length() == 10 && mobNo.trim().charAt(0) != '0');
    }

    /**
     * This method is used for validate paycode in this project which is more than 5 digits and less than 16 digits
     * @param payCodeno
     * @return
     */

    public static boolean isvalidpaycode(String payCodeno) {

        return payCodeno != null && payCodeno.trim().length() > 5 ||
                payCodeno != null && payCodeno.trim().length() == 16;

    }


    /**
     * This method is used for token validation and return boolean values
     * @param tokenNo
     * @return
     */
    public static boolean isvalidTokenNo(String tokenNo) {

         return  (tokenNo.trim().length() > 4 || tokenNo.trim().length() == 8);

    }

    /**
     * This method is used for entering Control Card Number is valid or not
     * @param cardNo
     * @return
     */
    public static boolean isvalidCTRLCardNO(String cardNo) {
        boolean retValue = false;
        if (cardNo != null && cardNo.trim().length() >= 10) {
            retValue = true;
        }
        return retValue;
    }

    /**
     * This method is used for validate control card pin  validation
     * @param controlPin
     * @return
     */
    public static boolean isvalidControlPin(String controlPin) {

        boolean retValue = false;

        if (controlPin != null && controlPin.trim().length() == 4) {

            retValue = true;
        }

        return retValue;

    }
    /**
     * This method is used for checking the length of control card pin entered
     */
    public static boolean isvalidCardPin(String cardPin) {

        boolean retValue = false;

        if (cardPin != null && cardPin.trim().length() == 4) {

            retValue = true;
        }

        return retValue;

    }

    /**
     * This  method is used for validate Terminal pin entered length is valid or not
     * @param terminalPin
     * @return
     */
    public static boolean isvalidTerminalPin(String terminalPin) {

        boolean retValue = false;

        if (terminalPin != null && terminalPin.trim().length() == 4) {

            retValue = true;
        }

        return retValue;

    }

    /**
     * This method is used for UTR number validation
     * @param utrNo
     * @return
     */
    public static boolean isvalidUTRNo(String utrNo) {

        boolean retValue = false;

        if (utrNo != null && utrNo.trim().length() <= 22 && utrNo.trim().length() > 0) {

            retValue = true;
        }

        return retValue;

    }

    /**
     * This method is used  for not entering null value in redeem points
     * @param redeemPoint
     * @return
     */
    public static boolean isvalidRedeemPoint(String redeemPoint) {

        boolean retValue = false;
        if (redeemPoint != null && !redeemPoint.equalsIgnoreCase("")) {

            int rdmpoint;

            rdmpoint = Integer.parseInt(redeemPoint);

            if (rdmpoint > 0) {

                retValue = true;
            }
        }

        return retValue;

    }

    /**
     * This method is used  for valid form number checking
     * @param formNo
     * @return
     */
    public static boolean isvalidFormNo(String formNo) {

        boolean retValue = false;

        if (formNo != null && formNo.trim().length() == 10) {

            retValue = true;
        }

        return retValue;

    }

    /**
     * This method is  used for validating correct cheque number entered or not
     * @param chequeNo
     * @return
     */
    public static boolean isvalidChequeNo(String chequeNo) {
        boolean retValue = false;

        if (chequeNo != null && chequeNo.trim().length() == 6) {
            retValue = true;
        }
        return retValue;
    }

    /**
     * This method  is used for entering  valid roc number
     * @param chequeNo
     * @return
     */
    public static boolean isValidRocNo(String chequeNo) {

        boolean retValue = false;

        if (chequeNo != null && chequeNo.trim().length() > 0) {


            retValue = true;
        }

        return retValue;

    }

    /**
     * This  method  is used for entering correct micr code number
     * @param mICRCode
     * @return
     */
    public static boolean isvalidMICRCode(String mICRCode) {

        boolean retValue = false;

        if (mICRCode != null && mICRCode.trim().length() == 9) {

            retValue = true;
        }

        return retValue;

    }

    /**
     * This method is used for checking valid OTP number entering
     * @param oTPNo
     * @return
     */
    public static boolean isvalidOTPNo(String oTPNo) {

        boolean retValue = false;

        if (oTPNo != null && oTPNo.trim().length() == 6) {

            retValue = true;
        }

        return retValue;

    }

    /**
     * This method is used for checking  correct odometer reading
     * @param odometerNo
     * @return
     */
    public static boolean isvalidOdometerReading(String odometerNo) {

        boolean retValue = false;

        if (odometerNo != null && odometerNo.trim().length() == 2) {


            retValue = true;
        }

        return retValue;

    }

    /**
     * This method is used for validate redeem point entered
     * @param redeemPoint
     * @return
     */
    public static boolean isValidRedeemPoints(String redeemPoint) {

        boolean retValue = false;

        if (redeemPoint != null && redeemPoint.trim().length() == 5) {


            retValue = true;
        }

        return retValue;

    }

    public static boolean isValidTerminalId(String terminalId) {
        return (terminalId != null && terminalId.trim().length() == 10);
    }
}
