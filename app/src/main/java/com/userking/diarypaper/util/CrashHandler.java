package com.userking.diarypaper.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ${Jay} on 2016/4/27 0027.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //获取单例对象
    private static CrashHandler crashHandler = new CrashHandler();

    //获取程序上下文
    private Context mContext;

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");


    //异常内容
    private StringBuilder exceptionContent = new StringBuilder();

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void init(Context context) {
        mContext = context;

        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        getPhoneInfo();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        exceptionContent.append("错误异常信息：" + sw.toString());
        writeFileToSD(exceptionContent.toString());
        Log.i("tttttt", "over");
        /*if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("CrashHandler", "error : ", e);
            }

        }*/
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                /*Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG).show();

                //sendMailByIntent();
                Looper.loop();*/
                writeFileToSD(exceptionContent.toString());
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                }
            }
        }.start();

        return true;
    }


    @Deprecated
    @SuppressWarnings("deprecation")
    private void getPhoneInfo() {
        String MOBILE_TYPE = Build.MODEL;
        String OS_VERSION = Build.VERSION.SDK;
        exceptionContent.append("应用名称：CarFriend\r\n");
        exceptionContent.append("手机型号：" + MOBILE_TYPE + "\r\n");
        exceptionContent.append("系统SDK版本：" + OS_VERSION + "\r\n");
    }

    public void writeFileToSD(String err) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        try {
            String pathName = Environment.getExternalStorageDirectory() + "/CarFriend/";
            String time = formatter.format(new Date());
            String fileName = "file-" + time + ".txt";
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                Log.d("TestFile", "Create the path:" + pathName);
                path.mkdir();
            }
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = err.getBytes();
            stream.write(buf);
            stream.close();

        } catch (Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }
}
