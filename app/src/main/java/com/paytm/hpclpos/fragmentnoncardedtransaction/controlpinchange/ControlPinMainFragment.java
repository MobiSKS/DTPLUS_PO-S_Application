package com.paytm.hpclpos.fragmentnoncardedtransaction.controlpinchange;

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
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

public class ControlPinMainFragment extends Fragment implements View.OnClickListener {

  private EditText cardnoEdittext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_control_pin_main, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

         cardnoEdittext = rootView.findViewById(R.id.cardNo_EditText);

        ImageView gotoBack= rootView.findViewById( R.id.gotoBack);
        gotoBack.setOnClickListener(this);

        LinearLayout lLNext=  rootView.findViewById( R.id.lLNext);
        LinearLayout lLCancel= rootView.findViewById( R.id.lLCancel);

        lLNext.setOnClickListener(this);
        lLCancel.setOnClickListener(this);


        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);
        layoutDone.setOnClickListener(this);

        cardnoEdittext.setOnTouchListener((v, event) -> {
            int inType = cardnoEdittext.getInputType(); // backup the input type
            cardnoEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            cardnoEdittext.onTouchEvent(event); // call native handler
            cardnoEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cardnoEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cardnoEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cardnoEdittext.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llayout_done:
                callChangeTerminalPinFrag();
                break;
            case R.id.lLNext:
                callChangeTerminalPinFrag();
                break;

            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;
            case R.id.lLCancel:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void callChangeTerminalPinFrag() {

        String ctRLCardNo = cardnoEdittext.getText().toString();

        if(ctRLCardNo.trim().length()<1){

            Toast.makeText(getActivity(),"Please Enter Control Card No.",Toast.LENGTH_SHORT).show();

        }else if (!Validation.isvalidCTRLCardNO(ctRLCardNo.trim())) {

            Toast.makeText(getActivity(),"Please enter a valid Control Card No.",Toast.LENGTH_SHORT).show();

        } else {

            Fragment fragment = new ChangeTerminalPinFrag();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            cardnoEdittext.setText("");
        }

    }
}