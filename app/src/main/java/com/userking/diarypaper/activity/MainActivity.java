package com.userking.diarypaper.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.userking.diarypaper.MyApplication;
import com.userking.diarypaper.R;
import com.userking.diarypaper.adapter.CommonRecAdapter;
import com.userking.diarypaper.base.BaseHeadActivity;
import com.userking.diarypaper.db.DBEntry;
import com.userking.diarypaper.util.Const;
import com.userking.diarypaper.util.ShareUtil;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseHeadActivity {


    @Bind(R.id.easyRecyclerView)
    EasyRecyclerView easyRecyclerView;

    CommonRecAdapter<DBEntry> dbEntryCommonRecAdapter;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Override
    protected void init() {
        setTitle("米奇笔记");
        setIvRight4(R.mipmap.notebook);
        setIvRight4Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlay(WriteActivity.class);
            }
        });
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbEntryCommonRecAdapter = new CommonRecAdapter<DBEntry>(this) {
            @Override
            public int layout() {
                return R.layout.item_diary;
            }

            @Override
            public void initViewAndData(View viewGroup, Object data) {
                DBEntry dbEntry = (DBEntry)data;
                TextView title = (TextView)viewGroup.findViewById(R.id.title);
                TextView summary = (TextView)viewGroup.findViewById(R.id.summary);
                String date = simpleDateFormat.format(dbEntry.getUpdateTime());
                title.setText(date);
                summary.setText(dbEntry.getResult());
            }
        };
        easyRecyclerView.setAdapter(dbEntryCommonRecAdapter);
        showLoadBar("加载数据","在正加载数据...");
        initReceiver();
        loadData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateReceiver);
    }

    private UpdateReceiver updateReceiver;
    private void initReceiver(){
        updateReceiver = new UpdateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.UPDATE_BROADCAST);
        this.registerReceiver(updateReceiver, intentFilter);
    }

    private void loadData(){
        new MyAsyncTask().execute();
    }

    private class MyAsyncTask extends AsyncTask<Void,Void,List<DBEntry>>{

        @Override
        protected List<DBEntry> doInBackground(Void... params) {
            List<DBEntry> dbEntries = MyApplication.liteOrmDb.query(DBEntry.class);
            return dbEntries;
        }

        @Override
        protected void onPostExecute(List<DBEntry> dbEntries) {
            dbEntryCommonRecAdapter.addAll(dbEntries);
            dbEntryCommonRecAdapter.notifyDataSetChanged();
            hideLoadBar();
        }
    }

    private class UpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Const.UPDATE_BROADCAST)){
                dbEntryCommonRecAdapter.clear();
                dbEntryCommonRecAdapter.notifyDataSetChanged();
                showLoadBar("加载数据","在正加载数据...");
                loadData();
            }
        }
    }

}
