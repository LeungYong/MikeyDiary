package com.userking.diarypaper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;


/**
 * Created by ${Jay} on 2016/5/3 0003.
 */
public abstract class CommonRecAdapter<T> extends RecyclerArrayAdapter<T> {

    public CommonRecAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(final ViewGroup parent, int viewType) {
        return new CommonHolder(parent, layout()){

            @Override
            public void initData(View viewGroup, Object data) {
                initViewAndData(viewGroup,data);
            }
        };
    }

    public abstract int layout();

    public abstract void initViewAndData(View viewGroup, Object data);

}
