package com.userking.diarypaper.activity;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.userking.diarypaper.R;
import com.userking.diarypaper.base.BaseHeadActivity;
import com.userking.diarypaper.util.ToastHelper;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class AlarmTimeActivity extends BaseHeadActivity {
    @Bind(R.id.datePicker)
    DatePicker datePicker;
    @Bind(R.id.timePicker)
    TimePicker timePicker;

    @Override
    protected void init() {
        setIvLeft(R.mipmap.back_arrow);
        setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTvOption("完成");
        setTvOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth()+" "+timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute(),AlarmTimeActivity.this);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_alarm;
    }
}
