package com.paytm.hpclpos.fragmentnoncardedtransaction.creditsalecomplete;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.MyKeyboard;
import com.paytm.hpclpos.constants.Validation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CSCEnterAmntFragment extends Fragment {

    private String current = "";

    private String currency = "";
    private String separator = ",";
    private Boolean spacing = false;
    private Boolean delimiter = false;
    private Boolean decimals = true;



    private String sendAmount;
    private EditText enterAmountText;

    private TextToSpeech textToSpeech;


    public CSCEnterAmntFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_c_s_c_enter_amnt, container, false);

        viewFinds(rootView);

        return rootView;
    }

    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    String utteranceId=this.hashCode() + "";
                    int speechStatus = textToSpeech.speak("Please Enter Amount", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
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



        ImageView gotoBack= view.findViewById( R.id.gotoBack);
        enterAmountText = view.findViewById(R.id.enterAmountText);
        LinearLayout lLconfirm = view.findViewById(R.id.lLconfirm);
        MyKeyboard keyboard = view.findViewById(R.id.keyboard);

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        lLconfirm.setOnClickListener(v -> callConfirmTransactionFrag());

        enterAmountText.setOnTouchListener((v, event) -> {
            int inType = enterAmountText.getInputType(); // backup the input type
            enterAmountText.setInputType(InputType.TYPE_NULL); // disable soft input
            enterAmountText.onTouchEvent(event); // call native handler
            enterAmountText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        enterAmountText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        enterAmountText.setTextIsSelectable(true);

        enterAmountText.addTextChangedListener(new TextWatcher(){
            DecimalFormat dec = new DecimalFormat("0.00");
            @Override
            public void afterTextChanged(Editable arg0) {
                //Do something
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //Do Something
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (!s.toString().equals(current)) {
                    enterAmountText.removeTextChangedListener(this);



                    String cleanString = s.toString().replaceAll("[$,.]", "").replaceAll(currency, "").replaceAll("\\s+", "");

                    if (cleanString.length() != 0) {
                        try {

                            String currencyFormat = "";
                            if (spacing) {
                                if (delimiter) {
                                    currencyFormat = currency + ". ";
                                } else {
                                    currencyFormat = currency + " ";
                                }
                            } else {
                                if (delimiter) {
                                    currencyFormat = currency + ".";
                                } else {
                                    currencyFormat = currency;
                                }
                            }

                            double parsed;
                            int parsedInt;
                            String formatted;

                            if (decimals) {
                                parsed = Double.parseDouble(cleanString);
                                formatted = NumberFormat.getCurrencyInstance().format((parsed / 100)).replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), currencyFormat);
                            } else {
                                parsedInt = Integer.parseInt(cleanString);
                                formatted = currencyFormat + NumberFormat.getNumberInstance(Locale.US).format(parsedInt);
                            }

                            current = formatted;

                            //if decimals are turned off and Separator is set as anything other than commas..
                            if (!separator.equals(",") && !decimals) {
                                //..replace the commas with the new separator
                                enterAmountText.setText(formatted.replaceAll(",", separator));
                                sendAmount = enterAmountText.getText().toString();
                                Log.e("amountentered",""+formatted);
                            } else {
                                //since no custom separators were set, proceed with comma separation
                                enterAmountText.setText(formatted);
                                sendAmount = enterAmountText.getText().toString();

                                Log.e("amountentered_1",""+formatted);
                            }
                            enterAmountText.setSelection(formatted.length());
                        } catch (NumberFormatException e) {
                            Log.e("CSCEnterAmntFragment",e.getLocalizedMessage());
                        }
                    }

                    enterAmountText.addTextChangedListener(this);
                }
            }
        });


        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = enterAmountText.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);



    }

    private void callConfirmTransactionFrag() {

        if (Validation.isValidEnterAmount(enterAmountText.getText().toString().trim())){
            Bundle bundle=new Bundle();
            bundle.putString(Constants.ENTERAMOUNT,sendAmount);
            Fragment fragment = new TransactionCnfrmFragment();
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            enterAmountText.setText("");
        }else{
            Toast.makeText(getContext(),getResources().getString(R.string.entervalidamount),Toast.LENGTH_LONG).show();
        }
    }
}