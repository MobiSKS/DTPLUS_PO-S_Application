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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel;
import com.paytm.hpclpos.constants.OnItemClick;

import java.util.List;

public class SettingMaintainanceAdapter extends RecyclerView.Adapter<SettingMaintainanceAdapter.HomeProfile> {

    private Context context;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    private FragmentActivity settingDashboardActivity;
    private OnItemClick onItemClick;

    public SettingMaintainanceAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList,FragmentActivity settingDashboardActivity,OnItemClick itemClick) {
        this.context = context;
        this.cardedAndMobileModelList=cardedAndMobileModelList;
        this.settingDashboardActivity=settingDashboardActivity;
        this.onItemClick = itemClick;
    }

    @NonNull
    @Override
    public SettingMaintainanceAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingMaintainanceAdapter.HomeProfile holder, int position) {
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
            case 2:
            case 3:
                onItemClick.onBatchSettlement(pos);
                break;
            default:
                break;
        }
    }

    private void callFragment(Fragment fragment) {
        settingDashboardActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
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

