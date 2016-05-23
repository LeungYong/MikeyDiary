package com.userking.diarypaper.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.userking.diarypaper.MyApplication;
import com.userking.diarypaper.R;
import com.userking.diarypaper.base.BaseHeadActivity;
import com.userking.diarypaper.db.DBEntry;
import com.userking.diarypaper.util.Const;
import com.userking.diarypaper.util.ShareUtil;

import java.io.FileNotFoundException;

import butterknife.Bind;

public class WriteActivity extends BaseHeadActivity {

    @Bind(R.id.content)
    EditText mContent;

    @Override
    protected void init() {
        setIvLeft(R.mipmap.back_arrow);
        setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key;
                String content = mContent.getText().toString();
                if(mContent.getText().toString().length() > 10) {
                    key = content.substring(0, 10);
                }else {
                    key = content.substring(0,content.length());
                }
                String value = mContent.getText().toString();
                DBEntry dbEntry = new DBEntry();
                dbEntry.setKey(key);
                dbEntry.setResult(value);
                dbEntry.setUpdateTime(System.currentTimeMillis());
                MyApplication.liteOrmDb.insert(dbEntry);
                Intent intent = new Intent();
                intent.setAction(Const.UPDATE_BROADCAST);
                sendBroadcast(intent);
                finish();
            }
        });
        setIvRight1(R.mipmap.alarm);
        setIvRight1Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlay(AlarmTimeActivity.class);
            }
        });
        setIvRight2(R.mipmap.pc_49);
        setIvRight2Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent.append("\r\n");
                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);

                getImage.addCategory(Intent.CATEGORY_OPENABLE);

                getImage.setType("image/*");

                startActivityForResult(getImage, 1);
            }
        });
        setIvRight3(R.mipmap.ic_delete);
        setIvRight4(R.mipmap.fx_19);
        setIvRight4Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupWindowsNGShare(WriteActivity.this,v);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_write;
    }

    /**
     * 分享弹出框
     * **/
    public class PopupWindowsNGShare extends PopupWindow {

        public PopupWindowsNGShare(final Context context, View parent) {
            View view = View
                    .inflate(context, R.layout.friends_share_popupwin, null);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.activity_friends_zw_share_rela);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            view.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            LinearLayout mOneBt = (LinearLayout) view
                    .findViewById(R.id.activity_friends_zw_share_my_linear);
            LinearLayout mTwoBt = (LinearLayout) view
                    .findViewById(R.id.activity_friends_zw_share_pyq_linear);
            LinearLayout mThreeBt = (LinearLayout) view
                    .findViewById(R.id.activity_friends_zw_share_wxhy_linear);
            final LinearLayout mFourBt = (LinearLayout) view
                    .findViewById(R.id.activity_friends_zw_share_qqhy_linear);
            mOneBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtil.getInstance(WriteActivity.this).share(SHARE_MEDIA.QQ, mContent.getText().toString());
                    dismiss();
                }
            });
            mTwoBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtil.getInstance(WriteActivity.this).share(SHARE_MEDIA.WEIXIN, mContent.getText().toString());
                    dismiss();
                }
            });
            mThreeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtil.getInstance(WriteActivity.this).share(SHARE_MEDIA.WEIXIN_CIRCLE, mContent.getText().toString());
                    dismiss();
                }
            });
            mFourBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtil.getInstance(WriteActivity.this).share(SHARE_MEDIA.QZONE, mContent.getText().toString());
                    dismiss();
                }
            });
        }
    }


    Uri originalUri;
    Bitmap bitmap;
    private final double RATE = 0.618;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                originalUri = data.getData();

                try {

                    Bitmap originalBitmap = BitmapFactory.decodeStream(resolver

                            .openInputStream(originalUri));

                    bitmap = originalBitmap;
                    bitmap = resizeImage(originalBitmap, mContent.getWidth()-100, (int)(mContent.getWidth()*RATE));

                } catch (FileNotFoundException e) {

                    e.printStackTrace();

                }

                if (bitmap != null) {

                    insertIntoEditText(getBitmapMime(bitmap, originalUri));

                } else {

                    Toast.makeText(WriteActivity.this, "获取图片失败",

                            Toast.LENGTH_SHORT).show();

                }

            }

        }
    }

    private int calculateRate(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int rate = height/width;
        return rate;
    }

    public Bitmap resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    private SpannableString getBitmapMime(Bitmap pic, Uri uri) {

        String path = uri.getPath();

        SpannableString ss = new SpannableString(path);

        ImageSpan span = new ImageSpan(this, pic);

        ss.setSpan(span, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;

    }

    private void insertIntoEditText(SpannableString ss) {

        Editable et = mContent.getText();// 先获取Edittext中的内容

        int start = mContent.getSelectionStart();

        et.insert(start, ss);// 设置ss要添加的位置

        mContent.setText(et);// 把et添加到Edittext中

        mContent.setSelection(start + ss.length());// 设置Edittext中光标在最后面显示

    }
}
