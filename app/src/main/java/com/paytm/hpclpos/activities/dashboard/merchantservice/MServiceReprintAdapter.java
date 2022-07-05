package com.paytm.hpclpos.activities.dashboard.merchantservice;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apphpcldb.entity.repository.AppRepository;
import com.paytm.hpclpos.printreceipts.SaleReceipts;
import com.paytm.hpclpos.printreceipts.printmodels.PrintStatusListener;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.activities.dashboard.CardedAndMobileModel;
import com.paytm.hpclpos.constants.ToastMessages;
import com.paytm.hpclpos.roomDatabase.entity.HpclTransaction;

import java.util.List;

public class MServiceReprintAdapter extends RecyclerView.Adapter<MServiceReprintAdapter.HomeProfile> {

    Context context;
    Dialog dialog;
    private List<CardedAndMobileModel> cardedAndMobileModelList;
    private NavController navController;
    FragmentActivity activity;

    public MServiceReprintAdapter(Context context, List<CardedAndMobileModel> cardedAndMobileModelList,
                                  NavController navController, FragmentActivity activity) {
        this.context = context;
        this.cardedAndMobileModelList = cardedAndMobileModelList;
        this.navController = navController;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MServiceReprintAdapter.HomeProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_adapter, parent, false);
        return new HomeProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MServiceReprintAdapter.HomeProfile holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(cardedAndMobileModelList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.ptregular);
        holder.tvName.setTypeface(typeface);
        holder.imgView.setImageDrawable(cardedAndMobileModelList.get(position).getImage());
        holder.llReload.setOnClickListener(view -> displaySelectedScreen(position));
    }

    private void displaySelectedScreen(int pos) {
        switch (pos) {
            case 0:
                lastTranxSlipPrint();
                break;
            case 1:
                anyTransactionPrintSlip();
                break;
            case 2:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pos);
        }

    }

    private void lastTranxSlipPrint() {
        AppRepository db = new AppRepository(context);
        HpclTransaction transaction = db.getLastTransaction();

        if (transaction != null) {
            showGifDialog();
            new Handler(Looper.getMainLooper()).postDelayed(() -> dialog.dismiss(), 2000);
            new SaleReceipts(context, activity, transaction).displayReceipt(new PrintStatusListener() {
                @Override
                public void onError(int error) {
                // DO nothing
                }

                @Override
                public void onSuccess() {
                    // DO nothing
                }
            });
        } else {
            ToastMessages.customMsgToast(context, "No Transaction Found");
        }
    }

    private void anyTransactionPrintSlip() {
        activity.runOnUiThread(() -> navController.navigate(R.id.action_enter_invoice_number));
    }

    private void showGifDialog() {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_print_gif_layout);
        ImageView imageView = dialog.findViewById(R.id.gifImageview);
        Glide.with(context).load(R.drawable.printergif).into(imageView);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
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

