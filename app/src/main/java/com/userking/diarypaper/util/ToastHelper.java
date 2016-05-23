package com.userking.diarypaper.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ${Jay} on 2016/4/25 0025.
 */
public class ToastHelper {
    public static Toast mToast;

    public static void showToast(int resId, Context context) {
        String text = context.getString(resId);
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }

    public static void showToast(String text, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
