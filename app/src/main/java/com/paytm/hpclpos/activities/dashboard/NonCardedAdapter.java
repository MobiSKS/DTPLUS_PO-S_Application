package com.paytm.hpclpos.activities.dashboard;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.cardredoptions.CardInfoEntity;
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.enums.SaleTransactionDetails;
import com.paytm.hpclpos.fragmentnoncardedtransaction.loyalitybalance.LoyalityBalanceMainFragment;
import com.paytm.hpclpos.fragmentnoncardedtransaction.loyaltyredeem.LoyaltyRedeemMainFrag;

import java.util.List;

public class NonCardedAdapter extends RecyclerView.Adapter<NonCardedAdapter.HomeProfile> {
    FragmentActivity transactionDashboardActivity;
    Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    private NavController navController;

    public NonCardedAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList, FragmentActivity transactionDashboardActivity, NavController navController) {
        this.context = context;
        this.cardedAndMobileModelList = cardedAndMobileModelList;
        this.transactionDashboardActivity = transactionDashboardActivity;
        this.navController = navController;
    }

    @NonNull
    @Override
    public NonCardedAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new NonCardedAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NonCardedAdapter.HomeProfile holder, int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());

        holder.llReload.setOnClickListener(view -> displaySelectedScreen(position));
    }

    private void displaySelectedScreen(int pos) {
        Fragment fragment;
        switch (pos) {
            case 0:
                runOnUiThread(R.id.action_pay_merchant_fragment);
                break;

            case 1:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.CREDIT_SALE_COMPLETE.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.CREDIT_SALE_COMPLETE.getSaleName());
                runOnUiThread(R.id.action_control_card_fragment);
                break;

            case 2:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.PAYBACK_BURN.getCategory());
                runOnUiThread(R.id.action_payback_main_fragment);
                break;

            case 3:
                runOnUiThread(R.id.action_ccmsRechargeNonCardedMainFragment);
                break;

            case 4:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.CCMS_BALANCE.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.CCMS_BALANCE.getSaleName());
               runOnUiThread(R.id.action_control_card_fragment);
                break;

            case 5:
                fragment = new LoyaltyRedeemMainFrag();
                callEnterchecknoandmicr(fragment);
                break;

            case 6:
                fragment = new LoyalityBalanceMainFragment();
                callEnterchecknoandmicr(fragment);
                break;

            case 7:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.CARD_FEE_PAYMENT.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.CARD_FEE_PAYMENT.getSaleName());
                runOnUiThread(R.id.action_cardFeeFragment);
                break;

            case 8:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.CONTROL_PIN_CHANGE.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.CONTROL_PIN_CHANGE.getSaleName());
                runOnUiThread(R.id.action_control_card_fragment);
                break;
            default:
                break;
        }
    }

    public void runOnUiThread(Integer id) {
        //GlobalMethods.Companion.setCCMSNonCarded(true);
        CardInfoEntity cardInfoEntity = new CardInfoEntity();
        cardInfoEntity.setControlCardNumber(true);
        GlobalMethods.Companion.setCardInfoEntity1(cardInfoEntity);
        transactionDashboardActivity.runOnUiThread(() -> navController.navigate(id));
    }

    private void callEnterchecknoandmicr(Fragment fragment) {
        GlobalMethods.Companion.setSaleType(SaleTransactionDetails.CARD_FEE_PAYMENT.getSaleName());
        transactionDashboardActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    @Override
    public int getItemCount() {
        return cardedAndMobileModelList.size();
    }

    public class HomeProfile extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgView;
        LinearLayout llReload;

        public HomeProfile(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgView = itemView.findViewById(R.id.imgView);
            llReload = itemView.findViewById(R.id.llReload);
        }
    }
}

