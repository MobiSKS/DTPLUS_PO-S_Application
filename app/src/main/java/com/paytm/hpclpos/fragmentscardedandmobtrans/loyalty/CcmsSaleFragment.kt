package com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty

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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paytm.hpclpos.R
import com.paytm.hpclpos.constants.Constants
import com.paytm.hpclpos.constants.MyKeyboard
import com.paytm.hpclpos.constants.Validation
import com.paytm.hpclpos.databinding.FragmentCcmsSaleBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CcmsSaleFragment : Fragment(), View.OnClickListener {
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var current = ""
    private val Currency = ""
    private val Separator = ","
    private val Spacing = false
    private val Delimiter = false
    private val Decimals = true
    private var sendAmount: String? = null

    private val rcvAmount: String? = null
    private var textToSpeech: TextToSpeech? = null
    private val titleName: String? = null
    private val dtpNonDtpCheck = ""

    lateinit var binding: FragmentCcmsSaleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_ccms_sale, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinds(view)
        textToSpeech(view)
    }

    private fun viewFinds(view: View) {
        view.setOnTouchListener { view, motionEvent -> true }
        binding.gotoBack!!.setOnClickListener(this)
        binding.lLconfirm!!.setOnClickListener(this)
    }

    private fun textToSpeech(view: View) {
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

        binding.enterAmountText!!.setText(rcvAmount)
        val keyboard = view.findViewById<View>(R.id.keyboard) as MyKeyboard
        binding.enterAmountText!!.setOnTouchListener { v, event ->
            val inType = binding.enterAmountText!!.inputType // backup the input type
            binding.enterAmountText!!.inputType = InputType.TYPE_NULL // disable soft input
            binding.enterAmountText!!.onTouchEvent(event) // call native handler
            binding.enterAmountText!!.inputType = inType // restore input type
            true // consume touch even
        }

        binding.enterAmountText!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.enterAmountText!!.setTextIsSelectable(true)
        binding.enterAmountText!!.addTextChangedListener(object : TextWatcher {
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
                    binding.enterAmountText!!.removeTextChangedListener(this)
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
                                binding.enterAmountText!!.setText(formatted.replace(",".toRegex(), Separator))
                                sendAmount = binding.enterAmountText!!.text.toString()
                                Log.e("amountentered", "" + formatted)
                            } else {
                                //since no custom separators were set, proceed with comma separation
                                binding.enterAmountText!!.setText(formatted)
                                sendAmount = binding.enterAmountText!!.text.toString()
                                Log.e("amountentered_1", "" + formatted)
                            }
                            binding.enterAmountText!!.setSelection(formatted.length)
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }
                    }
                    binding.enterAmountText!!.addTextChangedListener(this)
                }
            }
        })


        // pass the InputConnection from the EditText to the keyboard
        val ic = binding.enterAmountText!!.onCreateInputConnection(EditorInfo())
        keyboard.setInputConnection(ic)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.gotoBack -> requireActivity()!!.onBackPressed()
            R.id.lLconfirm -> callConfirmAmountFragment()
        }
    }

    private fun callConfirmAmountFragment() {
        if (Validation.isValidEnterAmount(binding.enterAmountText!!.text.toString())) {
            val bundle = Bundle()
            bundle.putString(Constants.ENTERAMOUNT, binding.enterAmountText!!.text.toString())
            val fragment: Fragment = ConfirmationAmountFragment()
            fragment.arguments = bundle
            requireActivity()!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.javaClass.simpleName).addToBackStack(null).commit()
        } else {
            Toast.makeText(context, resources.getString(R.string.entervalidamount), Toast.LENGTH_LONG).show()
        }
    }
}