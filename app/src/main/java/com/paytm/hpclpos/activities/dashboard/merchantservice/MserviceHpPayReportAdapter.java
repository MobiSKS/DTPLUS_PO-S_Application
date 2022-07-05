package com.paytm.hpclpos.activities.dashboard.merchantservice;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel;
import com.paytm.hpclpos.constants.ToastMessages;

import java.util.List;

public class MserviceHpPayReportAdapter extends RecyclerView.Adapter<MserviceHpPayReportAdapter.HomeProfile> {

    FragmentActivity dashboardMerchantService;

    Activity context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;

    public MserviceHpPayReportAdapter(Activity context, List<CardedAndMobileModel> cardedAndMobileModelList,FragmentActivity dashboardMerchantService) {
        this.context = context;
        this.cardedAndMobileModelList=cardedAndMobileModelList;
        this.dashboardMerchantService=dashboardMerchantService;
    }

    @NonNull
    @Override
    public MserviceHpPayReportAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new MserviceHpPayReportAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MserviceHpPayReportAdapter.HomeProfile holder, int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());
        holder.llReload.setOnClickListener(view -> displaySelectedScreen(position));
    }

    private void displaySelectedScreen(int pos) {
        switch (pos) {
            case 0:
                context.runOnUiThread(() -> ToastMessages.customMsgToast(context,"Not Implemented"));
                break;
            case 1:
               context.runOnUiThread(() -> ToastMessages.customMsgToast(context,"Not Implemented"));
               break;
            default:
                break;
        }
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
