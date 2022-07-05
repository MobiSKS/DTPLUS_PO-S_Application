package com.paytm.hpclpos.activities.dashboard.settings;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
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
import com.paytm.hpclpos.activities.mainsaleactivities.MainActivity;

import java.util.List;

public class SettingOthersAdapter extends RecyclerView.Adapter<SettingOthersAdapter.HomeProfile> {

    Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    FragmentActivity settingDashboardActivity;

    public SettingOthersAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList, FragmentActivity settingDashboardActivity) {
        this.context = context;
        this.cardedAndMobileModelList = cardedAndMobileModelList;
        this.settingDashboardActivity = settingDashboardActivity;

    }

    @NonNull
    @Override
    public SettingOthersAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new SettingOthersAdapter.HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingOthersAdapter.HomeProfile holder, int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());
        holder.llReload.setOnClickListener(view -> displaySelectedScreen(position));

    }

    private void displaySelectedScreen(int pos) {

        Intent intent;

        switch (pos) {
            case 0:
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS).setFlags(FLAG_ACTIVITY_NEW_TASK));
                break;
            case 1:
                context.startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS).setFlags(FLAG_ACTIVITY_NEW_TASK));
                break;
            case 2:
                context.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS).setFlags(FLAG_ACTIVITY_NEW_TASK));
                break;
            case 3:
                context.startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS).setFlags(FLAG_ACTIVITY_NEW_TASK));
                break;
            case 4:
                intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                settingDashboardActivity.finish();
                break;
            case 5:
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
            tvName = itemView.findViewById(R.id.tvName);
            imgView = itemView.findViewById(R.id.imgView);
            llReload = itemView.findViewById(R.id.llReload);
        }
    }
}

