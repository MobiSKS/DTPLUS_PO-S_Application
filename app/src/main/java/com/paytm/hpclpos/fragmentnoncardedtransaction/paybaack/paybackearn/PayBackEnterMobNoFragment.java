package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackearn;

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

public class PayBackEnterMobNoFragment extends Fragment {

    private EditText mobnoEditText;

    public PayBackEnterMobNoFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_pay_back_enter_mob_no, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

         mobnoEditText = rootView.findViewById(R.id.mobno_editText);
        ImageView gotoBack= rootView.findViewById( R.id.gotoBack);



        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> enterAmntFragCall());

        mobnoEditText.setOnTouchListener((v, event) -> {
            int inType = mobnoEditText.getInputType(); // backup the input type
            mobnoEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            mobnoEditText.onTouchEvent(event); // call native handler
            mobnoEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        mobnoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mobnoEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = mobnoEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        return rootView;
    }

    private void enterAmntFragCall() {

        String mobNo = mobnoEditText.getText().toString();

        if(mobNo.trim().length()<1){

            Toast.makeText(getActivity(),"Please Enter Mobile No.",Toast.LENGTH_SHORT).show();

        }else if (!Validation.isvalidMobNo(mobNo.trim())) {
            Toast.makeText(getActivity(),"Please enter a valid Mobile No.",Toast.LENGTH_SHORT).show();

        } else {

            Fragment fragment = new PayBackEnterAmountFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            mobnoEditText.setText("");
        }

    }
}