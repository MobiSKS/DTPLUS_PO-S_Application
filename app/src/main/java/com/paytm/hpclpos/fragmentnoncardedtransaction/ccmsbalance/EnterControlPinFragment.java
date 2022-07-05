package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsbalance;

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


public class EnterControlPinFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.cntRL_Pin_editText)
    EditText cntrlPinEdittext;
    @BindView(R.id.gotoBack)
    ImageView gotoBack;


    public EnterControlPinFragment() {
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

        final View rootView = inflater.inflate(R.layout.fragment_enter_control_pin, container, false);
        ButterKnife.bind(this,rootView);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);
        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);

        cntrlPinEdittext.setOnTouchListener((v, event) -> {
            int inType = cntrlPinEdittext.getInputType(); // backup the input type
            cntrlPinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            cntrlPinEdittext.onTouchEvent(event); // call native handler
            cntrlPinEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cntrlPinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cntrlPinEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cntrlPinEdittext.onCreateInputConnection(new EditorInfo());
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

        String cntRLPin = cntrlPinEdittext.getText().toString();

        if (!Validation.isvalidControlPin(cntRLPin.trim())) {

            Toast.makeText(getContext(),"Please enter a valid Control Pin",Toast.LENGTH_SHORT).show();


        } else {

            Fragment fragment = new DisplayBalanceFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            cntrlPinEdittext.setText("");
        }

    }
}