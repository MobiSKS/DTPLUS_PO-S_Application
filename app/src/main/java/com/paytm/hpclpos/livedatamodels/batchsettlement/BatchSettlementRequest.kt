package com.paytm.hpclpos.livedatamodels.batchsettlement

import com.google.gson.annotations.SerializedName

class BatchSettlementRequest(
 @SerializedName("UserId")
  var userId: String?,
 @SerializedName("Useragent")
  var userAgent: String?,
 @SerializedName("Userip")
  var userIp: String?,
 @SerializedName("Merchantid")
  var merchantId: String?,
 @SerializedName("Terminalid")
  var terminalId: String?,
 @SerializedName("Batchid")
  var batchId: Int,
 @SerializedName("ObjTranscationsForBatchSettlement")
  var objTranscationsForBatchSettlement: ArrayList<ObjTranscationsForBatchSettlement>? = null,
 @SerializedName("CreatedBy")
  var createdBy: String?
 )