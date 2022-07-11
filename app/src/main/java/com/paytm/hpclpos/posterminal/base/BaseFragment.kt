package com.paytm.hpclpos.posterminal.base

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.paytm.hpclpos.Dialog.DialogUtil
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.broadcastreceiver.CallSettlement
import com.paytm.hpclpos.activities.broadcastreceiver.SettlementListener
import com.paytm.hpclpos.activities.dialogs.SettlementDialog
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity
import com.paytm.hpclpos.constants.*
import com.paytm.hpclpos.livedatamodels.ccmsrecharge.CCMSRechargeResponse
import com.paytm.hpclpos.livedatamodels.generatetoken.GenerateTokenRequest
import com.paytm.hpclpos.viewmodel.DialogListener
import com.paytm.hpclpos.viewmodel.MainActivityViewModel


abstract class BaseFragment : Fragment() {
    open var navController : NavController? = null

    protected open var dialog: Dialog? = null
    private lateinit var settlementDialog: SettlementDialog
    protected lateinit var countDownTimer: CountDownTimer
    private val TAG = "BaseFragment"
    lateinit var sharedPreferencesData: SharedPreferencesData

    abstract fun updateTimerUi(l: Long)

    fun showCustomToast(message: String?) {
        val inflater: LayoutInflater = getLayoutInflater()
        val layout: View = inflater.inflate(
            R.layout.toast,
            requireActivity().findViewById(R.id.toast_layout_root) as ViewGroup
        )
        val text = layout.findViewById<View>(R.id.text) as TextView
        text.text = message
        val toast = Toast(getContext())
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 350)
        toast.duration = Toast.LENGTH_LONG
        toast.setView(layout)
        toast.show()
    }

    protected fun showDialogForCardCOnfirm(cardType: String,title: String?, msgId1: String, msgId2: String,msgId3: String,dialogUtil: DialogUtil.OnClickListener) {
        dialog = DialogUtil.showOkDialogForCardCOnfirm(context,title,msgId1,
            msgId2,msgId3,cardType,dialogUtil)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        LocalBroadcastManager.getInstance(requireContext()).
        registerReceiver(receiver, IntentFilter("filter_string"))
    }

    protected fun showExitDialog() {
        DialogUtil.showDialog(this.requireContext(),"Remind","Click OK to Cancel the Transaction", object : DialogUtil.OnClickListener{
            override fun onConfirm() {
                DialogUtil.dismissOptionDialog()
                navController!!.navigate(R.id.action_main_fragment)
            }

            override fun onCancel() {
                DialogUtil.dismissOptionDialog()
            }
        })
    }

    fun showAppExitDialog() {
        DialogUtil.showDialog(activity,"Do you want to close the app",null, object : DialogUtil.OnClickListener{
            override fun onConfirm() {
                DialogUtil.dismissOptionDialog()
                requireActivity().finish()
            }

            override fun onCancel() {
                DialogUtil.dismissOptionDialog()
            }
        })
    }

    open fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitDialog()
                }
            })
    }

    fun callExitDialog() {
        requireActivity().runOnUiThread({
            DialogUtil.showOptionsDialog("Are you sure you want to exit the app",requireActivity(),object :
                DialogListener {
                override fun onPositiveOptionSelected() {
                    requireActivity().finish()
                }

                override fun onNegativeOptionSelected() {
                    // Ignore
                }
            })
        })
    }

    var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("MainFragment","Broadcast Received at Main Screen Activity")
            // get all your data from intent and do what you want
            if (isAdded) {
                settlementDialog = SettlementDialog(requireActivity())
                settlementDialog.setTitle(context.getString(R.string.title_settlement))
                settlementDialog.setVisibilityForSettlementStatus()
                settlementDialog.settimerAndEndpoint("${GlobalMethods.getServerIp(requireContext())}.....")
                settlementDialog.setpacketStatus("Recieving.....")
                settlementDialog.setProcessing()
                settlementDialog.show()
                CallSettlement(context).makeApiCallForBatchSettlement(object : SettlementListener {
                    override fun response(response: Any?) {
                        val response1 = response as CCMSRechargeResponse
                        if (response1.success) {
                            if (response1.internelStatusCode.equals(Constants.STATUS_SUCCESS)) {
                                GlobalMethods.decrementTransactionIdByOne(context, "000001")
                                settlementDialog.onSuccess()
                                Handler().postDelayed({ settlementDialog.dismiss() }, 1000)
                                ToastMessages.customMsgToast(
                                        context,
                                        "Response Message ${response1.internelStatusCode} Message ${response1.message}"
                                )
                                TransactionUtils.incrementBatch(context, Constants.BATCH)
                            }
                        } else {
                            settlementDialog.onFailure(response1.message)
                            Handler().postDelayed({ settlementDialog.dismiss() },1000)
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
        super.onDestroy()
    }


    open fun cancelTimer() {
        countDownTimer.cancel()
    }

    override fun onPause() {
        super.onPause()
        Log.d("timer ::: ", "onPause")
        cancelTimer()
    }

    override fun onResume() {
        super.onResume()
        val view = view
        if (view != null) {
            view.setOnTouchListener(View.OnTouchListener { view: View?, motionEvent: MotionEvent? ->
                Log.d("BaseFragment", "<<<< ON TOUCH >>>>")
                if (countDownTimer != null) {
                    countDownTimer!!.cancel()
                    countDownTimer!!.start()
                }
                false
            })
            initiateTimer()
        }
    }

    fun initiateTimer() {
        countDownTimer = object : CountDownTimer(90000, 1000) {
            override fun onTick(l: Long) {
                updateTimerUi(l)
                Log.d(TAG,"tick ::: S : $l")
            }

            override fun onFinish() {
                Handler().postDelayed({
                    DialogUtil.dismissOptionDialog()
                    startActivity(Intent(context, MainActivity::class.java))
                    requireActivity().finish()
                }, 100)
            }
        }
        countDownTimer.start()
    }

    fun displayDialog(title: String, msg: String,dialogOnclickListener: DialogUtil.OnClickListener) {
        DialogUtil.showDialog(this.requireContext(),title,msg, dialogOnclickListener)
    }

    fun closesoftKeypad(editText: EditText) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun hideKeypad() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    fun getToken() {
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val tokenRequestData = GenerateTokenRequest(Constants.ANDROIDAGENT, "string", GlobalMethods.getDeviceId(requireContext()))
        viewModel.makeApiCallGenerateToken(tokenRequestData)
        viewModel.getLiveDataObserverGenerateToken().observe(viewLifecycleOwner, {

            if (it != null) {
                if (it.Success) {
                    sharedPreferencesData.setSharedPreferenceData(Constants.TOKENIDPREF, Constants.TOKENID, it.Token)
                } else {
                    ToastMessages.customMsgToast(requireContext(), it.Message)
                }
            } else {
                ToastMessages.customMsgToast(requireContext(), "Internal Server Error")
            }
        })
    }
}