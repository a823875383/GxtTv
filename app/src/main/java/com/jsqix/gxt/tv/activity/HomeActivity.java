package com.jsqix.gxt.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.tv.BDVideoViewActivity;
import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.WebActivity;
import com.jsqix.gxt.tv.adapter.HomeAdapter;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.api.InterfaceJSONGet;
import com.jsqix.gxt.tv.api.JSONGet;
import com.jsqix.gxt.tv.base.ADAty;
import com.jsqix.gxt.tv.obj.HomeResult;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeActivity extends ADAty implements InterfaceJSONGet {
    @ViewInject(R.id.tv_screen)
    private TextView screenName;
    @ViewInject(R.id.tv_contact)
    private TextView contactName;
    @ViewInject(R.id.gridView)
    private GridView gridView;

    private List<HomeResult.ObjBean> data = new ArrayList<HomeResult.ObjBean>();
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (StringUtils.toInt(aCache.getAsString(KeyUtils.S_STATUS)) != 1) {
            playerUtil.stopTask();
            Intent intent = new Intent(this, BDVideoViewActivity.class);
            startActivity(intent);
        }
        //开启定时上传截图服务
//        PollingUtils.startPollingService(this, 2 * 60, ScreenUpService.class, ScreenUpService.ACTION);
    }

    @Override
    protected void executeMessage(int instructions) {
        if (instructions == 1001) {//禁用
            aCache.put(KeyUtils.S_STATUS, "-1");
            playerUtil.stopTask();
            Intent intent = new Intent(this, BDVideoViewActivity.class);
            startActivity(intent);
        } else if (instructions == 1003) {//启用

        } else if (instructions == 1002) {
            actualShot();
        } else if (instructions == 1007) {//解绑
            finish();
            aCache.put(KeyUtils.S_ID,"");
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();

    }

    @Override
    public void initView() {
        screenName.setText(aCache.getAsString(KeyUtils.S_NAME));
        contactName.setText(aCache.getAsString(KeyUtils.S_PHONE));
        adapter = new HomeAdapter(this, R.layout.item_menu, data);
        gridView.setAdapter(adapter);
    }

    @OnItemClick(R.id.gridView)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", data.get(position).getModel_link());
        startActivity(intent);
    }

    private void getData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceId", aCache.getAsString(KeyUtils.S_ID));
        JSONGet get = new JSONGet(this, map, this) {
            @Override
            public void onPreExecute() {
                showLoading();
            }
        };
        get.execute(HttpUtil.GET_HOME);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        hideLoading();
        HomeResult homeResult = new Gson().fromJson(result, HomeResult.class);
        if (homeResult != null) {
            if (homeResult.getCode().equals("000")) {
                data.clear();
                data.addAll(homeResult.getObj());
                adapter.notifyDataSetChanged();
            }
        } else {
            ToastUtils.toast(this, "链接错误,请稍后重试");
        }
    }


    @Override
    public boolean onGenericMotionEvent(MotionEvent ev) {
        if (0 != (ev.getSource() & InputDevice.SOURCE_CLASS_POINTER)) {
            if (ev.getAction() == MotionEvent.BUTTON_SECONDARY) {
                return true;
            }
        }
        return super.onGenericMotionEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
