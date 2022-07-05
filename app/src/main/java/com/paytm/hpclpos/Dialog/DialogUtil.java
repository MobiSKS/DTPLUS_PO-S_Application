package com.paytm.hpclpos.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.paytm.hpclpos.R;
import com.paytm.hpclpos.viewmodel.DialogListener;

public class DialogUtil {

    private DialogUtil() {
        throw new IllegalStateException("DialogUtil class Exception");
    }

    protected static OkDialog dialog = null;
    protected static OkDialogThreeParams dialogThreeParams = null;
    private static NotificationDialog notificationDialog =null;
    private static OptionDialog optionDialog = null;
    private static TerminalPinDialog terminalPinDialog = null;
    private static TerminalSettingDialog terminalSettingDialog = null;

    public static Dialog showDialog(Context context, String title, String msg, OnClickListener onClickListener) {
        optionDialog = new OptionDialog(context);
        optionDialog.setCanceledOnTouchOutside(false);
        optionDialog.setCancelable(false);
        optionDialog.setTitle(title).setMsg(msg).setOnClickListener(onClickListener);
        optionDialog.show();
        return optionDialog;
    }

    public static void dismissOptionDialog() {
        if (optionDialog != null && optionDialog.isShowing()) {
            optionDialog.dismiss();
        }
    }

    public static Dialog showOkDialog(Context context, String title, String msg, OnClickListener onClickListener) {
        dialog = new OkDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle(title).setMsg(msg).setOnClickListener(onClickListener);
        dialog.show();
        return dialog;
    }

    public static Dialog showOkDialogForCardCOnfirm(Context context, String title, String msg1, String msg2 , String msg3,String cardType, OnClickListener onClickListener) {
        dialogThreeParams = new OkDialogThreeParams(context);
        dialogThreeParams.setCanceledOnTouchOutside(false);
        dialogThreeParams.setCancelable(false);
        dialogThreeParams.setTitle(title).setMsg1(msg1).setMsg2(msg2).setMsg3(msg3).setCardType(cardType).setOnClickListener(onClickListener);
        dialogThreeParams.show();
        return dialogThreeParams;
    }

    public static void dismissOkDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static LoadingDialog showLoadingDialog(Context context, String msg) {
        LoadingDialog dialog = new LoadingDialog(context);
        dialog.setMsg(msg);
        dialog.show();
        return dialog;
    }

    private static class OptionDialog extends Dialog implements View.OnClickListener {

        private String msg = "";
        private String title = "";
        private DialogUtil.OnClickListener onClickListener;
        Context context;

        public OptionDialog(@NonNull Context context) {
            this(context, R.style.myDialog);
        }

        public OptionDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_confirm_cancel_layout);
            initView();
        }

        private void initView() {
            TextView tvMsg = findViewById(R.id.msg);
                TextView tvTitle = findViewById(R.id.title);
            TextView confirm = findViewById(R.id.confirm);
            TextView cancel = findViewById(R.id.cancel);
            confirm.setOnClickListener(this);
            cancel.setOnClickListener(this);
            if (TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(msg)) {
                tvMsg.setVisibility(View.GONE);
            }
            tvMsg.setText(msg);
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    if (onClickListener != null) {
                        onClickListener.onConfirm();
                    }
                    break;
                case R.id.cancel:
                    if (onClickListener != null) {
                        onClickListener.onCancel();
                    }
                    break;
                default:
                    break;
            }
            dismiss();
        }

        public OptionDialog setTitle(String title) {
            this.title = title;
            return this;
        }

        public OptionDialog setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public OptionDialog setOnClickListener(DialogUtil.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }
    }

    private static class LoadingDialog extends Dialog {

        private String msg = "";
        Context context;

        public LoadingDialog(@NonNull Context context) {
            this(context, R.style.loading_dialog_style);
        }

        public LoadingDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_loading_layout);
            initView();
        }

        private void initView() {
            TextView tvMsg = findViewById(R.id.msg);
            tvMsg.setText(msg);
        }

        public LoadingDialog setMsg(String msg) {
            this.msg = msg;
            return this;
        }
    }

    public interface OnClickListener {
        void onConfirm();
        void onCancel();
    }

    public interface OnTimeoutListener {
        void onTimeout();
    }

    private static class OkDialog extends Dialog implements View.OnClickListener {

        private String msg = "";
        private String title = "";
        private DialogUtil.OnClickListener onClickListener;
        Context context;

        public OkDialog(@NonNull Context context) {
            this(context, R.style.myDialog);
        }

        public OkDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.ok_dialog_layout);
            initView();
        }

        private void initView() {
            TextView tvMsg = findViewById(R.id.msg);
            TextView tvTitle = findViewById(R.id.title);
            Button okBtn = findViewById(R.id.ok);
            okBtn.setOnClickListener(this);
            if (TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            }
            tvMsg.setText(msg);
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ok && onClickListener != null) {
                onClickListener.onConfirm();
            } else {
                dismiss();
            }
        }

        public OkDialog setTitle(String title) {
            this.title = title;
            return this;
        }

        public OkDialog setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public OkDialog setOnClickListener(DialogUtil.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }
    }

    public static class NotificationDialog extends Dialog {
        Context context;

        public NotificationDialog(@NonNull Context context, String title, String message) {
            super(context, R.style.myDialog);
            this.context=context;
            setContentView(R.layout.notification_dialog_layout);
            setCancelable(false);
            TextView tvMsg = findViewById(R.id.msg);
            TextView tvTitle = findViewById(R.id.title);
            tvMsg.setText(message);
            tvTitle.setText(title);
        }
    }

    public static void showNotificationDialog(Context context, String title, String message, final OnTimeoutListener timeoutListener) {
        if(notificationDialog == null) {
            notificationDialog = new NotificationDialog(context, title, message);
        }
        notificationDialog.show();

        if(timeoutListener != null) {
            new Handler(Looper.getMainLooper()).postDelayed(timeoutListener::onTimeout, 2000);
        }
    }

    public static void dismissNotificationDialog() {
        if (notificationDialog != null && notificationDialog.isShowing()) {
            notificationDialog.dismiss();
        }
        notificationDialog = null;
    }

    public static Dialog showTransactionOutcomesDialog(Context context, String title, String msg, int image) {
        TransactionOutcomesDialog transactionDialog = new TransactionOutcomesDialog(context);
        transactionDialog.setCanceledOnTouchOutside(false);
        transactionDialog.setCancelable(false);
        transactionDialog.setTitle(title).setMsg(msg).setId(image);
        return transactionDialog;
    }

    private static class TransactionOutcomesDialog extends Dialog {
        private String title = "";
        private String msg = "";
        private int image;
        Context context;

        public TransactionOutcomesDialog(@NonNull Context context) {
            this(context, R.style.myDialog);
        }

        public TransactionOutcomesDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.transaction_dialog_layout);
            initView();
        }

        private void initView() {
            TextView tvTitle = findViewById(R.id.title);
            TextView tvMsg = findViewById(R.id.msg);
            ImageView imageView = findViewById(R.id.image);
            tvTitle.setText(title);
            tvMsg.setText(msg);
            imageView.setImageResource(image);
        }

        public TransactionOutcomesDialog setTitle(String title) {
            this.title = title;
            return this;
        }

        public TransactionOutcomesDialog setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public TransactionOutcomesDialog setId(int image) {
            this.image = image;
            return this;
        }
    }

    public static void getAlertDialog(Context context, String title, String msg) {
        AlertDialog alertDialog = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.label_ok, (dialogInterface, i) -> dialogInterface.dismiss()).create();
        alertDialog.show();
    }


    private static class OkDialogThreeParams extends Dialog implements View.OnClickListener {

        private String msg1 = "";
        private String msg2 = "";
        private String msg3 = "";
        private String title = "";
        private String cardTypeString = "";
        private DialogUtil.OnClickListener onClickListener;
        Context context;

        public OkDialogThreeParams(@NonNull Context context) {
            this(context, R.style.myDialog);
        }

        public OkDialogThreeParams(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.ok_dialog_layout_three_params);
            initView();
        }

        private void initView() {
            TextView tvMsg1 = findViewById(R.id.msg1);
            TextView tvMsg2 = findViewById(R.id.msg2);
            TextView tvMsg3 = findViewById(R.id.msg3);
            TextView tvTitle = findViewById(R.id.title);
            TextView cardType = findViewById(R.id.cardNumber);
            TextView confirm = findViewById(R.id.confirm);
            TextView cancel = findViewById(R.id.cancel);
            confirm.setOnClickListener(this);
            cancel.setOnClickListener(this);
            if (TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            }
            tvMsg1.setText(msg1);
            tvMsg2.setText(msg2);
            tvMsg3.setText("₹ "+ msg3);
            tvTitle.setText(title);
            cardType.setText(cardTypeString);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    if (onClickListener != null) {
                        onClickListener.onConfirm();
                    }
                    break;
                case R.id.cancel:
                    if (onClickListener != null) {
                        onClickListener.onCancel();
                    }
                    break;
                default:
                    break;
            }
            dismiss();
        }

        public OkDialogThreeParams setTitle(String title) {
            this.title = title;
            return this;
        }

        public OkDialogThreeParams setMsg1(String msg) {
            this.msg1 = msg;
            return this;
        }

        public OkDialogThreeParams setMsg2(String msg) {
            this.msg2 = msg;
            return this;
        }

        public OkDialogThreeParams setMsg3(String msg) {
            this.msg3 = msg;
            return this;
        }

        public OkDialogThreeParams setCardType(String msg) {
            this.cardTypeString = msg;
            return this;
        }

        public OkDialogThreeParams setOnClickListener(DialogUtil.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }
    }

    public static AlertDialog showOptionsDialog(final String message, final Context context, final DialogListener listener) {
        return new MaterialAlertDialogBuilder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.label_ok, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    if(listener != null) {
                        listener.onPositiveOptionSelected();
                    }
                })
                .setNegativeButton("cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    if(listener != null) {
                        listener.onNegativeOptionSelected();
                    }
                })
                .show();
    }

    private static class TerminalPinDialog extends Dialog implements View.OnClickListener {

        private TerminalPinOnClickListener onClickListener;
        private Context context;
        private TextView terminalPin;

        public TerminalPinDialog(@NonNull Context context) {
            this(context, R.style.myDialog);
        }

        public TerminalPinDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_terminal_pin);
            initView();
        }

        private void initView() {
            terminalPin = findViewById(R.id.etTerminalPin);
            TextView confirm = findViewById(R.id.confirm);
            TextView cancel = findViewById(R.id.cancel);
            confirm.setOnClickListener(this);
            cancel.setOnClickListener(this);
            terminalPin.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (onClickListener != null) {
                        dismiss();
                        terminalPin.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        onClickListener.onConfirm(terminalPin.getText().toString());
                    }
                    return true;
                }
                return false;
            });
        }

        public String getTerminalPin() {
            return terminalPin.getText().toString();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    if (onClickListener != null) {
                        dismiss();
                        onClickListener.onConfirm(terminalPin.getText().toString());
                        terminalPin.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }
                    break;
                case R.id.cancel:
                    if (onClickListener != null) {
                        dismiss();
                        onClickListener.onCancel();
                        terminalPin.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }
                    break;
                default:
                    break;
            }
        }

        public TerminalPinDialog setOnClickListener(TerminalPinOnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }
    }

    public static Dialog showTerminalPinDialog(Context context, TerminalPinOnClickListener onClickListener) {
        terminalPinDialog = new TerminalPinDialog(context);
        terminalPinDialog.setCanceledOnTouchOutside(false);
        terminalPinDialog.setCancelable(false);
        terminalPinDialog.setOnClickListener(onClickListener);
        terminalPinDialog.show();
        return terminalPinDialog;
    }

    private static class TerminalSettingDialog extends Dialog implements View.OnClickListener {

        private DialogUtil.OnClickListener onClickListener;
        private Context context;
        private TextView etInputText;
        private TextView title;
        private String textTitle;
        private String textValue;

        public TerminalSettingDialog(@NonNull Context context,String title, String value) {
            this(context, R.style.myDialog);
            textTitle = title;
            textValue = value;
        }

        public TerminalSettingDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            this.context = context;
            setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_terminal_setting);
            initView();
        }

        private void initView() {
            etInputText = findViewById(R.id.etInputText);
            title = findViewById(R.id.title);
            TextView confirm = findViewById(R.id.confirm);
            TextView cancel = findViewById(R.id.cancel);
            confirm.setOnClickListener(this);
            cancel.setOnClickListener(this);
            title.setText(textTitle);
            etInputText.setText(textValue);
            etInputText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (onClickListener != null) {
                        dismiss();
                        etInputText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        onClickListener.onConfirm();
                    }
                    return true;
                }
                return false;
            });
        }

        public String getInputText() {
            return etInputText.getText().toString();
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setValue(String value) {
            this.etInputText.setText(value);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    if (onClickListener != null) {
                        dismiss();
                        onClickListener.onConfirm();
                        etInputText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }
                    break;
                case R.id.cancel:
                    if (onClickListener != null) {
                        dismiss();
                        onClickListener.onCancel();
                        etInputText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }
                    break;
                default:
                    break;
            }
        }

        public TerminalSettingDialog setOnClickListener(DialogUtil.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }
    }

    public static Dialog showTerminalSettingDialog(String title, String value, Context context, DialogUtil.OnClickListener onClickListener) {
        terminalSettingDialog = new TerminalSettingDialog(context,title,value);
        terminalSettingDialog.setCanceledOnTouchOutside(false);
        terminalSettingDialog.setCancelable(false);
        terminalSettingDialog.setOnClickListener(onClickListener);
        terminalSettingDialog.show();
        return terminalSettingDialog;
    }

    public static String getTerminalSettingDialogEditText() {
        return terminalSettingDialog.getInputText();
    }

    public interface TerminalPinOnClickListener {
        void onConfirm(String pinPassword);
        void onCancel();
    }
}

