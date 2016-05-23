package com.userking.diarypaper.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.userking.diarypaper.R;


/**
 * Created by Administrator on 2016/5/16 0016.
 * 友盟第三方分享
 */
public class ShareUtil {

    private static Activity _activity;

    private static ShareUtil shareUtil = new ShareUtil();

    static UMImage image ;
    static UMusic music ;

    static UMVideo video ;
    static String url = "http://www.umeng.com";

    public static ShareUtil getInstance(Activity activity){
        _activity = activity;
        if(shareUtil == null){
            shareUtil = new ShareUtil();
        }
        image = new UMImage(_activity, "http://www.umeng.com/images/pic/social/integrated_3.png");
        music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        music.setTitle("This is music title");
        music.setThumb(new UMImage(_activity, "http://www.umeng.com/images/pic/social/chart_1.png"));
        video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
        return shareUtil;
    }

    public void share(SHARE_MEDIA platform, String url){
        new ShareAction(_activity).setPlatform(platform).setCallback(umShareListener)
                .withText("umeng")
                .withMedia(image)
                .withExtra(new UMImage(_activity, R.drawable.umeng_socialize_qq_on))
                .withTargetUrl("http://dev.umeng.com")
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            Toast.makeText(_activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(_activity,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(_activity,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
