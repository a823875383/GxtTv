package com.jsqix.gxt.tv.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.view.LoadingDialog;
import com.jsqix.gxt.tv.view.MyDialog;
import com.lidroid.xutils.ViewUtils;

import cn.jpush.android.api.InstrumentedActivity;

public class BaseAty extends InstrumentedActivity {
    private String TAG = "BaseAty";
    public ACache aCache;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        aCache = ACache.get(this, "User");

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.inject(this);
    }

    public boolean isLogin() {
        if (StringUtils.isEmpty(aCache.getAsString("id"))) {
            return false;
        }
        return true;
    }

    public void showToast(String text) {
        if (text != null) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }

    public void showToast(int id) {
        if (id != 0) {
            String text = getResources().getString(id);
            if (text != null) {
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void alert(String titleMsg, String msg, String butStr) {
        AlertDialog builder = new MyDialog(this);
        builder.setTitle(titleMsg);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setButton(butStr, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void showLoading() {
        try {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(this);
            }
            loadingDialog.setLoadText(R.string.loading_default_text);
            loadingDialog.show();
        } catch (Exception e) {
        }

    }

    public void showLoading(String text) {
        if (text != null) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(this);
            }
            loadingDialog.setLoadText(text);
            loadingDialog.show();
        }
    }

    public void showLoading(int id) {
        if (id != 0) {
            String text = getResources().getString(id);
            if (text != null) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(this);
                }
                loadingDialog.setLoadText(text);
                loadingDialog.show();
            }
        }
    }

    public void hideLoading() {
        try {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

}
