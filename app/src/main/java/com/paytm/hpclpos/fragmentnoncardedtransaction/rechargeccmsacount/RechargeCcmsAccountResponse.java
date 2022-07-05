package com.paytm.hpclpos.fragmentnoncardedtransaction.rechargeccmsacount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paytm.hpclpos.livedatamodels.ccmssale.Data;

import java.util.ArrayList;

public class RechargeCcmsAccountResponse {
   @SerializedName("Success")
   @Expose
    public boolean success;
   @SerializedName("Status_Code")
   @Expose
   public int statusCode;
   @SerializedName("Internel_Status_Code")
   @Expose
   public int internelStatusCode;
   @SerializedName("Message")
   @Expose
   public String message;
   @SerializedName("Method_Name")
   @Expose
   public String methodName;
   @SerializedName("Data")
   @Expose
   public ArrayList<Data> data;
   @SerializedName("Model_State")
   @Expose
   public Object modelState;
}
