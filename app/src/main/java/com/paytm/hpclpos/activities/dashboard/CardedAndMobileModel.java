package com.paytm.hpclpos.activities.dashboard;

import android.graphics.drawable.Drawable;

public class CardedAndMobileModel {
    private String name;
    private Drawable image;

    public CardedAndMobileModel(String name, Drawable image){
        this.name=name;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }


}
