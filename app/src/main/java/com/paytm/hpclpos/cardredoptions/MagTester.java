package com.paytm.hpclpos.cardredoptions;

import android.util.Log;

import com.pax.dal.IMag;
import com.pax.dal.entity.TrackData;
import com.pax.dal.exceptions.MagDevException;
import com.paytm.hpclpos.posterminal.base.DemoApp;
import com.paytm.hpclpos.posterminal.util.BaseTester;


public class MagTester extends BaseTester {

    private static MagTester magTester;

    private IMag iMag;

    private MagTester() {
        iMag = DemoApp.getDal().getMag();
    }

    public static MagTester getInstance() {
        if (magTester == null) {
            magTester = new MagTester();

        }
        return magTester;
    }

    public void open() {
        try {
            iMag.open();
            logTrue("open");
        } catch (MagDevException e) {
            Log.d("Error",e.getMessage());
            logErr("open", e.toString());
        }
    }

    public void close() {
        try {
            iMag.close();
            logTrue("close");
        } catch (MagDevException e) {
            logErr("Error",e.getMessage());
            logErr("close", e.toString());
        }
    }

    // Reset magnetic stripe card reader, and clear buffer of magnetic stripe card.
    public void reset() {
        try {
            iMag.reset();
            logTrue("reSet");
        } catch (MagDevException e) {
            logErr("Error",e.getMessage());
            logErr("reSet", e.toString());
        }
    }

    // Check whether a card is swiped
    public boolean isSwiped() {
        boolean b = false;
        try {
            b = iMag.isSwiped();
        } catch (MagDevException e) {
            logErr("Error",e.getMessage());
            logErr("isSwiped", e.toString());
        }
        return b;
    }

    public TrackData read() throws MagDevException {
        TrackData trackData = iMag.read();
        logTrue("read");
        return trackData;
    }
}
