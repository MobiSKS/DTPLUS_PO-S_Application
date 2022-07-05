package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackvoid;

import static com.pax.dal.entity.EFontTypeAscii.FONT_12_48;
import static com.pax.dal.entity.EFontTypeAscii.FONT_16_48;
import static com.pax.dal.entity.EFontTypeAscii.FONT_8_16;
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

import butterknife.ButterKnife;


public class PaybackSuccessFragment extends Fragment {

    private Dialog dialog;



    public PaybackSuccessFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payback_success, container, false);
        ButterKnife.bind(this,view);
        LinearLayout lLgoToMain=  view.findViewById( R.id.lLgoToMain);

        lLgoToMain.setOnClickListener(v -> goToMainPage());

        showGifDialog();

        new Handler().postDelayed(() -> dialog.dismiss(), 3000);

        showReceipt();
        return view;
    }

    private void showGifDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_print_gif_layout);
        ImageView imageView = dialog.findViewById(R.id.gifImageview);
        Glide.with(this).load(R.drawable.printergif).into(imageView);
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
            String str1="             HPCL";
            String str2="        DriveTrack Plus";
            String str3="       MOTI RAM AND SONS";
            String str4="           PALWAL";
            String str5="DATE/TIME:          02/06/21  06:36:21";
            String strTid="TID:                        5000057570";
            String strBatcchNo="BATCH NO:                           59";
            String strRocNo="ROC NO:                              4";

            String str7="       VOID-MOBILE EARN";

            String str8="MOBILE NO:                     ******9999";
            String str10="CARD TYPE:                        LOYALTY";
            String str11="EXP DATE:                           **/**";
            String str12="AMT:                            RS 500.00";
            String str15="----------------------------------------";
            String str16="Ensure that the above details are correct";
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
                PrinterTester.getInstance().printStr(str4+"\n", null);


                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_8_32,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(2);
                PrinterTester.getInstance().printStr(str5+"\n", null);
                PrinterTester.getInstance().printStr(strTid+"\n", null);
                PrinterTester.getInstance().printStr(strBatcchNo+"\n", null);
                PrinterTester.getInstance().printStr(strRocNo+"\n", null);



                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_12_48,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(3);
                PrinterTester.getInstance().printStr(str7 +"\n", null);



                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_8_32,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(2);
                PrinterTester.getInstance().printStr(str8+"\n", null);
                PrinterTester.getInstance().printStr(str10+"\n", null);
                PrinterTester.getInstance().printStr(str11+"\n"+"\n", null);
                PrinterTester.getInstance().printStr(str12+"\n", null);
                PrinterTester.getInstance().printStr(str15+"\n", null);

                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_8_16,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(1);
                PrinterTester.getInstance().printStr(str16+"\n", null);


                PrinterTester.getInstance().fontSet((EFontTypeAscii) FONT_8_32,
                        (EFontTypeExtCode) FONT_16_16);
                PrinterTester.getInstance().setGray(2);
                PrinterTester.getInstance().printStr(str18+"\n"+"\n", null);



                PrinterTester.getInstance().start();
            }



        }).start();
    }


    private void goToMainPage() {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}