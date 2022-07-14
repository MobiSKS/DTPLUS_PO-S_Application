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
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.enums.SaleTransactionDetails;
import com.paytm.hpclpos.fragmentscardedandmobtrans.loyalty.PresentCardFragment;
import com.paytm.hpclpos.fragmentscardedandmobtrans.otcdriverredeem.EnterCardPinFragment;
import com.paytm.hpclpos.fragmentscardedandmobtrans.tracking.TrackingFragment;

import java.util.List;

public class CardedAndMobileAdapter extends RecyclerView.Adapter<CardedAndMobileAdapter.HomeProfile> {
    private Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    private FragmentActivity transactionDashboardActivity;
    private NavController navController;

    public CardedAndMobileAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList
            , FragmentActivity transactionDashboardActivity,NavController navController) {
        this.context = context;
        this.cardedAndMobileModelList = cardedAndMobileModelList;
        this.transactionDashboardActivity = transactionDashboardActivity;
        this.navController = navController;
    }

    public void runOnUiThread(Integer id){
        transactionDashboardActivity.runOnUiThread(() -> navController.navigate(id));
    }

    @NonNull
    @Override
    public CardedAndMobileAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new CardedAndMobileAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardedAndMobileAdapter.HomeProfile holder, int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());
        holder.llReload.setOnClickListener(view -> displaySelectedScreen(position));
    }

    private void displaySelectedScreen(int pos) {
        switch (pos) {
            case 0:
                runOnUiThread(R.id.action_reloadMainFragment);
                break;

            case 1:
                runOnUiThread(R.id.action_ccmsRechargeMainFragment);
                break;

            case 2:
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.BALANCE_TRANSFER.getSaleName());
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.BALANCE_TRANSFER.getSaleName());
                runOnUiThread(R.id.action_amount_entry_fragment);
                break;

            case 3:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.VOID.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.VOID.getSaleName());
                runOnUiThread(R.id.action_present_card);
                break;

            case 4:
                Fragment trackingFragment = new TrackingFragment();
                callFragment(trackingFragment);
                break;
            case 5:
                Fragment presentCardFragment = new PresentCardFragment();
                callFragment(presentCardFragment);
                break;
            case 6:
                Fragment enterCardPinFragment = new EnterCardPinFragment();
                callFragment(enterCardPinFragment);
                break;
            case 7:
                runOnUiThread(R.id.action_balanceEnquiryFragment);
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.BALANCE_ENQUIRY.getSaleName());
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.BALANCE_ENQUIRY.getSaleName());
                break;
            case 8:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.CHANGE_CARD_PIN.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.CHANGE_CARD_PIN.getSaleName());
                runOnUiThread(R.id.action_present_card);
                break;
            case 9:
                GlobalMethods.Companion.setTransType(SaleTransactionDetails.UNBLOCK_CARD_PIN.getSaleName());
                GlobalMethods.Companion.setSaleType(SaleTransactionDetails.UNBLOCK_CARD_PIN.getSaleName());
                runOnUiThread(R.id.action_present_card);
                break;
            case 10:
               /* Fragment loyaltyBalanceFragment = new LoyaltyBalanceFragment();
                callFragment(loyaltyBalanceFragment);*/
                break;
            default:
                break;
        }
    }

    private void callFragment(Fragment fragment) {
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

