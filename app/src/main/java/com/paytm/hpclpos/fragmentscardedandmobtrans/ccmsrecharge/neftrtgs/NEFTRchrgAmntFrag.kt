package com.paytm.hpclpos.fragmentscardedandmobtrans.ccmsrecharge.neftrtgs

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.GlobalMethods
import com.paytm.hpclpos.constants.MyKeyboard
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentNEFTRchrgAmntBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class NEFTRchrgAmntFrag : Fragment() {
    private var current = ""
    private val Currency = ""
    private val Separator = ","
    private val Spacing = false
    private val Delimiter = false
    private val Decimals = true
    private var sendAmount: String? = null
    private var enterAmountText: EditText? = null
    private val rcvAmount: String? = null
    private var textToSpeech: TextToSpeech? = null

    lateinit var binding: FragmentNEFTRchrgAmntBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_n_e_f_t_rchrg_amnt, container, false)
        textToSpeech = TextToSpeech(context, object : OnInitListener {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech!!.setLanguage(Locale.US)
                    val utteranceId = this.hashCode().toString() + ""
                    val speechStatus = textToSpeech!!.speak("Please Enter Amount", TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!")
                    }
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!")
                    } else {
                        Log.i("TTS", "Language Supported.")
                    }
                    Log.i("TTS", "Initialization success.")
                } else {
                    Toast.makeText(context, "TTS Initialization failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        enterAmountText = binding.root.findViewById<View>(R.id.enterAmountText) as EditText
        enterAmountText!!.setText(rcvAmount)
        val lLconfirm = binding.root.findViewById<View>(R.id.lLconfirm) as LinearLayout
        val keyboard = binding.root.findViewById<View>(R.id.keyboard) as MyKeyboard
        binding.gotoBack.setOnClickListener { requireActivity()!!.onBackPressed() }
        lLconfirm.setOnClickListener { callControlPinFrag() }
        enterAmountText!!.setOnTouchListener { v, event ->
            val inType = enterAmountText!!.inputType // backup the input type
            enterAmountText!!.inputType = InputType.TYPE_NULL // disable soft input
            enterAmountText!!.onTouchEvent(event) // call native handler
            enterAmountText!!.inputType = inType // restore input type
            true // consume touch even
        }

        // prevent system keyboard from appearing when EditText is tapped
        enterAmountText!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
        enterAmountText!!.setTextIsSelectable(true)
        enterAmountText!!.addTextChangedListener(object : TextWatcher {
            var dec = DecimalFormat("0.00")
            override fun afterTextChanged(arg0: Editable) {
                //Do Something
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                //Do Something
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString() != current) {
                    enterAmountText!!.removeTextChangedListener(this)
                    val cleanString = s.toString().replace("[$,.]".toRegex(), "").replace(Currency.toRegex(), "").replace("\\s+".toRegex(), "")
                    if (cleanString.length != 0) {
                        try {
                            var currencyFormat = ""
                            currencyFormat = if (Spacing) {
                                if (Delimiter) {
                                    "$Currency. "
                                } else {
                                    "$Currency "
                                }
                            } else {
                                if (Delimiter) {
                                    "$Currency."
                                } else {
                                    Currency
                                }
                            }
                            val parsed: Double
                            val parsedInt: Int
                            val formatted: String
                            if (Decimals) {
                                parsed = cleanString.toDouble()
                                formatted = NumberFormat.getCurrencyInstance().format(parsed / 100).replace(NumberFormat.getCurrencyInstance().currency.symbol, currencyFormat)
                            } else {
                                parsedInt = cleanString.toInt()
                                formatted = currencyFormat + NumberFormat.getNumberInstance(Locale.US).format(parsedInt.toLong())
                            }
                            current = formatted

                            //if decimals are turned off and Separator is set as anything other than commas..
                            if (Separator != "," && !Decimals) {
                                //..replace the commas with the new separator
                                enterAmountText!!.setText(formatted.replace(",".toRegex(), Separator))
                                sendAmount = enterAmountText!!.text.toString()
                                Log.e("amountentered", "" + formatted)
                            } else {
                                //since no custom separators were set, proceed with comma separation
                                enterAmountText!!.setText(formatted)
                                sendAmount = enterAmountText!!.text.toString()
                                Log.e("amountentered_1", "" + formatted)
                            }
                            enterAmountText!!.setSelection(formatted.length)
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }
                    }
                    enterAmountText!!.addTextChangedListener(this)
                }
            }
        })


        // pass the InputConnection from the EditText to the keyboard
        val ic = enterAmountText!!.onCreateInputConnection(EditorInfo())
        keyboard.setInputConnection(ic)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener { view, motionEvent -> true }
    }

    private fun callControlPinFrag() {
        if (Validation.isValidEnterAmount(enterAmountText!!.text.toString())) {
            GlobalMethods.setAmount(enterAmountText!!.text.toString())
            val fragment: Fragment = NEFTControlPinFrag()
            requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.entervalidamount), Toast.LENGTH_LONG).show()
        }
    }
}