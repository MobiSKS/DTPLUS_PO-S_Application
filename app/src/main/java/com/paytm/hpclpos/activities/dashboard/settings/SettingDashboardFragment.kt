package com.paytm.hpclpos.activities.dashboard.settings

import com.paytm.hpclpos.constants.TransactionUtils.Companion.isTerminalRegistered
import com.paytm.hpclpos.constants.TransactionUtils.Companion.getTerminalPin
import com.paytm.hpclpos.constants.ToastMessages.customMsgToast
import com.paytm.hpclpos.constants.GlobalMethods.Companion.getServerIp
import com.paytm.hpclpos.posterminal.base.BaseFragment
import androidx.recyclerview.widget.RecyclerView
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.paytm.hpclpos.R
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.paytm.hpclpos.viewmodel.PerformRegistartion
import com.paytm.hpclpos.viewmodel.SettingDashboardViewModel
import com.paytm.hpclpos.livedatamodels.registrationapi.RegistrationResponse
import android.os.Looper
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.IntentFilter
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.databinding.ActivitySettingDashboardBinding
import java.util.*

class SettingDashboardFragment : BaseFragment(), View.OnClickListener, OnItemClick {
    private var rvMaintenance: RecyclerView? = null
    private var rvAdmin: RecyclerView? = null
    private var rvOthers: RecyclerView? = null
    private var settlementDialog: SettlementDialog? = null
    private var cardedAndMobileModelList: MutableList<CardedAndMobileModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBroadCastManager()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ActivitySettingDashboardBinding>(
            inflater,
            R.layout.activity_setting_dashboard,
            container,
            false)
        sharedPreferencesData = SharedPreferencesData(context)
        val gotoBack = binding.root.findViewById<ImageView>(R.id.gotoBack)
        rvAdmin = binding.root.findViewById(R.id.rvAdmin)
        rvMaintenance = binding.root.findViewById(R.id.rvMaintenance)
        rvOthers = binding.root.findViewById(R.id.rvOthers)
        gotoBack.setOnClickListener { v: View? -> gotoBackActivity() }
        maintenanceData()
        adminData()
        othersData()
        getToken()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    gotoBackActivity()
                }
            })
        return binding.root
    }

    private fun gotoBackActivity() {
        unRegisterBroadCastManager()
        navController?.popBackStack()
    }

    private fun maintenanceData() {
        val maintainanceAdapter: SettingMaintainanceAdapter
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Register",
                ContextCompat.getDrawable(requireContext(), R.drawable.register_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Edit Parameter",
                ContextCompat.getDrawable(requireContext(), R.drawable.editparameter_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Display Parameters",
                ContextCompat.getDrawable(requireContext(), R.drawable.about_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "About",
                ContextCompat.getDrawable(requireContext(), R.drawable.about_icon)
            )
        )
        rvMaintenance!!.layoutManager = GridLayoutManager(requireContext(), 4)
        maintainanceAdapter = SettingMaintainanceAdapter(
            requireContext(),
            cardedAndMobileModelList, requireActivity(), this
        )
        rvMaintenance!!.adapter = maintainanceAdapter
    }

    private fun adminData() {
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Key Exchange",
                ContextCompat.getDrawable(requireContext(), R.drawable.keyexchanege_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Clear Reversal",
                ContextCompat.getDrawable(requireContext(), R.drawable.clrreversal_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Reset Terminal",
                ContextCompat.getDrawable(requireContext(), R.drawable.clrreversal_icon)
            )
        )
        rvAdmin!!.layoutManager = GridLayoutManager(requireContext(), 4)
        val adminAdapter = SettingAdminAdapter(requireContext(), cardedAndMobileModelList)
        rvAdmin!!.adapter = adminAdapter
    }

    private fun othersData() {
        cardedAndMobileModelList = ArrayList()
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Wlan",
                ContextCompat.getDrawable(requireContext(), R.drawable.wlan_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Network",
                ContextCompat.getDrawable(requireContext(), R.drawable.network_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Bluetooth",
                ContextCompat.getDrawable(requireContext(), R.drawable.bluetooth_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Sound",
                ContextCompat.getDrawable(requireContext(), R.drawable.sound_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Home",
                ContextCompat.getDrawable(requireContext(), R.drawable.home_icon)
            )
        )
        cardedAndMobileModelList?.add(
            CardedAndMobileModel(
                "Quit",
                ContextCompat.getDrawable(requireContext(), R.drawable.quit_icon)
            )
        )
        rvOthers!!.layoutManager = GridLayoutManager(requireContext(), 4)
        val othersAdapter =
            SettingOthersAdapter(requireContext(), cardedAndMobileModelList, requireActivity())
        rvOthers!!.adapter = othersAdapter
    }

    override fun onClick(v: View) {
        if (v.id == R.id.gotoBack) {
            navController?.navigate(R.id.action_main_fragment)
        } else {
            //Do nothing
        }
    }

    override fun onBatchSettlement(position: Int) {
        when (position) {
            0 -> if (!isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)
                && getTerminalPin(requireContext(), Constants.TERMINAL_PIN) == "") {
                navController?.navigate(R.id.action_terminal_input_screen_fragment)
            } else if (!isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)) {
                callRegistrationApi()
            } else {
                customMsgToast(requireContext(), "Terminal Already Registered")
            }
            1 -> navController?.navigate(R.id.action_terminal_setting_fragment)
            2 -> if (isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)) {
                navController?.navigate(R.id.action_display_parameter_fragments)
            } else {
                customMsgToast(requireContext(), "Please Register the Terminal")
            }
            3 -> navController?.navigate(R.id.action_about_fragment)
            else -> {}
        }
    }

    private fun performRegistration() {
        val performRegistartion =
            PerformRegistartion(requireContext()).constructRegistrationRequest()
        val viewModel = ViewModelProvider(this).get(
            SettingDashboardViewModel::class.java
        )
        viewModel.makeApiRegistration(performRegistartion)
        viewModel.getliveRegistration()
            .observe(this) { registrationResponse: RegistrationResponse? ->
                if (registrationResponse != null) {
                    if (registrationResponse.Success) {
                        if (registrationResponse.Internel_Status_Code == Constants.STATUS_SUCCESS) {
                            val data = registrationResponse.Data
                            settlementDialog!!.onSuccess()
                            Handler(Looper.getMainLooper()).postDelayed(
                                { settlementDialog!!.dismiss() },
                                2000
                            )
                            viewModel.storeRegistrationDataIntoDb(data, requireContext())
                            viewModel.setAlarmForSettlement(requireActivity())
                        } else {
                            requireActivity().runOnUiThread {
                                settlementDialog!!.onFailure(registrationResponse.Message)
                                Handler(Looper.getMainLooper()).postDelayed(
                                    { settlementDialog!!.dismiss() },
                                    2000
                                )
                            }
                        }
                    } else {
                        if (registrationResponse.Internel_Status_Code == Constants.STATUS_TOKEN_EXPIRED) {
                            settlementDialog!!.onFailure(registrationResponse.Message)
                            Handler(Looper.getMainLooper()).postDelayed(
                                { settlementDialog!!.dismiss() },
                                2000
                            )
                        }
                    }
                } else {
                    settlementDialog!!.onFailure("Please check your Internet Connection")
                    Handler(Looper.getMainLooper()).postDelayed(
                        { settlementDialog!!.dismiss() },
                        2000
                    )
                }
            }
    }

    private fun showDialog() {
        settlementDialog = SettlementDialog(requireActivity())
        settlementDialog!!.setTitle(getString(R.string.title_registration))
        settlementDialog!!.setVisibilityForSettlementStatus()
        settlementDialog!!.setProcessing()
        settlementDialog!!.settimerAndEndpoint(getServerIp(requireContext()) + ".....")
        settlementDialog!!.setpacketStatus("Recieving.....")
        settlementDialog!!.show()
    }

    override fun updateTimerUi(l: Long) {
        //
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume Called")
    }

    var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val message = intent.getStringExtra("message")
            Log.d("receiver", "Got message: $message")
            callRegistrationApi()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterBroadCastManager()
    }

    fun unRegisterBroadCastManager() {
        Log.d(tag, "Local Broadcast Manager UnRegistered")
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver)
    }

    fun registerBroadCastManager() {
        Log.d(tag, "Local Broadcast Manager Registered")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            mMessageReceiver,
            IntentFilter("registration")
        )
    }

    companion object {
        private const val TAG = "SettingDashboardFragmnt"
    }

    fun callRegistrationApi() {
        val tokenId = sharedPreferencesData.getSharedPreferenceData(Constants.TOKENIDPREF, Constants.TOKENID).toString()
        if (tokenId.isBlank()) {
            getToken()
        } else {
            showDialog()
            performRegistration()
        }
    }
}