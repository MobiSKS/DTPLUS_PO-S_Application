package com.paytm.hpclpos.retrofit;


import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cash.cashmodel.CashReloadModel;
import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cheque.chequemodel.CCMSChequeModel;
import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.neftrtgs.neftreload.NEFTModel;
import com.paytm.hpclpos.models.GenerateTokenData;
import com.paytm.hpclpos.models.OperatorLoginMain;
import com.paytm.hpclpos.models.SendOtpMain;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApi {

    @GET("dtp/api/dtplus/generatetoken")
    Call<GenerateTokenData> dogeneratetoken();

    @FormUrlEncoded
    @POST("/dtp/api/dtplus/login/send_otp")
    Call<SendOtpMain> dosendotp(@Field("user_Mobile") String user_Mobile,
                                @Field("tid") String tid,
                                @Field("Merchant_Id") String Merchant_Id,
                                @Field("userid") String userid,
                                @Field("useragent") String useragent,
                                @Field("userip") String userip,
                                @Field("flag_Type") String flag_Type,
                                @Field("role_id") String role_id,
                                @Field("user_Type") String user_Type);
    @FormUrlEncoded
    @POST("/dtp/api/dtplus/login/authenticate")
    Call<SendOtpMain> doauthotp(@Field("user_Mobile") String user_Mobile,
                                @Field("otp") String otp,
                                @Field("tid") String tid,
                                @Field("Merchant_Id") String Merchant_Id,
                                @Field("userid") String userid,
                                @Field("useragent") String useragent);

    @FormUrlEncoded
    @POST("/dtp/api/dtplus/user/operator_login")
    Call<OperatorLoginMain> dooperatorlogin(@Field("username") String username,
                                            @Field("password") String password,
                                            @Field("useragent") String useragent,
                                            @Field("userip") String userip,
                                            @Field("userid") String userid);



    @FormUrlEncoded
    @POST("terminal/api/edc/transaction/reload_api_by_cash")
    Call<CashReloadModel> cashReload(@Field("card_No") String card_No,
                                     @Field("recharge_Amount") String recharge_Amount,
                                     @Field("sale_Type") String sale_Type,
                                     @Field("transaction_Id") String transaction_Id,
                                     @Field("transaction_Type") String transaction_Type,
                                     @Field("tid") String tid,
                                     @Field("Merchant_Id") String Merchant_Id,
                                     @Field("batch_Id") String batch_Id,
                                     @Field("userid") String userid,
                                     @Field("useragent") String useragent,
                                     @Field("userip") String userip);


    @FormUrlEncoded
    @POST("terminal/api/edc/transaction/reload_api_by_cheque")
    Call<CCMSChequeModel> ChequeModel(@Field("card_No") String card_No,
                                      @Field("recharge_Amount") String recharge_Amount,
                                      @Field("sale_Type") String sale_Type,
                                      @Field("transaction_Type") String transaction_Type,
                                      @Field("transaction_Id") String transaction_Id,
                                      @Field("tid") String tid,
                                      @Field("Merchant_Id") String Merchant_Id,
                                      @Field("batch_Id") String batch_Id,
                                      @Field("cheque_No") String cheque_No,
                                      @Field("micR_Code") String micR_Code,
                                      @Field("userid") String userid,
                                      @Field("useragent") String useragent,
                                      @Field("userip") String userip);


    @FormUrlEncoded
    @POST("terminal/api/edc/transaction/reload_api_by_neft_rtgs")
    Call<NEFTModel> NEFTModel(@Field("card_No") String card_No,
                              @Field("recharge_Amount") String recharge_Amount,
                              @Field("sale_Type") String sale_Type,
                              @Field("transaction_Type") String transaction_Type,
                              @Field("transaction_Id") String transaction_Id,
                              @Field("tid") String tid,
                              @Field("Merchant_Id") String Merchant_Id,
                              @Field("batch_Id") String batch_Id,
                              @Field("utR_No") String utR_No,
                              @Field("userid") String userid,
                              @Field("useragent") String useragent,
                              @Field("userip") String userip);

    @FormUrlEncoded
    @POST("dtpwebapi/api/dtplus/transaction/recharge_ccms_account")
    Call<NEFTModel> recharge_ccms_account(@Field("card_No") String card_No,
                              @Field("recharge_Amount") String recharge_Amount,
                              @Field("sale_Type") String sale_Type,
                              @Field("transaction_Type") String transaction_Type,
                              @Field("transaction_Id") String transaction_Id,
                              @Field("tid") String tid,
                              @Field("Merchant_Id") String Merchant_Id,
                              @Field("batch_Id") String batch_Id,
                              @Field("utR_No") String utR_No,
                              @Field("userid") String userid,
                              @Field("useragent") String useragent,
                              @Field("userip") String userip);
}

