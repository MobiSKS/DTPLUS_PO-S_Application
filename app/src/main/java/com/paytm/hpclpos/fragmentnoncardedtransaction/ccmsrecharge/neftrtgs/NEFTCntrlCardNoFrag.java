package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.neftrtgs;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

import butterknife.ButterKnife;


public class NEFTCntrlCardNoFrag extends Fragment implements View.OnClickListener {

    EditText ctrlcardnoEdittext;
    LinearLayout gotoBack;

    public NEFTCntrlCardNoFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_n_e_f_t_cntrl_card_no, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        ButterKnife.bind(this,rootView);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);
        ctrlcardnoEdittext = rootView.findViewById(R.id.ctrlCardNo_editText);
        gotoBack = rootView.findViewById(R.id.gotoBack);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);


        ctrlcardnoEdittext.setOnTouchListener((v, event) -> {
            int inType = ctrlcardnoEdittext.getInputType(); // backup the input type
            ctrlcardnoEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            ctrlcardnoEdittext.onTouchEvent(event); // call native handler
            ctrlcardnoEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        ctrlcardnoEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        ctrlcardnoEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = ctrlcardnoEdittext.onCreateInputConnection(new EditorInfo());
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
                callRechargeAmntFrag();
                break;

            default:
                break;
        }
    }

    private void callRechargeAmntFrag() {

        String ctRLCardNo = ctrlcardnoEdittext.getText().toString();

        if(ctRLCardNo.trim().length()<1){
            Toast.makeText(getActivity(),"Please Enter Control Card No.",Toast.LENGTH_SHORT).show();


        }else if (!Validation.isvalidCTRLCardNO(ctRLCardNo.trim())) {
            Toast.makeText(getActivity(),"Please enter a valid Control Card No.",Toast.LENGTH_SHORT).show();

        } else {

            Fragment fragment = new NEFTRchrgAmntFrag();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            ctrlcardnoEdittext.setText("");
        }

    }
}