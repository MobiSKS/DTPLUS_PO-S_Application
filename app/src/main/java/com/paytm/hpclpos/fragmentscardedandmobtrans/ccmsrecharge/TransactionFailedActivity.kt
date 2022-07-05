package com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity
import com.paytm.hpclpos.constants.Constants

class TransactionFailedActivity : AppCompatActivity() {
    var tvErrorMessage: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_failed)
        val lLgoToMain = findViewById<View>(R.id.lLgoToMain) as LinearLayout
        lLgoToMain.setOnClickListener { goToMainActivity() }
        tvErrorMessage = findViewById(R.id.tvErrorMessage)
        val intent = intent
        val errorMessage = intent.getStringExtra(Constants.LIMITEXCEED)
        tvErrorMessage!!.setText(errorMessage)

        //Toast.makeText(getApplicationContext(),"Your Credit TXN not enable..",Toast.LENGTH_SHORT).show();
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }
}