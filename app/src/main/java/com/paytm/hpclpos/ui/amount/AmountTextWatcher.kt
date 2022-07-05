package com.paytm.hpclpos.ui.amount
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.paytm.hpclpos.databinding.ActivityCcsmsSaleBinding
import java.text.NumberFormat
import java.util.*

class AmountTextWatcher
    ( private val binding: ActivityCcsmsSaleBinding , private val amountViewModel: AmountViewModel) : TextWatcher {
    private var current = ""
    private val Spacing = false
    private val Delimiter = false
    private val Currency = ""
    private val Decimals = true
    private val Separator = ","
    private var sendAmount: String? = null

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //Required for future implementation
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        //Required for future implementation
    }

    override fun afterTextChanged(s: Editable) {
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
                            binding.enterAmountText.setText(formatted.replace(",".toRegex(), Separator))
                            sendAmount = binding.enterAmountText.text.toString()
                            Log.d("amountentered", "" + formatted)
                        } else {
                            //since no custom separators were set, proceed with comma separation
                            binding.enterAmountText.setText(formatted)
                            sendAmount = binding.enterAmountText.text.toString()
                            Log.d("amountentered_1", "" + formatted)
                        }
                        binding.enterAmountText.setSelection(formatted.length)
                    } catch (e: NumberFormatException) {
                        Log.e("AmountTextWatcher",e.localizedMessage!!)
                    }
                }
                binding.enterAmountText.addTextChangedListener(this)
            }
    }
}