package com.paytm.hpclpos.fragmentnoncardedtransaction.loyaltyredeem;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.MyKeyboard;
import com.paytm.hpclpos.constants.Validation;


import java.util.Locale;

public class EnterPointToRedeemFrag extends Fragment {

    private EditText enterRedeemPointText;

    private String rcvAmount;

    private TextToSpeech textToSpeech;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_enter_point_to_redeem, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    String utteranceId = this.hashCode() + "";
                    int speechStatus = textToSpeech.speak("Please Enter Redeem Point", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView gotoBack = rootView.findViewById(R.id.gotoBack);

        enterRedeemPointText = rootView.findViewById(R.id.enterRedeemPointText);
        enterRedeemPointText.setText(rcvAmount);
        LinearLayout lLconfirm = rootView.findViewById(R.id.lLconfirm);
        MyKeyboard keyboard = rootView.findViewById(R.id.keyboard);

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        lLconfirm.setOnClickListener(v -> callCnfrmPointAndAmountFrag());

        enterRedeemPointText.setOnTouchListener((v, event) -> {
            int inType = enterRedeemPointText.getInputType(); // backup the input type
            enterRedeemPointText.setInputType(InputType.TYPE_NULL); // disable soft input
            enterRedeemPointText.onTouchEvent(event); // call native handler
            enterRedeemPointText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        enterRedeemPointText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        enterRedeemPointText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = enterRedeemPointText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

        return rootView;

    }

    private void callCnfrmPointAndAmountFrag(){
        String redeemPoint= enterRedeemPointText.getText().toString().trim();

        Log.e("RedeemPoint",redeemPoint);

         if (!Validation.isvalidRedeemPoint(redeemPoint)){
            Toast.makeText(getContext(),"Please enter a Valid Redeem Points",Toast.LENGTH_SHORT).show();
         } else {
            Bundle bundle=new Bundle();
            bundle.putString(Constants.REDEEMPOINT,redeemPoint);
            Fragment fragment = new ConfrmPointAndAmntFrag();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        }
    }
}