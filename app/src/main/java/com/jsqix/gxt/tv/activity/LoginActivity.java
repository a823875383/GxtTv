package com.jsqix.gxt.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.api.InterfaceJSONPost;
import com.jsqix.gxt.tv.api.JSONPost;
import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.obj.LoginResult;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseAty implements InterfaceJSONPost {
    @ViewInject(R.id.et_name)
    private EditText loginName;
    @ViewInject(R.id.et_pass)
    private EditText loginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void executeMessage(int instructions) {

    }


    @Override
    public void initView() {
        if (StringUtils.notNull(aCache.getAsString(KeyUtils.S_ID))) {
            loginName.setText(aCache.getAsString(KeyUtils.U_NAME));
            loginPass.setText(aCache.getAsString(KeyUtils.U_PASS));
            login();
        }
    }

    @OnClick(R.id.bt_login)
    public void loginClick(View v) {
        if (StringUtils.isEmpty(StringUtils.textToString(loginName))) {
            ToastUtils.toast(this, "请输入登录名");
        } else if (StringUtils.isEmpty(StringUtils.textToString(loginPass))) {
            ToastUtils.toast(this, "请输入密码");
        } else {
            login();
        }
    }

    //登录
    private void login() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uName", StringUtils.textToString(loginName));
        map.put("uPassword", StringUtils.textToString(loginPass));
        map.put("mac", aCache.getAsString(KeyUtils.MAC));
        map.put("token", aCache.getAsString(KeyUtils.TOKEN));
        JSONPost post = new JSONPost(this, map, this) {
            @Override
            public void onPreExecute() {
                showLoading();
            }
        };
        post.execute(HttpUtil.LOGIN);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        hideLoading();
        LoginResult loginResult = new Gson().fromJson(result, LoginResult.class);
        if (loginResult != null) {
            if (loginResult.getCode().equals("000")) {
                aCache.put(KeyUtils.U_NAME, StringUtils.textToString(loginName));
                aCache.put(KeyUtils.U_PASS, StringUtils.textToString(loginPass));
                aCache.put(KeyUtils.S_ID, loginResult.getObj().getDevice_id());
                aCache.put(KeyUtils.S_STATUS, loginResult.getObj().getDevice_status());
                aCache.put(KeyUtils.S_PHONE, loginResult.getObj().getDevice_phone());
                if (StringUtils.isEmpty(loginResult.getObj().getDevice_name())) {
                    startActivity(new Intent(this, NameActivity.class));
                } else {
                    aCache.put(KeyUtils.S_NAME, loginResult.getObj().getDevice_name());
                    startActivity(new Intent(this, HomeActivity.class));
                }
                finish();
            } else {
                ToastUtils.toast(this, loginResult.getMsg());
            }
        } else {
            ToastUtils.toast(this, "链接错误,请稍后重试");
        }
    }
}
