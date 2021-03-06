package com.jsqix.gxt.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.api.InterfaceJSONPost;
import com.jsqix.gxt.tv.api.JSONPost;
import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.obj.BaseObj;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;


public class NameActivity extends BaseAty implements InterfaceJSONPost {
    @ViewInject(R.id.et_name)
    private EditText screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
    }

    @Override
    protected void executeMessage(int instructions) {

    }


    @Override
    public void initView() {

    }

    @OnClick(R.id.bt_confirm)
    public void confirmClick(View v) {
        if (StringUtils.notNull(StringUtils.textToString(screenName))) {
            setName();
        } else {
            ToastUtils.toast(this, "请输入名称");
        }
    }

    private void setName() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceId", aCache.getAsString(KeyUtils.S_ID));
        map.put("deviceName", StringUtils.textToString(screenName));
        JSONPost post = new JSONPost(this, map, this) {
            @Override
            public void onPreExecute() {
                showLoading();
            }
        };
        post.execute(HttpUtil.SET_DEVICE_NAME);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        hideLoading();
        BaseObj baseObj = new Gson().fromJson(result, BaseObj.class);
        if (baseObj != null) {
            if (baseObj.getCode().equals("000")) {
                startActivity(new Intent(this, HomeActivity.class));
                aCache.put(KeyUtils.S_NAME, StringUtils.textToString(screenName));
                finish();
            } else {
                ToastUtils.toast(this, baseObj.getMsg());
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
