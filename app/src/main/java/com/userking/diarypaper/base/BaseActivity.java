package com.userking.diarypaper.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.userking.diarypaper.R;
import com.userking.diarypaper.util.DeviceUtil;
import com.userking.diarypaper.util.ToastHelper;

import butterknife.ButterKnife;


/**
 * Created by ${Jay} on 2016/4/25 0025.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    public Gson gson = new Gson();
    public Context mContext;

    /**
     * 查找view
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

//    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        mContext = this;
        ButterKnife.bind(this);
        init();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        //EventBus.getDefault().unregister(this);
    }


    Context sharedContext = null;
    private SharedPreferences preferences = null;

    public SharedPreferences getMyPreferences() {
        // TODO Auto-generated method stub
        if (preferences != null)
            return preferences;
        try {
            sharedContext = createPackageContext(DeviceUtil.getPackageName(this), Context.CONTEXT_IGNORE_SECURITY);
            preferences = sharedContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        } catch (PackageManager.NameNotFoundException e) {
//            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return preferences;
    }

    protected void saveP(String key, String value) {
        SharedPreferences preferences = getMyPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected void saveP(String key, Long value) {
        SharedPreferences preferences = getMyPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    protected void saveP(String key, Boolean value) {
        SharedPreferences preferences = getMyPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected boolean loadP(String key, Boolean dvalue) {
        SharedPreferences preferences = getMyPreferences();
        return preferences.getBoolean(key, dvalue);
    }

    protected long loadP(String key, Long dvalue) {
        SharedPreferences preferences = getMyPreferences();
        return preferences.getLong(key, dvalue);
    }

    protected String loadP(String key) {
        SharedPreferences preferences = getMyPreferences();
        return preferences.getString(key, null);
    }

    protected void clearP(String key) {
        SharedPreferences preferences = getMyPreferences();
        preferences.edit().remove(key).commit();
    }


    /**
     * 转跳 没有finish 没有切换效果
     *
     * @param classObj
     */
    public void toActivity(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        startActivity(intent);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     */
    public void overlay(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param params
     */
    public void overlay(Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     * @param params
     */
    public void overlayLeftForResult(Class<?> classObj, int requestCode, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     */
    public void overlayForResult(Class<?> classObj, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    // //////////////////////////////////////////////////////////////////////////////////////////////
    // logic method

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     * @param params
     */
    public void overlayForResult(Class<?> classObj, int requestCode, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 转跳 有finish
     *
     * @param classObj
     */
    public void forward(Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        this.startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        this.finish();
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    // debug method

    /**
     * 转跳 有finish
     *
     * @param classObj
     * @param params
     */
    public void forward(Class<?> classObj, Bundle params) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.putExtras(params);
        this.startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        this.finish();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        onFinish();
//        scrollToFinishActivity();
    }

    public void onFinish() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    public ProgressWheel wheel = null;
    /**
     * 收起waiting dialog
     */
    public void hideLoadBar() {
        if (wheel != null)
            wheel.stopSpinning();
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    public void showLoadBar(String title, String content) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = new MaterialDialog.Builder(mContext)
                .title(title)
                .content(content)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
    }


    /**
     * 初始化
     * findViewById(...)
     */
    protected abstract void init();

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 初始化数据
     */
    protected void initData() {
        //
    }



    // //////////////////////////////////////////////////////////////////////////////////////////////
    // common classes
    protected boolean isHard = false;
    private Dialog loadingDialog = null;




    public void setIsHard(boolean isHard) {
        this.isHard = isHard;
    }

    boolean isbackground = false;

    public void setIsbackgroundLoading(boolean isbackground) {
        this.isbackground = isbackground;
    }


    public void toast(String msg) {
        ToastHelper.showToast(msg, getApplicationContext());
    }

    protected void toast(int msg_id) {
        ToastHelper.showToast(getString(msg_id), this);
    }

}
