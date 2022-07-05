package com.paytm.hpclpos.fragmentnoncardedtransaction.paybaack.paybackvoid;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PaybackRocNoFragment extends Fragment {

    @BindView(R.id.rocNo_EditText)
    EditText rocNoEditText;
    @BindView(R.id.reEnterrocNo_EditText)
    EditText reEnterrocNoEditText;
    private Dialog dialog;
    private Dialog cnfrmAmntdialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payback_roc_no, container, false);
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

        LinearLayout gotoBack = view.findViewById(R.id.gotoBack);

        EnterMobileNoKeyboard mobNokeyboard = view.findViewById(R.id.keyboard);


        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> enterAmountCall());


        gotoBack.setOnClickListener(v -> getActivity().onBackPressed());


        enterRoc(mobNokeyboard);


    }

    private void enterRoc(EnterMobileNoKeyboard mobNokeyboard) {
        rocNoEditText.setOnTouchListener((v, event) -> {


            int inType = rocNoEditText.getInputType(); // backup the input type
            rocNoEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            rocNoEditText.onTouchEvent(event); // call native handler
            rocNoEditText.setInputType(inType); // restore input type
            // prevent system keyboard from appearing when EditText is tapped
            rocNoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            rocNoEditText.setTextIsSelectable(true);


            // pass the InputConnection from the EditText to the keyboard
            InputConnection ic = rocNoEditText.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(ic);
            return true; // consume touch even


        });

        reEnterrocNoEditText.setOnTouchListener((v, event) -> {


            int inType = reEnterrocNoEditText.getInputType(); // backup the input type
            reEnterrocNoEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            reEnterrocNoEditText.onTouchEvent(event); // call native handler
            reEnterrocNoEditText.setInputType(inType); // restore input type
            // prevent system keyboard from appearing when EditText is tapped
            reEnterrocNoEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            reEnterrocNoEditText.setTextIsSelectable(true);


            // pass the InputConnection from the EditText to the keyboard
            InputConnection ic = reEnterrocNoEditText.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(ic);
            return true; // consume touch even


        });
    }


    private void enterAmountCall() {
        if (rocNoEditText.getText().toString().equalsIgnoreCase(reEnterrocNoEditText.getText().toString())) {
            if (Validation.isValidRocNo(rocNoEditText.getText().toString())) {
                dialogCnfrmAmnt();
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.entervalirocno), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.mismatchrocno), Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogCnfrmAmnt() {
        cnfrmAmntdialog = new Dialog(getContext());
        cnfrmAmntdialog.setContentView(R.layout.dialog_cnfrm_amnt);
        Window window = cnfrmAmntdialog.getWindow();
        cnfrmAmntdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvCancel = cnfrmAmntdialog.findViewById(R.id.tvCancel);
        TextView tvOk = cnfrmAmntdialog.findViewById(R.id.tvOk);



        tvCancel.setOnClickListener(v -> cnfrmAmntdialog.cancel());
        tvOk.setOnClickListener(v -> {
            cnfrmAmntdialog.dismiss();
            openTrminalpinDilog();
        });
        cnfrmAmntdialog.show();
    }


    private void openTrminalpinDilog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_terminal_pin);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvOk = dialog.findViewById(R.id.tvOk);
        EditText etTerminalPin = dialog.findViewById(R.id.etTerminalPin);

        EnterMobileNoKeyboard mobNokeyboard = dialog.findViewById(R.id.mobileNokeyboard);
        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);


        layoutDone.setOnClickListener(v -> dialogCallPaymentSuccess(etTerminalPin));
        etTerminalPin.setOnTouchListener((v, event) -> {

            etTerminalPin.setCursorVisible(true);

            int inType = etTerminalPin.getInputType(); // backup the input type
            etTerminalPin.setInputType(InputType.TYPE_NULL); // disable soft input
            etTerminalPin.onTouchEvent(event); // call native handler
            etTerminalPin.setInputType(inType); // restore input type
            etTerminalPin.setRawInputType(InputType.TYPE_CLASS_TEXT);
            etTerminalPin.setTextIsSelectable(true);

            // pass the InputConnection from the EditText to the keyboard
            InputConnection ic = etTerminalPin.onCreateInputConnection(new EditorInfo());
            mobNokeyboard.setInputConnection(ic);
            return true; // consume touch even
        });

        tvCancel.setOnClickListener(v -> dialog.cancel());
        tvOk.setOnClickListener(v -> dialogCallPaymentSuccess(etTerminalPin));
        dialog.show();
    }

    private void dialogCallPaymentSuccess(EditText etTerminalPin) {
        if (Validation.isvalidTerminalPin(etTerminalPin.getText().toString())) {
            Fragment fragment = new PaybackSuccessFragment();
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            if (dialog != null) {
                dialog.cancel();
            }

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.enterterminalpin), Toast.LENGTH_LONG).show();
        }
    }
}