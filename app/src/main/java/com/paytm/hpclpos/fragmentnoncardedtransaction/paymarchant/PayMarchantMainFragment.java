package com.paytm.hpclpos.fragmentnoncardedtransaction.paymarchant;

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

public class PayMarchantMainFragment extends Fragment {

    private EditText paycodeEdittext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pay_marchant_main, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        paycodeEdittext = rootView.findViewById(R.id.paycode_EditText);
        ImageView gotoBack = rootView.findViewById(R.id.gotoBack);


        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> transactionPageCall());

        paycodeEdittext.setOnTouchListener((v, event) -> {
            int inType = paycodeEdittext.getInputType(); // backup the input type
            paycodeEdittext.setInputType(InputType.TYPE_NULL); // disable soft input
            paycodeEdittext.onTouchEvent(event); // call native handler
            paycodeEdittext.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        paycodeEdittext.setRawInputType(InputType.TYPE_CLASS_TEXT);
        paycodeEdittext.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = paycodeEdittext.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        return rootView;
    }

    private void transactionPageCall() {

        String payCode = paycodeEdittext.getText().toString();

        if (payCode.trim().length() < 1) {

            Toast.makeText(getActivity(), "Please Enter Paycode.", Toast.LENGTH_SHORT).show();

        } else if (!Validation.isvalidpaycode(payCode.trim())) {

            Toast.makeText(getActivity(), "Please enter a valid Paycode no.", Toast.LENGTH_SHORT).show();

        } else {

         /*   Fragment fragment = new TransactionSuccessFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            paycodeEdittext.setText("");*/
        }
    }
}