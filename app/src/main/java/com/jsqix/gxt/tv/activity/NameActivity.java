package com.jsqix.gxt.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.R;
import com.lidroid.xutils.view.annotation.event.OnClick;


public class NameActivity extends BaseAty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
    }

    @OnClick(R.id.bt_confirm)
    public void confirmClick(View v) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
