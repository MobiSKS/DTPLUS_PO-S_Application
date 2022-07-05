package com.paytm.hpclpos.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cash.cashmodel.CashReloadModel;
import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.cheque.chequemodel.CCMSChequeModel;
import com.paytm.hpclpos.fragmentnoncardedtransaction.ccmsrecharge.neftrtgs.neftreload.NEFTModel;
import com.paytm.hpclpos.models.GenerateTokenData;
import com.paytm.hpclpos.models.OperatorLoginMain;
import com.paytm.hpclpos.models.SendOtpMain;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper ourInstance = new RetrofitHelper();

    public static RetrofitHelper getInstance() {
        return ourInstance;
    }

    private RetrofitHelper() {
    }

    RetrofitApi retrofitapi;

    public void init() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).
                connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json");
                        ongoing.addHeader("Secret_Key", "PVmMSclp834KBIUa9O-XxpBsDJhsi1dsds74CiGaoo5");
                        ongoing.addHeader("API_Key", "3C25F265-F86D-419D-9A04-EA74A503C197");
                        ongoing.addHeader("Authorization","Bearer "+Constants.TokenClass.TOKEN);
                        return chain.proceed(ongoing.build());

                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        retrofitapi = retrofit.create(RetrofitApi.class);
    }

    public void doGenerateToken(Callback<GenerateTokenData> callback){
        Call<GenerateTokenData> response;
        response=retrofitapi.dogeneratetoken();
        response.enqueue(callback);
    }
    public void doSendOtp(Callback<SendOtpMain> callback,String user_Mobile,String tid,String Merchant_Id,String userid,
                          String useragent,String userip,String flag_Type,String role_id,String user_Type){
        Call<SendOtpMain> response;
        response=retrofitapi.dosendotp(user_Mobile,tid,Merchant_Id,userid,useragent,userip,flag_Type,role_id,user_Type);
        response.enqueue(callback);
    }

    public void doAuthOtp(Callback<SendOtpMain> callback,String user_Mobile,String otp,String tid,String outletId,String userid,String useragent){
        Call<SendOtpMain> response;
        response=retrofitapi.doauthotp(user_Mobile,otp,tid,outletId,userid,useragent);
        response.enqueue(callback);
    }

    public void doOperatorLogin(Callback<OperatorLoginMain> callback, String username, String password, String useragent, String userip, String userid){
        Call<OperatorLoginMain> response;
        response=retrofitapi.dooperatorlogin(username,password,useragent,userip,userid);
        response.enqueue(callback);
    }

    public void cashReload(Callback<CashReloadModel> callback, String card_No, String recharge_Amount, String sale_Type, String transaction_Id,
                           String transaction_Type, String tid, String outlet_Id, String batch_Id, String userid, String useragent, String userip) {
        Call<CashReloadModel> response;
        response = retrofitapi.cashReload(card_No, recharge_Amount, sale_Type, transaction_Id, transaction_Type, tid, outlet_Id, batch_Id, useragent, userip, userid);
        response.enqueue(callback);
    }

    public void chequeReload(Callback<CCMSChequeModel> callback, String card_No, String recharge_Amount, String sale_Type, String transaction_Type,
                             String transaction_Id, String tid, String outlet_Id, String batch_Id, String cheque_No, String micR_Code, String userid, String useragent, String userip) {
        Call<CCMSChequeModel> response;
        response = retrofitapi.ChequeModel(card_No, recharge_Amount, sale_Type, transaction_Type, transaction_Id, tid, outlet_Id, batch_Id, cheque_No,
                micR_Code, useragent, userip, userid);
        response.enqueue(callback);
    }

    public void cCMSNEFTModel(Callback<NEFTModel> callback, String card_No, String recharge_Amount, String sale_Type, String transaction_Type,
                              String transaction_Id, String tid, String outlet_Id, String batch_Id, String utR_No, String userid, String useragent, String userip) {
        Call<NEFTModel> response;
        response = retrofitapi.NEFTModel(card_No, recharge_Amount, sale_Type, transaction_Type, transaction_Id, tid, outlet_Id, batch_Id, utR_No,
                useragent, userip, userid);
        response.enqueue(callback);
    }

}
