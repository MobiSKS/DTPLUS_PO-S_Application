package com.paytm.hpclpos.ui.amount

import androidx.lifecycle.ViewModel
import lombok.Getter
import lombok.Setter

class AmountViewModel : ViewModel() {
    @Getter
    @Setter
    var amountDisplayText: String? = null

    @Getter
    @Setter
    var amount: Long = 0
    var decimals = 2

    @Getter
    @Setter
    val amountFormat = "%.2f"
    val regex = "\\s"

    @Getter
    val divider = 100
}