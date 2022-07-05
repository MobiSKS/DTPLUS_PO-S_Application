package com.paytm.hpclpos.printreceipts.printmodels

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.paytm.hpclpos.R
import lombok.Getter

class KeyValueItem() {
    private lateinit var key: TextView
    private lateinit var value: TextView
    @Getter
    lateinit var rootView : View
    fun render(entity: ReceiptDataFieldEntity) {
        if (entity.keyGravity > 0 && entity.valueGravity > 0) {
            key.text = entity.key
            value.text = entity.value
            key.gravity = entity.keyGravity
            value.gravity = entity.valueGravity
        } else if (entity.textSizeSet > 0 && entity.textBold) {
            key.text = entity.key
            value.text = entity.value
            key.typeface = Typeface.DEFAULT_BOLD
            value.typeface = Typeface.DEFAULT_BOLD
            key.textSize = entity.textSizeSet.toFloat()
            value.textSize = entity.textSizeSet.toFloat()
        } else if (entity.padding > 0) {
            key.text = entity.key
            value.text = entity.value
            value.typeface = Typeface.DEFAULT_BOLD
            key.setPadding(0,entity.padding,0,0)
            value.setPadding(0,entity.padding,0,0)
        } else {
            key.text = entity.key
            value.text = entity.value
        }
    }

    constructor(rootView: View) : this() {
        this.rootView = rootView
        key = rootView.findViewById(R.id.name_value_name)
        value = rootView.findViewById(R.id.name_value_val)
    }
}