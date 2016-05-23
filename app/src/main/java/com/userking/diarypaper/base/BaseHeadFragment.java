package com.userking.diarypaper.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.userking.diarypaper.R;



/**
 * Created by ${Jay} on 2016/5/9 0009.
 */
public abstract class BaseHeadFragment extends BaseFragment{
    private ImageView iv_left;

    /**
     * 设置左图标
     * **/
    protected void setIvLeft(int res){
        iv_left = (ImageView)findView(R.id.left_iv);
        if(iv_left != null){
            iv_left.setBackgroundResource(res);
            iv_left.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置左操作事件
     * **/
    protected void setLeftListener(View.OnClickListener onClickListener){
        if(iv_left == null){
            iv_left = (ImageView)findView(R.id.left_iv);
        }
        iv_left.setOnClickListener(onClickListener);
    }


    private TextView tv_left;

    /**
     * 设置左标题
     * **/
    protected void setTvLeft(String title){
        tv_left = (TextView)findView(R.id.title_tv);
        if(tv_left != null){
            tv_left.setText(title);
            tv_left.setVisibility(View.VISIBLE);
        }
    }

    private TextView tv_title;

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        tv_title = (TextView) findView(R.id.title_tv);
        if (tv_title != null) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }
    }

    private ImageView iv_title;

    /***
     * 设置标题图标
     * **/
    protected void setIvTitle(int resId){
        iv_title = (ImageView)findView(R.id.title_iv);
        if (iv_title != null){
            iv_title.setBackgroundResource(resId);
            iv_title.setVisibility(View.VISIBLE);
        }
    }


    private ImageView insertImage;

    /**
     * 设置图标1
     * **/
    protected void setIvRight1(int res){
        insertImage = (ImageView)findView(R.id.insertButton);
        if(insertImage != null){
            insertImage.setBackgroundResource(res);
            insertImage.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置图标1 点击事件
     * **/
    protected void setIvRight1Listener(View.OnClickListener onClickListener){
        if(insertImage == null){
            insertImage = (ImageView)findView(R.id.insertButton);
        }
        insertImage.setVisibility(View.VISIBLE);
        insertImage.setOnClickListener(onClickListener);
    }

    private ImageView theme;

    /**
     * 设置图标2
     * **/
    protected void setIvRight2(int res){
        theme = (ImageView)findView(R.id.themeButton);
        if(theme != null){
            theme.setBackgroundResource(res);
            theme.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置图标2 点击事件
     * **/
    protected void setIvRight2Listener(View.OnClickListener onClickListener){
        if(theme == null){
            theme = (ImageView)findView(R.id.themeButton);
        }
        theme.setVisibility(View.VISIBLE);
        theme.setOnClickListener(onClickListener);
    }

    private ImageView share;

    /**
     * 设置图标3
     * **/
    protected void setIvRight3(int res){
        share = (ImageView)findView(R.id.shareButton);
        if(share != null){
            share.setBackgroundResource(res);
            share.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置图标3 点击事件
     * **/
    protected void setIvRight3Listener(View.OnClickListener onClickListener){
        if(share == null){
            share = (ImageView)findView(R.id.shareButton);
        }
        share.setVisibility(View.VISIBLE);
        share.setOnClickListener(onClickListener);
    }

    private ImageView option;

    /**
     * 设置图标4
     * **/
    protected void setIvRight4(int res){
        option = (ImageView)findView(R.id.optionButton);
        if(option != null){
            option.setBackgroundResource(res);
            option.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置图标4 点击事件
     * **/
    protected void setIvRight4Listener(View.OnClickListener onClickListener){
        if(option == null){
            option = (ImageView)findView(R.id.optionButton);
        }
        option.setVisibility(View.VISIBLE);
        option.setOnClickListener(onClickListener);
    }

    private TextView tv_option;
    /**
     * 设置右操作文字
     * **/
    protected void setTvOption(String title){
        tv_option = (TextView)findView(R.id.tv_option);
        if(tv_option != null){
            tv_option.setText(title);
            tv_option.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右操作事件
     * **/
    protected void setTvOptionListener(View.OnClickListener optionListener){
        if(tv_option == null){
            tv_option = (TextView)findView(R.id.tv_option);
        }
        tv_option.setOnClickListener(optionListener);
    }
}
