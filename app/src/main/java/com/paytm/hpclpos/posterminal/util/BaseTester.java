package com.paytm.hpclpos.posterminal.util;

import android.util.Log;

public class BaseTester {

    private String childName = "";

    public BaseTester() {
        //Do nothing
    }

    public void logTrue(String method) {
        childName = getClass().getSimpleName() + ".";
        String trueLog = childName + method;
        Log.i("IPPITest", trueLog);
    }

    public void logErr(String method, String errString) {
        childName = getClass().getSimpleName() + ".";
        String errorLog = childName + method + "   errorMessage：" + errString;
        Log.e("IPPITest", errorLog);
        FloatView.appendLog("error:"+errorLog + "\n");
    }

    public void clear() {
        FloatView.clearLog();
    }
}
