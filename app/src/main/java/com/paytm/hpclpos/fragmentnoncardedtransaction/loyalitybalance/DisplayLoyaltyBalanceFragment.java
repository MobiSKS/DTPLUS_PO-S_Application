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

    }

    private void goToMainPage() {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }
}