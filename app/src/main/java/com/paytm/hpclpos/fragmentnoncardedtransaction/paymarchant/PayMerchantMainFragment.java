package com.paytm.hpclpos.fragmentnoncardedtransaction.paymarchant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Validation;
import com.paytm.hpclpos.databinding.FragmentPayMarchantMainBinding;

public class PayMerchantMainFragment extends Fragment {

    private FragmentPayMarchantMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_marchant_main, container, false);
        return binding.getRoot();
    }

    private void transactionPageCall() {
        String payCode = binding.paycodeEditText.getText().toString();
        if (payCode.trim().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter Paycode.", Toast.LENGTH_SHORT).show();
        } else if (!Validation.isvalidpaycode(payCode.trim())) {
            Toast.makeText(getActivity(), "Please enter a valid Paycode no.", Toast.LENGTH_SHORT).show();
        } else {

        }
    }
}