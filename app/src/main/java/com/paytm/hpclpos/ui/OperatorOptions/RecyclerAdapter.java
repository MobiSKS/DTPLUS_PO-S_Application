package com.paytm.hpclpos.ui.OperatorOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphpcldb.entity.repository.AppRepository;
import com.paytm.hpclpos.Dialog.LoginDialogFragment;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.constants.Constants;
import com.paytm.hpclpos.constants.GlobalMethods;
import com.paytm.hpclpos.constants.ToastMessages;
import com.paytm.hpclpos.printreceipts.OperatorLogoutReciept;
import com.paytm.hpclpos.roomDatabase.entity.Operators;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Serializable {

    private List<Operators> operators;
    private Activity context;
    private AppRepository appRepository;
    private NavController navController;

    RecyclerAdapter(List<Operators> operators, Activity context,NavController navController) {
        appRepository = new AppRepository(context);
        this.navController = navController;
        this.operators = operators;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView(operators.get(position).getOperatorId());
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private static final String TAG = "MyViewHolder";
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        void bindView(String movie) {
            textView.setText(movie);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            if (operators.get(getAdapterPosition()).isLogon()) {
                MenuItem menuItem = popupMenu.getMenu().findItem(R.id.action_popup_edit);
                menuItem.setTitle(context.getString(R.string.logout));
            }
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popup_edit:
                    if(!operators.get(getAdapterPosition()).isLogon()) { // For click on Login
                        context.runOnUiThread(() -> {
                            LoginDialogFragment.Companion.setOperatrId(operators.get(getAdapterPosition()).getOperatorId());
                            navigate2LoginFragment(Constants.LOGIN);
                           }
                        );
                    } else { // For click on Log out
                        boolean b = !operators.get(getAdapterPosition()).isLogon();
                        GlobalMethods.Companion.setIsLoginOperator(context,!operators.get(getAdapterPosition()).isLogon());
                        appRepository.updateOperator(b, operators.get(getAdapterPosition()).getOperatorId());
                        operators.get(getAdapterPosition()).setLogon(b);
                        OperatorLogoutReciept operatorLogoutReciept = new OperatorLogoutReciept(context);
                        operatorLogoutReciept.printReceipt(operators.get(getAdapterPosition()).getOperatorId()
                                , operators.get(getAdapterPosition()).getLastLogonDate()
                                , operators.get(getAdapterPosition()).getLastLogonTime());
                        notifyDataSetChanged();
                    }
                    return true;
                case R.id.action_popup_delete:
                    ToastMessages.customMsgToast(context,"DELETE");
                    appRepository.deleteOperator(Objects.requireNonNull(operators.get(getAdapterPosition()).getOperatorId()));
                    operators.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    return true;
                default:
                    return false;
            }
        }
    }

    private void navigate2LoginFragment(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NAV_VALUE, type);
        navController.navigate(R.id.action_login_fragment, bundle);
    }
}
