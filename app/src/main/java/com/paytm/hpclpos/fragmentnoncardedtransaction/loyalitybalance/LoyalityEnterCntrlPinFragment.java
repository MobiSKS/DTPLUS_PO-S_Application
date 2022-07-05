package com.paytm.hpclpos.fragmentnoncardedtransaction.loyalitybalance;

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

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoyalityEnterCntrlPinFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.cntRLPin_editText)
    EditText cntrlpinEdittext;
    @BindView(R.id.gotoBack)
    ImageView gotoBack;

    public LoyalityEnterCntrlPinFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_loyality_enter_cntrl_pin, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        ButterKnife.bind(this,rootView);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);
        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);


        cntrlpinEdittext.setOnTouchListener((v, event) -> {
            int inType = cntrlpinEdittext.getInputType(); // backup the input type
            cntrlpinEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            cntrlpinEdittext.onTouchEvent(event); // call native handler
            cntrlpinEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cntrlpinEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cntrlpinEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cntrlpinEdittext.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotoBack:
               requireActivity().onBackPressed();
               break;

            case R.id.llayout_done:
                callDisplayBalanceFragment();
                break;
            default:
                break;
        }
    }

    private void callDisplayBalanceFragment() {

        String ctRLPin = cntrlpinEdittext.getText().toString();

         if (!Validation.isvalidControlPin(ctRLPin.trim())) {

            Toast.makeText(getContext(),"Please Enter a valid Control Pin.",Toast.LENGTH_SHORT).show();


        } else {

            Fragment fragment = new DisplayLoyaltyBalanceFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            cntrlpinEdittext.setText("");
        }

    }
}