package com.paytm.hpclpos.livedatamodels.batchupload

import com.google.gson.annotations.SerializedName

data class BatchUploadResponse(
    @SerializedName("`data`")
    val data: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("method_Name")
    val methodName: String,
    @SerializedName("model_State")
    val modelState: Any,
    @SerializedName("status_Code")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)