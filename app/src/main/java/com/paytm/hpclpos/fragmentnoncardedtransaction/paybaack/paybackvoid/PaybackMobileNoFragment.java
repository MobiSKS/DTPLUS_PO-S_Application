package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackvoid;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;
import com.paytm.hpclpos.ui.EnterRocNumber.EnterRocNoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaybackMobileNoFragment extends Fragment {

    @BindView(R.id.mobno_editText)
    EditText mobnoEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payback_mobile_no, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFinds(view);
    }
    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);
        LinearLayout gotoBack= view.findViewById( R.id.gotoBack);
        EnterMobileNoKeyboard mobNokeyboard = view.findViewById(R.id.mobileNokeyboard);

        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());

        LinearLayout layoutDone=mobNokeyboard.findViewById(R.id.llayout_done);
        layoutDone.setOnClickListener(v -> enterAmntActivityCall());

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
    }
    private void enterAmntActivityCall(){
        if (Validation.isValidMobileNo(mobnoEditText.getText().toString())){
            Fragment fragment = new EnterRocNoFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        } else {
            Toast.makeText(getContext(),getResources().getString(R.string.entervalidmono), Toast.LENGTH_LONG).show();
        }
    }
}