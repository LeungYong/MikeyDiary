package com.userking.diarypaper.util;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/20 0020.
 * 友盟第三方登录
 */
public class AuthLoginUtil {

    private static UMShareAPI mShareAPI = null;

    private static Activity _activity;

    private static AuthLoginUtil authLoginUtil = new AuthLoginUtil();

    public static AuthLoginUtil getInstance(Activity activity){
        _activity = activity;
        mShareAPI = UMShareAPI.get(activity);
        if(authLoginUtil == null){
            authLoginUtil = new AuthLoginUtil();
        }
        return authLoginUtil;
    }

    /**
     * 验证登录
     * **/
    public static void doAuthVerify(SHARE_MEDIA platform){
        mShareAPI.doOauthVerify(_activity, platform, umAuthListener);
    }

    /**
     * 删除验证
     * **/
    public static void deleteAuthVerify(SHARE_MEDIA platform){
        mShareAPI.deleteOauth(_activity, platform, umdelAuthListener);
    }

    /** auth callback interface**/
    private static UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(_activity, "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(_activity, "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(_activity, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    /** delauth callback interface**/
    private static UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(_activity, "delete Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(_activity, "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(_activity, "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
}
