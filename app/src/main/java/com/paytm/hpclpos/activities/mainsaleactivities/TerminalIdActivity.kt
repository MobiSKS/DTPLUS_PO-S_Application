package com.paytm.hpclpos.activities.mainsaleactivities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.ToastMessages
import com.paytm.hpclpos.constants.Validation

class TerminalIdActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operatorid)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val terminalId_edittext = findViewById<TextInputEditText>(R.id.terminalId_edittext)
        val serverIp_editText = findViewById<TextInputEditText>(R.id.serverIpEdittext)
        val secondServerIpEdittext = findViewById<TextInputEditText>(R.id.secondServerIpEdittext)
        val confirmButton = findViewById<Button>(R.id.confirm)
        confirmButton.setOnClickListener {
            if (!Validation.isValidTerminalId(terminalId_edittext.text.toString())) {
                terminalId_edittext.setError("Please Enter Valid Terminal Id")
                return@setOnClickListener
            }

            if (serverIp_editText.text.toString().isBlank()) {
                serverIp_editText.setError("Please Enter Valid Server Ip Id")
                return@setOnClickListener
            }

            if (secondServerIpEdittext.text.toString().isBlank()) {
                secondServerIpEdittext.setError("Please Enter Valid Second Server Ip Id")
                return@setOnClickListener
            }
            callMainActivity(terminalId_edittext.text.toString(),serverIp_editText.text.toString(),
                secondServerIpEdittext.text.toString())
        }

        terminalId_edittext.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callMainActivity(terminalId_edittext.text.toString(),serverIp_editText.text.toString()
                ,secondServerIpEdittext.text.toString())
                true
            }
            false
        }
    }

    fun callMainActivity(terminalId: String,serverIp: String,secondServerIp: String) {
        GlobalMethods.setTerminalId(terminalId.trim(), this)
        GlobalMethods.setServerIp(serverIp.trim(), this)
        GlobalMethods.setSecondServerIp(secondServerIp.trim(), this)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}