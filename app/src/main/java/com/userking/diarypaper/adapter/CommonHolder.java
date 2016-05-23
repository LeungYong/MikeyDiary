package com.userking.diarypaper.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by ${Jay} on 2016/5/3 0003.
 */
public abstract class CommonHolder<T> extends BaseViewHolder<T> {

    private ViewGroup viewGroup;
    public CommonHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        this.viewGroup = parent;
    }

    @Override
    public void setData(T data) {
        super.setData(data);
        initData(this.itemView,data);
    }



    public abstract void initData(View viewGroup, T data);
}
