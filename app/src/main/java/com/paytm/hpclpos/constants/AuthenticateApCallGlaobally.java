package com.paytm.hpclpos.constants;

import android.content.Context;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.models.GenerateTokenData;
import com.paytm.hpclpos.models.SendOtpMain;
import com.paytm.hpclpos.retrofit.RetrofitHelper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class AuthenticateApCallGlaobally {

   private Context context;
   private Fragment fragment;
   private String stringType= "string";

    public AuthenticateApCallGlaobally(Context context, Fragment fragment){
        this.context=context;
        this.fragment=fragment;
    }
    public  void authOtpCallback(){
        if (NetworkUtil.checkNetworkStatus(context)) {


            RetrofitHelper.getInstance().doAuthOtp(sendOtpCallback, "9999999999", "0", stringType, stringType, stringType, stringType);


        } else {
            ToastMessages.noInternetConnectionToast(context);
        }

    }

    Callback<SendOtpMain> sendOtpCallback = new Callback<SendOtpMain>() {
        @Override
        public void onResponse(Call<SendOtpMain> call, Response<SendOtpMain> response) {
            if (response.body() != null) {
                ToastMessages.customMsgToast(context, response.body().getData().getReason());
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                        .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            } else {
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
    Callback<GenerateTokenData> generateTokenCallback = new Callback<GenerateTokenData>() {
        @Override
        public void onResponse(Call<GenerateTokenData> call, Response<GenerateTokenData> response) {
        }

        @Override
        public void onFailure(Call<GenerateTokenData> call, Throwable t) {
            ToastMessages.customMsgToast(context, "Failure");
        }
    };
}
