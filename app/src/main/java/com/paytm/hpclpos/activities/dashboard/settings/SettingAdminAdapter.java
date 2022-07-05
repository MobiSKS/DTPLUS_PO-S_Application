package com.paytm.hpclpos.activities.dashboard.settings;

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

import java.util.List;

public class SettingAdminAdapter extends RecyclerView.Adapter<SettingAdminAdapter.HomeProfile> {

    private Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;

    public SettingAdminAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList) {
        this.context = context;
        this.cardedAndMobileModelList = cardedAndMobileModelList;
    }

    @NonNull
    @Override
    public SettingAdminAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new SettingAdminAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdminAdapter.HomeProfile holder, int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());
        holder.llReload.setOnClickListener(view -> {
             // Do Nothing
        });
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

