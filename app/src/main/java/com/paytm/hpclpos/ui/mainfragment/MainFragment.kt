package com.paytm.hpclpos.activities.mainsaleactivities

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.apphpcldb.entity.repository.AppRepository
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.constants.ToastMessages.customMsgToast
import com.paytm.hpclpos.constants.TransactionUtils.Companion.isTerminalRegistered
import com.paytm.hpclpos.databinding.FragmentMainBinding
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenRequest
import com.paytm.hpclpos.posterminal.base.BaseFragment
import com.paytm.hpclpos.viewmodel.MainActivityViewModel

class MainFragment : BaseFragment() {
    lateinit var binding: FragmentMainBinding
    private var saleCardview: CardView? = null
    private var transactionCardView: CardView? = null
    private var settingCardView: CardView? = null
    private var merchantServiceCardView: CardView? = null
    private val textToSpeech: TextToSpeech? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        saleCardview = binding.root.findViewById<View>(R.id.saleCardview) as CardView
        transactionCardView = binding.root.findViewById<View>(R.id.transactionCardView) as CardView
        settingCardView = binding.root.findViewById<View>(R.id.settingCardView) as CardView
        merchantServiceCardView = binding.root.findViewById<View>(R.id.merchantServiceCardView) as CardView
        sharedPreferencesData = SharedPreferencesData(context)
        GlobalMethods.initAndClear()
        GlobalMethods.setCardInfoEntity(null)
        getToken()
        viewFinds()
        hideKeypad()
        return binding.root
    }

    private fun navigateOperatorLoginFragment(type: String) {
        navigate2LoginFragment(type)
    }

    private fun callSettingDashboard() {
        navController!!.navigate(R.id.action_mainFragment_to_settingFragment)
    }

    private fun callMerchantServiceDashboard() {
        if (isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)) {
            navController!!.navigate(R.id.action_mainFragment_to_merchantServices)
        } else {
            customMsgToast(requireContext(), "Please Register the Terminal")
        }
    }

    override fun updateTimerUi(l: Long) {
        // Do nothing
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    override fun onResume() {
        super.onResume()
        countDownTimer.cancel()
    }

    fun checkLoginFlagAndNavigate() : Boolean {
        return GlobalMethods.getIsLoginOperator(requireContext())
    }

    private fun selectProductActivityCall() {
        navController!!.navigate(R.id.action_mainFragment_to_saleProductFragment)
    }

    private fun transactionActivityCall() {
        navController!!.navigate(R.id.action_mainFragment_to_transactions)
    }

    private fun navigate2LoginFragment(type: String) {
        val bundle = Bundle()
        bundle.putString(Constants.NAV_VALUE, type)
        navController!!.navigate(R.id.action_login_fragment, bundle)
    }

    private fun viewFinds() {
        saleCardview!!.setOnClickListener {
            if (isTerminalRegistered(
                    requireContext(),
                    Constants.REGISTRATION_STATUS
                )
            ) {
                if (checkLoginFlagAndNavigate()) {
                    selectProductActivityCall()
                } else {
                    checkForOperators()
                }
            } else {
                customMsgToast(
                    requireContext(),
                    getString(R.string.registration_alert_message)
                )
            }
        }
        transactionCardView!!.setOnClickListener {
            if (isTerminalRegistered(requireContext(), Constants.REGISTRATION_STATUS)) {
                if (checkLoginFlagAndNavigate()) {
                    transactionActivityCall()
                } else {
                    checkForOperators()
                }
            } else {
                customMsgToast(requireContext(), getString(R.string.registration_alert_message))
            }
        }
        settingCardView!!.setOnClickListener { callSettingDashboard() }
        merchantServiceCardView!!.setOnClickListener { callMerchantServiceDashboard() }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callExitDialog()
                }
            })
    }

    fun checkForOperators() {
        if (AppRepository(requireContext()).getAllOperators().isNullOrEmpty()) {
            customMsgToast(requireContext(),getString(R.string.please_add_operator))
        } else {
            navigateOperatorLoginFragment(Constants.LOGIN_SCREEN)
        }
    }
}