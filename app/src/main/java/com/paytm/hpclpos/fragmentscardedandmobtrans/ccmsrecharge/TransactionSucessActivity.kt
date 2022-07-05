package com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.apphpcldb.entity.repository.AppRepository
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.paytm.hpclpos.R
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.GlobalMethods.Companion.getCardNo
import com.paytm.hpclpos.constants.GlobalMethods.Companion.getMobileNo
import com.paytm.hpclpos.constants.GlobalMethods.Companion.getProductType
import com.paytm.hpclpos.constants.GlobalMethods.Companion.getSaleType
import com.paytm.hpclpos.constants.GlobalMethods.Companion.getTransType
import com.paytm.hpclpos.constants.GlobalMethods.Companion.setCardNo
import com.paytm.hpclpos.constants.SharedPreferencesData
import com.paytm.hpclpos.databinding.ActivityTransactionSucessBinding
import com.paytm.hpclpos.posterminal.printer.PrinterTester
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction

class TransactionSucessActivity : AppCompatActivity() {
    private var dialog: Dialog? = null
    private var transProduct: String? = null
    private var transAmount: String? = null
    private var transDate: String? = null
    private var transId: String? = null
    private var batchId: String? = null
    private var sharedPreferencesData: SharedPreferencesData? = null
    private var gap: String? = "--------------------------------------"

    lateinit var binding: ActivityTransactionSucessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_sucess)
        ButterKnife.bind(this)
        transId = intent.getStringExtra(Constants.TRANSID)
        transDate = intent.getStringExtra(Constants.TRANSDATE)
        transAmount = intent.getStringExtra(Constants.TRANSAMOUNT)
        transProduct = intent.getStringExtra(Constants.TRANSPRODUCT)
        sharedPreferencesData = SharedPreferencesData(applicationContext)
        batchId = sharedPreferencesData!!.getSharedPreferenceData(Constants.PREFCONFIG, Constants.BATCHID)
        val lLgoToMain = findViewById<View>(R.id.lLgoToMain) as LinearLayout
        lLgoToMain.setOnClickListener { goToMainActivity() }
        Handler().postDelayed({ dialog!!.dismiss() }, 2000)
        showReceipt()
        if (getTransType() == "2") {
            binding.rlMobileNo!!.visibility = View.VISIBLE
            binding.tvMobileNo!!.text = getMobileNo()
            binding.tvCardNumber!!.visibility = View.GONE
            binding.rlCardNo!!.visibility = View.GONE
        } else {
            binding.rlMobileNo!!.visibility = View.GONE
            binding.tvCardNumber!!.visibility = View.VISIBLE
            binding.rlCardNo!!.visibility = View.VISIBLE
            binding.tvCardNumber!!.text = getCardNo()
        }
        binding.tvAmount!!.text = transAmount
        binding.tvBatchNo!!.text = sharedPreferencesData!!.getSharedPreferenceData(Constants.PREFCONFIG, Constants.BATCHID)
        binding.tvDateTime!!.text = transDate
        binding.tvTrasactionType!!.text = getTransType()
        storeSaleDataInRoomDatabase()
        showGifDialog()
    }

    private fun storeSaleDataInRoomDatabase() {
        val db = AppRepository(applicationContext)
        //List<HpclTrasaction> users = transactionDao.getAll();
        val hpclTrasaction = HpclTransaction()
        hpclTrasaction.transaction_Id = transId.toString()
        hpclTrasaction.transaction_Date = transDate.toString()
        hpclTrasaction.transaction_Amount = transAmount.toString()
        hpclTrasaction.product = transProduct.toString()
        hpclTrasaction.batch_Id = batchId!!.toInt()
        hpclTrasaction.productType = getProductType()
        hpclTrasaction.TransactionType = getSaleType()
        hpclTrasaction.mobileNo = getMobileNo()
        hpclTrasaction.cardNumber = getCardNo()
        AsyncTask.execute { // Insert Data
            db.insertTransaction(hpclTrasaction)
            // Get Data
       //     Log.e("TAG=", transactionDao.all[0].transaction_Date + "")
        }
    }

    private fun showGifDialog() {
        dialog = Dialog(this)
        dialog!!.setContentView(R.layout.custom_print_gif_layout)
        val imageView = dialog!!.findViewById<ImageView>(R.id.gifImageview)
        Glide.with(this).load(R.drawable.printergif).into(imageView)
        val window = dialog!!.window
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        dialog!!.show()
    }

    private fun showReceipt() {
        Thread {
            PrinterTester.getInstance().init()
            PrinterTester.getInstance().setInvert(false)

            PrinterTester.getInstance().spaceSet("2".toByte(), "0".toByte())
            // String str = etInputText.getText().toString();
            var options = BitmapFactory.Options()
            options.inScaled = false
            var bitmap = BitmapFactory.decodeResource(resources, R.drawable.hplogoprint, options)
            PrinterTester.getInstance().printBitmap(bitmap)
            PrinterTester.getInstance().printStr("\n", null)
            var str = "       CUSTOMER COPY";
            val str1 = "            HPCL"
            val str2 = "      DriveTrack Plus"
            val str3 = "     MOTI RAM AND SONS"
            val str4 = "           MUMBAI"
            val str5 = "   " + transDate + "                " + "18:10:54"
            val nameOnCar = "   PAYTM TEST"
            val vehNo = "   VEH NO:               " + "MH01AR5462"//Vehicle No
            val cardNo = "   CRD:               " + getCardNo()
            val expDate = "   EXP DATE:                 " + "03/2024"//Card Expiry Date
            val saleType = "      " + getSaleType()
            val product = "   PRODUCT:                    " + getProductType()
            val amount = "   AMOUNT:                      " + GlobalMethods.getAmount()
            val rsp = "   RSP:                        " + "rsp"//RSP
            val volume = "   VOLUME:                         " + "151"//Volume
            val balance = "   BALANCE:                      " + "10500"//balance
            val odometer = "   ODOMETER:                     5467"
            val mode = "     TRAINING MODE"
            val txno = "   TXNO                        " + GlobalMethods.getTransactionId(applicationContext)!!
            val terminalidWithRoc = "            " + GlobalMethods.getTerminalId(this)
            val line1 = gap
            val thanku = "       Thank you HPCL"
            val hpclDtp = "          HPCL DTP"
            val line2 = gap
            val line3 = gap
            val softVer = "          SOFTWARE VER: V1.02"
            val line4 = gap

            if (str1 != null && str1.length > 0) {
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_16_24,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().printStr(str + "\n", null);
                PrinterTester.getInstance().printStr(str1 + "\n", null)
                PrinterTester.getInstance().printStr(str2 + "\n", null)
                PrinterTester.getInstance().printStr(str3 + "\n", null)
                PrinterTester.getInstance().printStr(str4 + "\n", null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
     $str5

     """.trimIndent(), null)



                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $nameOnCar
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $vehNo
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $cardNo
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $expDate
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_16_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr(saleType + "\n", null)



                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $product
    
    """.trimIndent(), null)



                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $amount
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $rsp
    
    """.trimIndent(), null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $volume
    
    """.trimIndent(), null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $balance
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $odometer
    
    """.trimIndent(), null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_16_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr(mode + "\n", null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $txno
    
    """.trimIndent(), null)



                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $terminalidWithRoc
    
    """.trimIndent(), null)



                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $line1
    
    """.trimIndent(), null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_16_24,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr(thanku + "\n", null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_16_24,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr(hpclDtp + "\n", null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $line2
    
    """.trimIndent(), null)

                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $line3
    
    """.trimIndent(), null)


                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr(softVer + "\n", null)
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $line4
    
    """.trimIndent(), null)


                PrinterTester.getInstance().start()
            }
        }.start()
    }

    private fun goToMainActivity() {
        setCardNo("")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }

    override fun onBackPressed() {
        setCardNo("")
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }
}