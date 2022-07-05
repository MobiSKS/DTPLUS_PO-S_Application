package com.paytm.hpclpos.printreceipts.printmodels

import android.view.LayoutInflater
import android.view.ViewGroup
import com.paytm.hpclpos.R
import lombok.Getter

@Getter
class ReceiptDataFieldEntity {
    var header: String? = null
    var align = false
    var size : Int = 0
    var key: String? = null
    var value: String? = null
    var textSizeSet = 0
    var bold = false
    var textBold = false
    var gravity = 0
    var type: String? = null
    var count: String? = null
    var amount: String? = null
    var keyGravity = 0
    var valueGravity = 0
    var invert: Float = 0f
    var padding: Int = 0

    constructor(header: String?) {
        this.header = header
    }

    constructor(header: String?, invert: Float) {
        this.header = header
        this.invert = invert
    }

    constructor(header: String?, bold: Boolean) {
        this.header = header
        this.bold = bold
    }

    constructor(header: String?, bold: Boolean, align: Boolean) {
        this.header = header
        this.bold = bold
        this.align = align
        this.invert = invert
    }

    constructor(header: String?, bold: Boolean, align: Boolean, size: Int) {
        this.header = header
        this.bold = bold
        this.align = align
        this.size = size
    }

    constructor(type: String?, count: String?, amount: String?) {
        this.type = type
        this.count = count
        this.amount = amount
    }

    constructor(key: String?, value: String?) {
        this.key = key
        this.value = value
    }

    constructor(key: String?, value: String?, textSizeSet: Int, textBold: Boolean) {
        this.key = key
        this.value = value
        this.textSizeSet = textSizeSet
        this.textBold = textBold
    }

    constructor(gravity: Int, header: String?) {
        this.header = header
        this.gravity = gravity
    }

    constructor(gravity: Int, header: String?, size: Int) {
        this.header = header
        this.gravity = gravity
        this.size = size
    }

    constructor(key: String?, value: String?, keyGravity: Int, valueGravity: Int) {
        this.key = key
        this.value = value
        this.keyGravity = keyGravity
        this.valueGravity = valueGravity
    }

    constructor(key: String?, value: String?,padding: Int) {
        this.key = key
        this.value = value
        this.padding = padding
    }

    fun createView(parent: ViewGroup): NameItemView {
        return NameItemView(
            LayoutInflater.from(parent.context).inflate(R.layout.item_print_name, parent, false)
        )
    }

    public fun createBoldView(parent: ViewGroup): KeyValueItem {
        return KeyValueItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_print_name_value_bold, parent, false)
        )
    }
}