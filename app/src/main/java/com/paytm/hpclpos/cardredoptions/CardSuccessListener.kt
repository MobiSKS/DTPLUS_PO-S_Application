package com.paytm.hpclpos.cardredoptions

interface CardSuccessListener {
    fun performActionSuccess(cardInfoEntity: CardInfoEntity)
}