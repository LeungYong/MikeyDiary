package com.userking.diarypaper.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.userking.diarypaper.R;
import com.userking.diarypaper.util.SharePreferencesProvider;

import butterknife.ButterKnife;


/**
 * Created by ${Jay} on 2016/5/9 0009.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected Activity mActivity;
    public Gson gson = new Gson();

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
        mContext = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View parent;
    LayoutInflater inflater;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;

        if(parent == null){
            parent = inflater.inflate(layout(),container,false);
        }
        ButterKnife.bind(this,parent);
        //EventBus.getDefault().register(this);
        init();
        return parent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    Context sharedContext = null;
    private SharedPreferences preferences = null;

    protected void saveP(String key, String value) {
        SharedPreferences preferences = SharePreferencesProvider.getMyPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected void saveP(String key, Boolean value) {
        SharedPreferences preferences = SharePreferencesProvider.getMyPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected void saveP(String key, long value) {
        SharedPreferences preferences = SharePreferencesProvider.getMyPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    protected boolean loadP(String key, Boolean dvalue) {
        SharedPreferences preferences = SharePreferencesProvider.getMyPreferences(mActivity);
        return preferences.getBoolean(key, dvalue);
    }

    protected String loadP(String key) {
        SharedPreferences preferences = SharePreferencesProvider.getMyPreferences(mActivity);
        return preferences.getString(key, null);
    }

    protected void clearP(String key) {
        SharedPreferences preferences = SharePreferencesProvider.getMyPreferences(mActivity);
        preferences.edit().remove(key).commit();
    }

    // common classes
    protected boolean isHard = false;
    public ProgressWheel wheel = null;
    private Dialog loadingDialog = null;

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
     * 转跳 没有finish 没有切换效果
     *
     * @param classObj
     */
    public void toActivity(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        startActivity(intent);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     */
    public void overlay(Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param params
     */
    public void overlay(Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 转跳 没有finish
     *
     * @param classObj
     * @param requestCode
     */
    public void overlayForResult(Class<?> classObj, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(mActivity, classObj);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
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
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
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
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    /**
     * 转跳 有finish
     *
     * @param classObj
     */
    public void forward(Class<?> classObj) {
        Intent intent = new Intent();
        intent.setClass(mActivity, classObj);
        this.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        mActivity.finish();
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
        intent.setClass(mActivity, classObj);
        intent.putExtras(params);
        this.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        mActivity.finish();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    protected abstract int layout();

    protected abstract void init();

    /**
     * 查找view
     */
    protected <T extends View> T findView(int id) {
        return (T) parent.findViewById(id);
    }
}
