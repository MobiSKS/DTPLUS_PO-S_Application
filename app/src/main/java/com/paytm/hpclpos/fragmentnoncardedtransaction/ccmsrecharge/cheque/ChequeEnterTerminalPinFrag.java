package com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cheque;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.paytm.hpclpos.ui.transactionsuccess.TransactionSucessActivity;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.EnterMobileNoKeyboard;
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.constants.NetworkUtil;
import com.paytm.hpclpos.constants.SharedPreferencesData;
import com.paytm.hpclpos.constants.Validation;
import com.paytm.hpclpos.enums.SaleTransactionDetails;
import com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount.RechargeCcmsAccountRequest;
import com.paytm.hpclpos.viewmodel.MainActivityViewModel;

import butterknife.ButterKnife;

public class ChequeEnterTerminalPinFrag extends Fragment implements View.OnClickListener {

    EditText termnlPinEditText;
    LinearLayout gotoBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate",":: Called");
    }

    void callTransactionSuccess(String transDate, String transAmount, String product) {
        Intent intent = new Intent(requireContext(), TransactionSucessActivity.class);
        intent.putExtra(Constants.TRANSID, "1");
        intent.putExtra(Constants.TRANSDATE, transDate);
        intent.putExtra(Constants.TRANSAMOUNT, transAmount);
        intent.putExtra(Constants.TRANSPRODUCT, product);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_cheque_enter_terminal_pin, container, false);
        gotoBack = rootView.findViewById(R.id.gotoBack);
        termnlPinEditText = rootView.findViewById(R.id.termnlPin_editText);
        ButterKnife.bind(this, rootView);
        EnterMobileNoKeyboard mobNokeyboard = rootView.findViewById(R.id.mobileNokeyboard);
        LinearLayout layoutDone = mobNokeyboard.findViewById(R.id.llayout_done);

        layoutDone.setOnClickListener(this);
        gotoBack.setOnClickListener(this);

        termnlPinEditText.setOnTouchListener((v, event) -> {
            int inType = termnlPinEditText.getInputType(); // backup the input type
            termnlPinEditText.setInputType(InputType.TYPE_NULL); // disable soft input
            termnlPinEditText.onTouchEvent(event); // call native handler
            termnlPinEditText.setInputType(inType); // restore input type
            return true; // consume touch even
        });

        // prevent system keyboard from appearing when EditText is tapped
        termnlPinEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        termnlPinEditText.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = termnlPinEditText.onCreateInputConnection(new EditorInfo());
        mobNokeyboard.setInputConnection(ic);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoBack:
                getActivity().onBackPressed();
                break;

            case R.id.llayout_done:
                callTransactionSlip();
                break;

            default:
                break;
        }
    }

    private void callTransactionSlip() {
        String terminalPin = termnlPinEditText.getText().toString();

        if (terminalPin.trim().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter Terminal Pin.", Toast.LENGTH_SHORT).show();

        } else if (!Validation.isvalidTerminalPin(terminalPin.trim())) {
            Toast.makeText(getActivity(), "Please enter a valid Terminal Pin.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Terminal would print the Transaction Slip", Toast.LENGTH_SHORT).show();
            termnlPinEditText.setText("");
            SharedPreferencesData sharedPreferencesData = new SharedPreferencesData(requireContext());
            String batchId =
                    sharedPreferencesData.getSharedPreferenceData(Constants.PREFCONFIG, Constants.BATCHID);
            //call api
            if (NetworkUtil.checkNetworkStatus(getContext())) {
                RechargeCcmsAccountRequest rechargeCcmsAccountRequest = new RechargeCcmsAccountRequest(
                        "string",
                        Constants.ANDROIDAGENT,
                        GlobalMethods.Companion.getDeviceId(getContext()),
                        String.valueOf(GlobalMethods.Companion.getTestMerchant_Id()),
                        String.valueOf(GlobalMethods.Companion.getTerminalId(requireContext())),
                        "1142419208929028",
                        Integer.parseInt(batchId),
                        Integer.parseInt(GlobalMethods.Companion.getAmount().replace(".", "").replace(",", "")),
                        String.valueOf(SaleTransactionDetails.CARDSALE.getSaleType()),
                        GlobalMethods.Companion.getTransactionId(getActivity().getApplicationContext()),
                        "2022-02-10 16:02:47.170",
                        GlobalMethods.Companion.getChequeNo(),
                        GlobalMethods.Companion.getMicrCode(),
                        "",
                        "1",
                        "INR",
                        "",
                        "",
                        "1234",
                        "Terminal",
                        "123456");
                MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
                viewModel.makeApiCcmsRecharge(rechargeCcmsAccountRequest);
                viewModel.getliveDataCcmsRecharge().observe(this, rechargeCcmsAccountResponse -> callTransactionSuccess(rechargeCcmsAccountRequest.getTransdate(),
                        String.valueOf(rechargeCcmsAccountRequest.getAmount()), rechargeCcmsAccountRequest.getTransid()));

            }
        }
    }
}