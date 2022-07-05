package com.paytm.hpclpos.posterminal.cardChipRead;

import com.pax.dal.IIcc;
import com.pax.dal.exceptions.IccDevException;
import com.paytm.hpclpos.posterminal.base.DemoApp;
import com.paytm.hpclpos.posterminal.util.BaseTester;


public class IccTester extends BaseTester {

    private static IccTester iccTester;

    private IIcc icc;

    private IccTester() {
        icc = DemoApp.getDal().getIcc();
    }

    public static IccTester getInstance() {
        if (iccTester == null) {
            iccTester = new IccTester();
        }
        return iccTester;
    }

    public byte[] init(byte slot) throws IccDevException {
        byte[] initRes = null;
        initRes = icc.init(slot);
        logTrue("init");
        return initRes;
    }

    public boolean detect(byte slot) {
        boolean res = false;
        try {
            res = icc.detect(slot);
            logTrue("detect");
            return res;
        } catch (IccDevException e) {
            e.printStackTrace();
            logErr("detect", e.toString());
            return res;
        }
    }

    public void close(byte slot) {
        try {
            icc.close(slot);
            logTrue("close");
        } catch (IccDevException e) {
            e.printStackTrace();
            logErr("close", e.toString());
        }
    }

    public void autoResp(byte slot, boolean autoresp) {
        try {
            icc.autoResp(slot, autoresp);
            logTrue("autoResp");
        } catch (IccDevException e) {
            e.printStackTrace();
            logErr("autoResp", e.toString());
        }
    }

    public byte[] isoCommand(byte slot, byte[] send) {
        byte[] resp = {};
        try {
            resp = icc.isoCommand(slot, send);
            logTrue("isoCommand");
            return resp;
        } catch (IccDevException e) {
            e.printStackTrace();
            logErr("isoCommand", e.toString());
            return resp;
        }
    }
    
    public void light(boolean flag){
        try {
            icc.light(flag);
            logTrue("light");
        } catch (IccDevException e) {
            e.printStackTrace();
            logErr("light", e.toString());
        }
    }
}
