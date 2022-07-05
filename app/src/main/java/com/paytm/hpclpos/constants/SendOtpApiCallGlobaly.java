package com.paytm.hpclpos.constants;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.models.GenerateTokenData;
import com.paytm.hpclpos.models.SendOtpMain;
import com.paytm.hpclpos.retrofit.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class SendOtpApiCallGlobaly {

   private Context context;
   private Fragment fragment;

   public SendOtpApiCallGlobaly(){
       // DO nothing
   }

    public SendOtpApiCallGlobaly(Context context, Fragment fragment){
        this.context=context;
        this.fragment=fragment;
    }
    public  void sendOtpCallback(){
        if (NetworkUtil.checkNetworkStatus(context)) {


            RetrofitHelper.getInstance().doSendOtp(sendOtpCallback, "9650826982","5022063542","3089798987", "string",Constants.ANDROIDAGENT,
                    GlobalMethods.Companion.getDeviceId(context),"customer_verification","2","1");


        } else {
            ToastMessages.noInternetConnectionToast(context);

        }

    }

    Callback<SendOtpMain> sendOtpCallback = new Callback<SendOtpMain>() {
        @Override
        public void onResponse(Call<SendOtpMain> call, Response<SendOtpMain> response) {
            if (response.body()!=null){
                ToastMessages.customMsgToast(context, response.body().getData().getReason());
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }else{
                ToastMessages.customMsgToast(context, "Token Expired, Click Again");
                if (NetworkUtil.checkNetworkStatus(context)) {


                    RetrofitHelper.getInstance().doGenerateToken(generateTokenCallback);


                } else {
                    ToastMessages.noInternetConnectionToast(context);
                }
            }

        }

        @Override
        public void onFailure(Call<SendOtpMain> call, Throwable t) {
            ToastMessages.customMsgToast(context, "Failure");
        }
    };

    public  Callback<GenerateTokenData> getGenerateTokenCallback() {
        return generateTokenCallback;
    }

    Callback<GenerateTokenData> generateTokenCallback = new Callback<GenerateTokenData>() {
        @Override
        @SuppressWarnings("squid:S2696")
        public void onResponse(Call<GenerateTokenData> call, Response<GenerateTokenData> response) {
            if (response.body() != null) {
                Constants.TokenClass.TOKEN = response.body().getToken();
            }
        }

        @Override
        public void onFailure(Call<GenerateTokenData> call, Throwable t) {
            ToastMessages.customMsgToast(context, "Failure");
        }
    };
}
