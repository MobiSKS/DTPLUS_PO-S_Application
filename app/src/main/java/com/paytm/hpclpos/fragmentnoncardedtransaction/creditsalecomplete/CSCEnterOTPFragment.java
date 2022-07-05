package com.paytm.hpclpos.fragmentnoncardedtransaction.creditsalecomplete;

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

import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;

public class CSCEnterOTPFragment extends Fragment {



    public CSCEnterOTPFragment() {
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
        final View rootView = inflater.inflate(R.layout.fragment_c_s_c_enter_o_t_p, container, false);

        rootView.setOnTouchListener((view, motionEvent) -> true);

        EditText cardPinEditText = rootView.findViewById(R.id.cardPin_editText);
        ImageView gotoBack= rootView.findViewById( R.id.gotoBack);

        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> callTransactionStatus());

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        cardPinEditText.setOnTouchListener((v, event) -> {
            int inType = cardPinEditText.getInputType(); // backup the input type
            cardPinEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            cardPinEditText.onTouchEvent(event); // call native handler
            cardPinEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        cardPinEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        cardPinEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = cardPinEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);

        return rootView;
    }

    private void callTransactionStatus(){
       /* Fragment fragment = new TransactionSuccessFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();*/
    }
}