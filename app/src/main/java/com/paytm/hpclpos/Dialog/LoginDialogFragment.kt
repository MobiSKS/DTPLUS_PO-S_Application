package com.paytm.hpclpos.Dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.apphpcldb.entity.repository.AppRepository
import com.google.android.material.button.MaterialButton
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.DateUtils
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.roomDatabase.entity.Operators
import com.paytm.hpclpos.ui.OperatorOptions.OperatorOptionsFragment
import com.paytm.hpclpos.ui.OperatorOptions.OperatorOptionsViewModel
import com.paytm.hpclpos.ui.OperatorOptions.RecyclerAdapter
import lombok.Setter

class LoginDialogFragment : DialogFragment() {

    companion object {
        private val WIDTH = 700
        private val HEIGHT = 700

        @Setter
        var operatrId : String? = null
    }

    lateinit var navController : NavController
    lateinit var operatorOptionsViewModel: OperatorOptionsViewModel
    lateinit var operatorId : EditText
    lateinit var password : EditText

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        handleOnBackPressed()
        return inflater.inflate(R.layout.fragment_login_dialog,container,false)
    }

    fun handleOnBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    dismiss()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.getWindow()!!.setLayout(WIDTH, HEIGHT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        operatorOptionsViewModel =
            ViewModelProviders.of(this@LoginDialogFragment).get(OperatorOptionsViewModel::class.java)
        operatorId = view.findViewById(R.id.operatorId)
        password =  view.findViewById(R.id.password)
        val cancel =  view.findViewById<AppCompatButton>(R.id.logincancel)
        val confirm =  view.findViewById<AppCompatButton>(R.id.loginconfirm)
        val onClickType = arguments?.getString(Constants.NAV_VALUE)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        onClickType?.let {
            if(it.equals(Constants.LOGIN)) {
                operatorId.setText(operatrId)
                operatorId.setKeyListener(null)
            }
        }
        cancel.setOnClickListener({ dismiss() })

        confirm.setOnClickListener {
            hideKeyboard(it)
            onClickType?.let {
                when (it) {
                    Constants.SIGNUP -> { signUp() }
                    Constants.LOGIN_SCREEN -> { validation() }
                    Constants.LOGIN -> { operatrId?.let { it1 -> login(it1, password.text.toString()) }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (dialog != null) {
            dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager? = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun signUp() {
        val operatorId = this@LoginDialogFragment.operatorId.text.toString()
        val password = this@LoginDialogFragment.password.text.toString()
        val appRepository = AppRepository(requireContext())
        if (operatorId.isBlank() || password.isBlank()) {
            ToastMessages.customMsgToast(context, "Please Enter Valid Id and Password")
        } else {
            val operators = Operators().apply {
                this.operatorId = this@LoginDialogFragment.operatorId.text.toString()
                this.password = this@LoginDialogFragment.password.text.toString()
                isLogon = false
                lastLogonDate = ""
                lastLogonTime = ""
            }
            appRepository.insertOperator(operators)
            navigate2OperatorsFragment()
        }
        dismiss()
    }

    private fun validation() {
        val date = DateUtils.getCurrentDateTime()
        val appRepository = AppRepository(requireContext())
        val operators = appRepository.getOperator(operatorId.text.toString(),password.text.toString())
        if (operators != null) {
            GlobalMethods.setIsLoginOperator(requireContext(),true)
            appRepository.updateOperatorLoginDateTime(!operators.isLogon, operatorId.text.toString(),
                date.substring(0, 10), date.substring(10, date.length))
            ToastMessages.customMsgToast(requireContext(), "LOGIN SUCESS")
        } else {
            ToastMessages.customMsgToast(requireContext(), "LOGIN FAILURE")
        }
        dismiss()
    }

    private fun login(operatorId: String, password: String) {
        val date = DateUtils.getCurrentDateTime()
        val appRepository = AppRepository(requireContext())
        val operators = appRepository.getOperator(operatrId!!, password)
        if (operators != null) {
            GlobalMethods.setIsLoginOperator(requireContext(),true)
            appRepository.updateOperatorLoginDateTime(!operators.isLogon, operatorId,
                date.substring(0, 10), date.substring(10, date.length))
            ToastMessages.customMsgToast(requireContext(),"LOGIN SUCCESSFUL")
            navigate2OperatorsFragment()
        } else {
            ToastMessages.customMsgToast(context,"LOGIN Failed, Please enter valid password")
        }
        dismiss()
    }

    private fun navigate2OperatorsFragment() {
        navController.navigate(R.id.action_operator_options_fragment)
    }
}