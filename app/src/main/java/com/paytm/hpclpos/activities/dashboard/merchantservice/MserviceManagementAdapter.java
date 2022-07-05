package com.paytm.hpclpos.activities.dashboard.merchantservice;

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
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel;
import com.paytm.hpclpos.activities.dashboard.merchantservice.management.TrminalPinChangeFrag;
import com.paytm.hpclpos.activities.dashboard.merchantservice.management.UnblockTerminalPinFrag;
import com.paytm.hpclpos.ui.OperatorOptions.OperatorOptionsFragment;

import java.util.List;

public class MserviceManagementAdapter extends RecyclerView.Adapter<MserviceManagementAdapter.HomeProfile> {

    FragmentActivity dashboardMerchantService;
    Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    private NavController navController;

    public MserviceManagementAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList,
                                     FragmentActivity dashboardMerchantService,NavController navController) {
        this.context = context;
        this.cardedAndMobileModelList=cardedAndMobileModelList;
        this.dashboardMerchantService=dashboardMerchantService;
        this.navController = navController;
    }

    @NonNull
    @Override
    public MserviceManagementAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new MserviceManagementAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MserviceManagementAdapter.HomeProfile holder, int position) {
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
                dashboardMerchantService.runOnUiThread(() ->
                        navController.navigate(R.id.action_operator_options_fragment));
                break;

            case 1:
                fragment=new TrminalPinChangeFrag();
                callEnterchecknoandmicr(fragment);
                break;

            case 2:
                fragment=new UnblockTerminalPinFrag();
                callEnterchecknoandmicr(fragment);
                break;

            default:
                break;

        }

    }

    private void callEnterchecknoandmicr( Fragment fragment) {
        dashboardMerchantService.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
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
            tvName=itemView.findViewById(R.id.tvName);
            imgView=itemView.findViewById(R.id.imgView);
            llReload=itemView.findViewById(R.id.llReload);
        }
    }
}

