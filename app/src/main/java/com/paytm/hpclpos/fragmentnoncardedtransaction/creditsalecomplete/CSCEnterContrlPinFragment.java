package com.paytm.hpclpos.fragmentnoncardedtransaction.creditsalecomplete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CSCEnterContrlPinFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.ctrl_PIN_editText)
    EditText ctrlPinEdittext;
    @BindView(R.id.gotoBack)
    ImageView gotoBack;


    public CSCEnterContrlPinFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_c_s_c_enter_contrl_pin, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        ButterKnife.bind(this,rootView);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);


        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);
        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);


        ctrlPinEdittext.setOnTouchListener((v, event) -> {
            int inType = ctrlPinEdittext.getInputType(); // backup the input type
            ctrlPinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            ctrlPinEdittext.onTouchEvent(event); // call native handler
            ctrlPinEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        ctrlPinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        ctrlPinEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = ctrlPinEdittext.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;

            case R.id.llayout_done:
                callDisplayBalanceFragment();
                break;
            default:
                break;
        }
    }

    private void callDisplayBalanceFragment() {

        String cntrlPin = ctrlPinEdittext.getText().toString();

        if(cntrlPin.trim().length()<1){

            Toast.makeText(getActivity(),"Please Enter Control Pin.",Toast.LENGTH_SHORT).show();


        }else if (!Validation.isvalidControlPin(cntrlPin.trim())) {

            Toast.makeText(getActivity(),"Please enter a valid Control Pin.",Toast.LENGTH_SHORT).show();


        } else {

            Fragment fragment = new CSCEnterAmntFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            ctrlPinEdittext.setText("");
        }
    }
}