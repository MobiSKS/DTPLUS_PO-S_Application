package com.paytm.hpclpos.constants;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import com.paytm.hpclpos.R;

public class EnterMobileNoKeyboard extends LinearLayout implements View.OnClickListener {

    // constructors
    public EnterMobileNoKeyboard(Context context) {
        this(context, null, 0);
    }

    public EnterMobileNoKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnterMobileNoKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // keyboard keys (Layout)

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context) {

        // initialize buttons
         LinearLayout llayout1;
         LinearLayout llayout2;
         LinearLayout llayout3;
         LinearLayout llayout4;
         LinearLayout llayout5;
         LinearLayout llayout6;
         LinearLayout llayout7;
         LinearLayout llayout8;
         LinearLayout llayout9;
         LinearLayout llayout0;
         LinearLayout llayoutErase;
         LinearLayout llayoutDone;
        LayoutInflater.from(context).inflate(R.layout.mobilenokeyboard, this, true);
        llayout1 =  findViewById(R.id.llayout_1);
        llayout2 =  findViewById(R.id.llayout_2);
        llayout3 =  findViewById(R.id.llayout_3);
        llayout4 =  findViewById(R.id.llayout_4);
        llayout5 =  findViewById(R.id.llayout_5);
        llayout6 =  findViewById(R.id.llayout_6);
        llayout7 =  findViewById(R.id.llayout_7);
        llayout8 =  findViewById(R.id.llayout_8);
        llayout9 =  findViewById(R.id.llayout_9);
        llayout0 =  findViewById(R.id.llayout_0);
        llayoutErase =  findViewById(R.id.llayout_erase);
        llayoutDone =  findViewById(R.id.llayout_done);

        // set button click listeners
        llayout1.setOnClickListener(this);
        llayout2.setOnClickListener(this);
        llayout3.setOnClickListener(this);
        llayout4.setOnClickListener(this);
        llayout5.setOnClickListener(this);
        llayout6.setOnClickListener(this);
        llayout7.setOnClickListener(this);
        llayout8.setOnClickListener(this);
        llayout9.setOnClickListener(this);
        llayout0.setOnClickListener(this);
        llayoutErase.setOnClickListener(this);
        llayoutDone.setOnClickListener(this);

        keyValues.put(R.id.llayout_1, "1");
        keyValues.put(R.id.llayout_2, "2");
        keyValues.put(R.id.llayout_3, "3");
        keyValues.put(R.id.llayout_4, "4");
        keyValues.put(R.id.llayout_5, "5");
        keyValues.put(R.id.llayout_6, "6");
        keyValues.put(R.id.llayout_7, "7");
        keyValues.put(R.id.llayout_8, "8");
        keyValues.put(R.id.llayout_9, "9");
        keyValues.put(R.id.llayout_0, "0");
    }


    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.llayout_erase) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }
}
