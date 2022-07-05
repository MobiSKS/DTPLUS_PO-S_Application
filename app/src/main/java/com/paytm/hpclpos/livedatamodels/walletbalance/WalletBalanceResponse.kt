package com.paytm.hpclpos.livedatamodels.walletbalance

import android.os.Parcel
import android.os.Parcelable

data class WalletBalanceResponse(
    //val `data`: List<Data>,
    val `data`: Any,
    val message: String,
    val methodName: String,
    val modelState: Any,
    val statusCode: Int,
    val success: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
            TODO("data"),
            parcel.readString()!!,
            parcel.readString()!!,
            TODO("model_State"),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeString(methodName)
        parcel.writeInt(statusCode)
        parcel.writeByte(if (success) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WalletBalanceResponse> {
        override fun createFromParcel(parcel: Parcel): WalletBalanceResponse {
            return WalletBalanceResponse(parcel)
        }

        override fun newArray(size: Int): Array<WalletBalanceResponse?> {
            return arrayOfNulls(size)
        }
    }
}