package com.paytm.hpclpos.fragmentnoncardedtransaction.creditsalecomplete;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;


public class CSCMainFragment extends Fragment implements View.OnClickListener {

 private EditText cardNoEditText;

    public CSCMainFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_c_s_c_main, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        cardNoEditText = rootView.findViewById(R.id.cardNo_EditText);

        ImageView gotoBack= rootView.findViewById( R.id.gotoBack);
        gotoBack.setOnClickListener(this);

        LinearLayout lLNext= rootView.findViewById( R.id.lLNext);
        LinearLayout lLCancl= rootView.findViewById( R.id.lLCancl);

        lLNext.setOnClickListener(this);
        lLCancl.setOnClickListener(this);


        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);
        layoutDone.setOnClickListener(this);

        cardNoEditText.setOnTouchListener((v, event) -> {
            int inType = cardNoEditText.getInputType(); // backup the input type
            cardNoEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            cardNoEditText.onTouchEvent(event); // call native handler
            cardNoEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cardNoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cardNoEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cardNoEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llayout_done:
                callEnterControlPinFrag();
                break;
            case R.id.lLNext:
                callEnterControlPinFrag();
                break;

            case R.id.lLCancl:
                getActivity().onBackPressed();
                break;

            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void callEnterControlPinFrag() {

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(Constants.MYPREF, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String cardNo= cardNoEditText.getText().toString();

        if(cardNo.trim().length()<1){

            Toast.makeText(getActivity(),"Please Enter Card No.",Toast.LENGTH_SHORT).show();

        }else if (!Validation.isvalidCTRLCardNO(cardNo.trim())) {

            Toast.makeText(getActivity(),"Please Enter a Valid Card No.",Toast.LENGTH_SHORT).show();

        } else {

            editor.putString(Constants.CONTROLCARDNO, cardNo);
            editor.commit();

            Fragment fragment = new CSCEnterContrlPinFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            cardNoEditText.setText("");
        }

    }
}