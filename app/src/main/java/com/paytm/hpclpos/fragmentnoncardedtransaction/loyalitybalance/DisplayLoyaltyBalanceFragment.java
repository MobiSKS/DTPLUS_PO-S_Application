package com.paytm.hpclpos.fragmentnoncardedtransaction.loyalitybalance;

import static com.pax.dal.entity.EFontTypeAscii.FONT_12_48;
import static com.pax.dal.entity.EFontTypeAscii.FONT_16_48;
import static com.pax.dal.entity.EFontTypeAscii.FONT_8_32;
import static com.pax.dal.entity.EFontTypeExtCode.FONT_16_16;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pax.dal.entity.EFontTypeAscii;
import com.pax.dal.entity.EFontTypeExtCode;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity;
import com.paytm.hpclpos.posterminal.printer.PrinterTester;


public class DisplayLoyaltyBalanceFragment extends Fragment {

    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_display_loyality_balance, container, false);

        LinearLayout lLgoToMain= rootView.findViewById( R.id.lLgoToMain);

        lLgoToMain.setOnClickListener(v -> goToMainPage());

        rootView.setOnTouchListener((view, motionEvent) -> true);

        showGifDialog();

        new Handler().postDelayed(() -> dialog.dismiss(), 3000);

        showReceipt();

        return rootView;
    }

    private void showGifDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_print_gif_layout);
        ImageView imageView = dialog.findViewById(R.id.gifImageview);
        Glide.with(DisplayLoyaltyBalanceFragment.this).load(R.drawable.printergif).into(imageView);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

    private void showReceipt(){

        new Thread(() -> {
            PrinterTester.getInstance().init();
            PrinterTester.getInstance().setInvert(false);

            String str = "         CUSTOMER COPY";
            String str1="            HPCL";
            String str2="        DriveTrack Plus";
            String str3="       MOTI RAM AND SONS";
            String str4="           PALWAL";
            String str5="DATE/TIME:          02/06/21  06:36:21";
            String strTid="TID:                        5000057570";
            String strDevider="----------------------------------------";
            String strctrlId="CONTROL ID:                 1900006767";
            String str7="    LOYLTY REDEEM (POINTS)";
            String str8="SERVICE:               LOYLTY_BALANCE_ENQ";
            String str10="BALANCE:                           216129";
            String str15="----------------------------------------";
            String str16="                Thank You";
            String str17="                   HPCL";
            String str18="-----------------------------------------";
            if (str != null && str.length() > 0){
                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_16_48,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(2);
                PrinterTester.getInstance().step(100);

                PrinterTester.getInstance().spaceSet(Byte.parseByte("1"),
                        Byte.parseByte("0"));
                PrinterTester.getInstance().printStr(str +"\n", null);
                PrinterTester.getInstance().printStr(str1+"\n", null);
                PrinterTester.getInstance().printStr(str2+"\n", null);
                PrinterTester.getInstance().printStr(str3+"\n", null);
                PrinterTester.getInstance().printStr(str4+"\n"+"\n", null);

                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_8_32,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(2);

                PrinterTester.getInstance().printStr(str5+"\n", null);
                PrinterTester.getInstance().printStr(strTid+"\n", null);
                PrinterTester.getInstance().printStr(strDevider+"\n", null);
                PrinterTester.getInstance().printStr(strctrlId+"\n", null);

                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_12_48,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(3);
                PrinterTester.getInstance().printStr(str7 +"\n", null);

                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_8_32,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(2);

                PrinterTester.getInstance().printStr(str8+"\n", null);
                PrinterTester.getInstance().printStr(str10+"\n", null);
                PrinterTester.getInstance().printStr(str15+"\n", null);
                PrinterTester.getInstance().printStr(str16+"\n", null);
                PrinterTester.getInstance().printStr(str17+"\n", null);
                PrinterTester.getInstance().printStr(str18+"\n"+"\n", null);

                PrinterTester.getInstance().start();
            }
        }).start();
    }

    private void goToMainPage() {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }
}