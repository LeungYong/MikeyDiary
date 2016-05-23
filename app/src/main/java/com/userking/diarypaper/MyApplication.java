package com.userking.diarypaper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.umeng.socialize.PlatformConfig;
import com.userking.diarypaper.util.CrashHandler;
import com.zhy.http.okhttp.OkHttpUtils;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.litesuits.orm.LiteOrm;
import cn.litesuits.orm.db.DataBase;
import okio.Buffer;


/**
 * Created by ${Jay} on 2016/4/25 0025.
 */
public class MyApplication extends Application{
    public static MyApplication singleInstance;
    private static SharedPreferences sharedPreferences;
    public static DataBase liteOrmDb;
    public static Toast toast;
    public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";

    public static Map<String,String> images = new HashMap<>();
    public static List<String> bitmaps = new ArrayList<>();

    public static String CURRENT = "";

    public static String URI = "";

    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";
    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
//        dialog = new MyDialogImpl();
        liteOrmDb = LiteOrm.newCascadeInstance(this, "liteorm.db");
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        OkHttpUtils.getInstance().setCertificates(new InputStream[]{
                new Buffer()
                        .writeUtf8(CER_12306)
                        .inputStream()});
        OkHttpUtils.getInstance().debug("OkHttpUtils").setConnectTimeout(100000, TimeUnit.MILLISECONDS);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
    }


    public static MyApplication getSingleInstance() {
        return singleInstance;
    }

    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = singleInstance.getSharedPreferences("biaoxunkuaiche_android", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length();
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

}
