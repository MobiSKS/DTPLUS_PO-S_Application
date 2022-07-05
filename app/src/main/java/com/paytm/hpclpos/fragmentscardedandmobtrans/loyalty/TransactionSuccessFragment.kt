package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.paytm.hpclpos.R
import com.paytm.hpclpos.databinding.FragmentTransactionSuccessBinding
import com.paytm.hpclpos.posterminal.printer.PrinterTester

class TransactionSuccessFragment : Fragment() {
    private var dialog: Dialog? = null

    lateinit var binding: FragmentTransactionSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_transaction_success, container, false)
        //ButterKnife.bind(this, view)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_transaction_success, container, false)
        showGifDialog()
        Handler().postDelayed({ dialog!!.dismiss() }, 3000)
        showReceipt()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lLgoToMain = view.findViewById<LinearLayout>(R.id.lLgoToMain)
        lLgoToMain.setOnClickListener { removeAllFragments(fragmentManager) }
    }

    private fun showGifDialog() {
        dialog = Dialog(requireContext()!!)
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
            // String str = etInputText.getText().toString();

            /*  BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inScaled = false;
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_hpresize,options);
//                        Log.i("Test", "width:"+bitmap.getWidth()+"height:"+bitmap.getHeight());
                            PrinterTester.getInstance().printBitmap(bitmap);*/
            val str = "         CUSTOMER COPY"
            val str1 = "             HPCL"
            val str2 = "        DriveTrack Plus"
            val str3 = "       MOTI RAM AND SONS"
            val str4 = "           PALWAL"
            val str5 = "DATE/TIME:          02/06/21  06:36:21"
            val strTid = "TID:                        5000057570"
            val strBatcchNo = "BATCH NO:                           59"
            val strRocNo = "ROC NO:                              4"
            val str7 = "         CASH RELOAD"
            val str8 = "MOBILE NO:                     ******9999"
            val str10 = "TRANSACTION TYPE:             CASH RELOAD"
            // String str11="EXP DATE:                           **/**";
            val str12 = "AMT:                            RS 500.00"
            val str15 = "----------------------------------------"
            val str16 = "Ensure that the above details are correct"
            val str18 = "-----------------------------------------"
            if (str != null && str.length > 0) {
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_16_48,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().step(100)

                PrinterTester.getInstance().spaceSet("1".toByte(), "0".toByte())
                PrinterTester.getInstance().printStr("""
    $str
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str1
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str2
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str3
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str4
    
    """.trimIndent(), null)
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $str5
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $strTid
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $strBatcchNo
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $strRocNo
    
    """.trimIndent(), null)
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_12_48,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(3)
                PrinterTester.getInstance().printStr("""
    $str7
    
    """.trimIndent(), null)
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $str8
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str10
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str12
    
    """.trimIndent(), null)
                PrinterTester.getInstance().printStr("""
    $str15
    
    """.trimIndent(), null)
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_16,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(1)
                PrinterTester.getInstance().printStr("""
    $str16
    
    """.trimIndent(), null)
                PrinterTester.getInstance().fontSet(EFontTypeAscii.FONT_8_32,
                        EFontTypeExtCode.FONT_16_16)
                PrinterTester.getInstance().setGray(2)
                PrinterTester.getInstance().printStr("""
    $str18
    
    
    """.trimIndent(), null)
                PrinterTester.getInstance().start()
            }
        }.start()
    }

    companion object {
        private fun removeAllFragments(fragmentManager: FragmentManager?) {
            while (fragmentManager!!.backStackEntryCount > 0) {
                fragmentManager.popBackStackImmediate()
            }
        }
    }
}