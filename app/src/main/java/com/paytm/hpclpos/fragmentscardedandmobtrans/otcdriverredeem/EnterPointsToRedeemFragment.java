package com.paytm.hpclpos.fragmentscardedandmobtrans.otcdriverredeem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

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


public class EnterPointsToRedeemFragment extends Fragment {

    @BindView(R.id.cardPin_editText)
    EditText cardPinEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_points_to_redeem, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFinds(view);
    }

    private void viewFinds(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);
        EnterMobileNoKeyboard mobNokeyboard = view.findViewById(R.id.mobileNokeyboard);
        LinearLayout llayoutDone = mobNokeyboard.findViewById(R.id.llayout_done);
        llayoutDone.setOnClickListener(v -> callSelectFuelFragment());

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
    }

    private void callSelectFuelFragment() {
        if (Validation.isValidRedeemPoints(cardPinEditText.getText().toString())) {
            Fragment fragment = new ConfirmPointNdAmntFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.entervalidredeempoints), Toast.LENGTH_LONG).show();
        }
    }
}