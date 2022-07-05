package com.paytm.hpclpos.activities.dashboard.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel;
import com.paytm.hpclpos.activities.dialogs.SettlementDialog;
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.constants.OnItemClick;
import com.paytm.hpclpos.constants.ToastMessages;
import com.paytm.hpclpos.constants.TransactionUtils;
import com.paytm.hpclpos.livedatamodels.registrationapi.Data;
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationRequest;
import com.paytm.hpclpos.posterminal.base.BaseFragment;
import com.paytm.hpclpos.viewmodel.PerformRegistartion;
import com.paytm.hpclpos.viewmodel.SettingDashboardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingDashboardFragment extends BaseFragment implements View.OnClickListener, OnItemClick {

    private RecyclerView rvMaintenance;
    private RecyclerView rvAdmin;
    private RecyclerView rvOthers;
    private SettlementDialog settlementDialog;

    private List<CardedAndMobileModel> cardedAndMobileModelList;

    private static final String TAG = "SettingDashboardFragmnt";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadCastManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.paytm.hpclpos.databinding.ActivitySettingDashboardBinding binding = DataBindingUtil.inflate(inflater, R.layout.activity_setting_dashboard, container, false);
        ImageView gotoBack = binding.getRoot().findViewById(R.id.gotoBack);
        rvAdmin = binding.getRoot().findViewById(R.id.rvAdmin);
        rvMaintenance = binding.getRoot().findViewById(R.id.rvMaintenance);
        rvOthers = binding.getRoot().findViewById(R.id.rvOthers);

        gotoBack.setOnClickListener(v -> gotoBackActivity());
        maintenanceData();
        adminData();
        othersData();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() { gotoBackActivity(); }
        });
        return binding.getRoot();
    }

    private void gotoBackActivity() {
        unRegisterBroadCastManager();
        Objects.requireNonNull(getNavController()).popBackStack();
    }

    private void maintenanceData() {
        SettingMaintainanceAdapter maintainanceAdapter;
        cardedAndMobileModelList = new ArrayList<>();
        cardedAndMobileModelList.add(new CardedAndMobileModel("Register", ContextCompat.getDrawable(requireContext(), R.drawable.register_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Edit Parameter", ContextCompat.getDrawable(requireContext(),R.drawable.editparameter_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Display Parameters", ContextCompat.getDrawable(requireContext(),R.drawable.about_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("About", ContextCompat.getDrawable(requireContext(),R.drawable.about_icon)));

        rvMaintenance.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        maintainanceAdapter = new SettingMaintainanceAdapter(requireContext(),
                cardedAndMobileModelList, requireActivity(),this);
        rvMaintenance.setAdapter(maintainanceAdapter);
    }

    private void adminData() {
        cardedAndMobileModelList = new ArrayList<>();
        cardedAndMobileModelList.add(new CardedAndMobileModel("Key Exchange", ContextCompat.getDrawable(requireContext(),R.drawable.keyexchanege_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Clear Reversal", ContextCompat.getDrawable(requireContext(),R.drawable.clrreversal_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Reset Terminal", ContextCompat.getDrawable(requireContext(),R.drawable.clrreversal_icon)));

        rvAdmin.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        SettingAdminAdapter adminAdapter = new SettingAdminAdapter(requireContext(), cardedAndMobileModelList);
        rvAdmin.setAdapter(adminAdapter);
    }

    private void othersData() {
        cardedAndMobileModelList = new ArrayList<>();
        cardedAndMobileModelList.add(new CardedAndMobileModel("Wlan", ContextCompat.getDrawable(requireContext(),R.drawable.wlan_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Network", ContextCompat.getDrawable(requireContext(),R.drawable.network_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Bluetooth", ContextCompat.getDrawable(requireContext(),R.drawable.bluetooth_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Sound", ContextCompat.getDrawable(requireContext(),R.drawable.sound_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Home", ContextCompat.getDrawable(requireContext(),R.drawable.home_icon)));
        cardedAndMobileModelList.add(new CardedAndMobileModel("Quit", ContextCompat.getDrawable(requireContext(),R.drawable.quit_icon)));


        rvOthers.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        SettingOthersAdapter othersAdapter = new SettingOthersAdapter(requireContext(), cardedAndMobileModelList, requireActivity());
        rvOthers.setAdapter(othersAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.gotoBack) {
            Objects.requireNonNull(getNavController()).navigate(R.id.action_main_fragment);
        } else {
            //Do nothing
        }
    }

    @Override
    public void onBatchSettlement(int position) {
        switch (position) {
            case 0:
                if (!TransactionUtils.Companion.isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)
                        && (TransactionUtils.Companion.getTerminalPin(requireContext(),Constants.TERMINAL_PIN).equals(""))) {
                    Objects.requireNonNull(getNavController()).navigate(R.id.action_terminal_input_screen_fragment);
                } else if(!TransactionUtils.Companion.isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)) {
                    showDialog();
                    performRegistration();
                } else {
                    ToastMessages.customMsgToast(requireContext(), "Terminal Already Registered");
                }
                break;
            case 1:
                Objects.requireNonNull(getNavController()).navigate(R.id.action_terminal_setting_fragment);
                break;
                //
            case 2:
                if (TransactionUtils.Companion.isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)) {
                    Objects.requireNonNull(getNavController()).navigate(R.id.action_display_parameter_fragments);
                } else {
                    ToastMessages.customMsgToast(requireContext(), "Please Register the Terminal");
                }
                break;
            case 3:
                Objects.requireNonNull(getNavController()).navigate(R.id.action_about_fragment);
            default:
                break;
        }
    }

    private void performRegistration() {
        RegistrationRequest performRegistartion = new PerformRegistartion(requireContext()).constructRegistrationRequest();
        SettingDashboardViewModel viewModel = new ViewModelProvider(this).get(SettingDashboardViewModel.class);
        viewModel.makeApiRegistration(performRegistartion);
        viewModel.getliveRegistration().observe(this, registrationResponse -> {
            if (registrationResponse != null) {
                if (registrationResponse.getSuccess()) {
                    if (registrationResponse.getInternel_Status_Code() == (Constants.STATUS_SUCCESS)) {
                        Data data = registrationResponse.getData();
                        settlementDialog.onSuccess();
                        new Handler(Looper.getMainLooper()).postDelayed(() -> settlementDialog.dismiss(), 2000);
                        viewModel.storeRegistrationDataIntoDb(data, requireContext());
                        viewModel.setAlarmForSettlement(requireActivity());
                    } else {
                        requireActivity().runOnUiThread(() -> {
                            settlementDialog.onFailure(registrationResponse.getMessage());
                            new Handler(Looper.getMainLooper()).postDelayed(() -> settlementDialog.dismiss(), 2000);
                        });
                    }
                } else {
                    if (registrationResponse.getInternel_Status_Code() == (Constants.STATUS_TOKEN_EXPIRED)) {
                        settlementDialog.onFailure(registrationResponse.getMessage());
                        new Handler(Looper.getMainLooper()).postDelayed(() -> settlementDialog.dismiss(), 2000);
                    }
                }
            } else {
                settlementDialog.onFailure("Please check your Internet Connection");
                new Handler(Looper.getMainLooper()).postDelayed(() -> settlementDialog.dismiss(), 2000);
            }
        });
    }

    private void showDialog() {
        settlementDialog = new SettlementDialog(requireActivity());
        settlementDialog.setTitle(getString(R.string.title_registration));
        settlementDialog.setVisibilityForSettlementStatus();
        settlementDialog.setProcessing();
        settlementDialog.settimerAndEndpoint(GlobalMethods.Companion.getServerIp(requireContext())+".....");
        settlementDialog.setpacketStatus("Recieving.....");
        settlementDialog.show();
    }

    @Override
    public void updateTimerUi(long l) {
       //
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"OnResume Called");
    }

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            showDialog();
            performRegistration();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadCastManager();
    }

    public void unRegisterBroadCastManager() {
        Log.d(getTag(),"Local Broadcast Manager UnRegistered");
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver);
    }

    public void registerBroadCastManager() {
        Log.d(getTag(),"Local Broadcast Manager Registered");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("registration"));
    }
}