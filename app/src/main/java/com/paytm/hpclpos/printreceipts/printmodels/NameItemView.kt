package com.paytm.hpclpos.printreceipts.printmodels

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.paytm.hpclpos.R
import lombok.Getter

class NameItemView() {
    private lateinit var printKeyValue: TextView
    @Getter
    lateinit var rootView: View
    fun render(entity: ReceiptDataFieldEntity) {
        if (entity.align && entity.bold && entity.size > 0) {
            printKeyValue.setText(entity.header)
            printKeyValue.setTypeface(Typeface.DEFAULT_BOLD)
            printKeyValue.gravity = Gravity.CENTER
            printKeyValue.textSize = entity.size.toFloat()
        } else if (entity.bold && entity.size > 0 && !entity.align) {
            printKeyValue.setText(entity.header)
            printKeyValue.textSize = entity.size.toFloat()
            printKeyValue.setTypeface(Typeface.DEFAULT_BOLD)
        } else if (entity.align && entity.size > 0) {
            printKeyValue.setText(entity.header)
            printKeyValue.textSize = entity.size.toFloat()
            printKeyValue.gravity = Gravity.CENTER
        } else if (entity.align && entity.bold) {
            printKeyValue.setText(entity.header)
            printKeyValue.setTypeface(Typeface.DEFAULT_BOLD)
            printKeyValue.gravity = Gravity.CENTER
        } else if (entity.bold) {
            printKeyValue.setText(entity.header)
            printKeyValue.setTypeface(Typeface.DEFAULT_BOLD)
        } else if (entity.gravity > 0) {
            printKeyValue.setText(entity.header)
            printKeyValue.gravity = entity.gravity
        } else if (entity.invert.equals("1".toFloat())) {
            printKeyValue.setText(entity.header)
            printKeyValue.setBackgroundColor(Color.BLACK)
            printKeyValue.setTextColor(Color.WHITE)
            printKeyValue.gravity = Gravity.CENTER
            printKeyValue.textSize = "22".toFloat()
        } else {
            printKeyValue.setText(entity.header)
        }
    }

    constructor(rootView: View) : this() {
        this.rootView = rootView
        printKeyValue = rootView.findViewById(R.id.printKeyValue)
    }
}