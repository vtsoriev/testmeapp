package com.vtsoriev.testmeapp;

import android.content.Context;
import android.widget.CheckBox;

/**
 * Created by Nell on 20.10.2017.
 */

public class MyCheckBox extends android.support.v7.widget.AppCompatCheckBox {

    public MyCheckBox(Context context) {
        super(context);
    }

    protected void markAsRight () {
        setTextColor(getResources().getColor(R.color.colorGreen));
        setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
    }

    protected void markAsWrong () {
        setTextColor(getResources().getColor(R.color.colorRed));
        setBackgroundColor(getResources().getColor(R.color.colorRed));
    }
}
