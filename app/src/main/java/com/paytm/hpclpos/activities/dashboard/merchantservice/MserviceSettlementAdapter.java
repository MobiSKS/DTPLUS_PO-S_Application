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
import androidx.recyclerview.widget.RecyclerView;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel;
import com.paytm.hpclpos.constants.OnItemClick;

import java.util.List;

public class MserviceSettlementAdapter extends RecyclerView.Adapter<MserviceSettlementAdapter.HomeProfile> {

    Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    private  OnItemClick onItemClick;

    public MserviceSettlementAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList, OnItemClick onItemClick) {
        this.context = context;
        this.cardedAndMobileModelList = cardedAndMobileModelList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MserviceSettlementAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new MserviceSettlementAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MserviceSettlementAdapter.HomeProfile holder, int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());
        holder.llReload.setOnClickListener(view -> displaySelectedScreen(position));
    }

    private void displaySelectedScreen(int pos) {
        switch (pos) {
            case 0:
            case 1:
                onItemClick.onBatchSettlement(pos);
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

