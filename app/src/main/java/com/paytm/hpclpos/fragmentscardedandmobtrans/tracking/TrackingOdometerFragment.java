package com.paytm.hpclpos.fragmentscardedandmobtrans.tracking;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrackingOdometerFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.gotoBack)
    LinearLayout gotoBack;

    @BindView(R.id.lLskip)
    LinearLayout lLskip;
    @BindView(R.id.odometer_EditText)
    EditText odometerEditText;
    @BindView(R.id.mobileNokeyboard)
    EnterMobileNoKeyboard mobNokeyboard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OnCreate", ":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_tracking_odometer, container, false);
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
        gotoBack.setOnClickListener(this);
        lLskip.setOnClickListener(this);

        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);
        layoutDone.setOnClickListener(v -> {
        });



        lLskip.setOnClickListener(v -> {
        });

        odometerEditText.setOnTouchListener((v, event) -> {
            int inType = odometerEditText.getInputType(); // backup the input type
            odometerEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            odometerEditText.onTouchEvent(event); // call native handler
            odometerEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        odometerEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        odometerEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = odometerEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }
    }
}