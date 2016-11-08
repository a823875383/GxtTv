package com.jsqix.gxt.tv.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.utils.PlayerUtil;

public class LoadingDialog extends Dialog {

    private Context mContext;
    private LayoutInflater inflater;
    private LayoutParams lp;
    private TextView loadtext;
    private PlayerUtil playerUtil;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        playerUtil = PlayerUtil.getInstence(context);
        setCancelable(true);
        this.mContext = context;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.loadingdialog, null);
        loadtext = (TextView) layout.findViewById(R.id.loading_text);
        setContentView(layout);
        WindowManager m = ((Activity) context).getWindowManager();
        // 设置window属性
        lp = getWindow().getAttributes();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        lp.gravity = Gravity.CENTER;
        lp.alpha = 1.0f; // 设置本身透明度
        lp.dimAmount = 0; // 设置黑暗度
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);

    }

    public LoadingDialog setLoadText(String content) {
        loadtext.setText(content);
        return this;
    }

    public LoadingDialog setLoadText(int sid) {
        loadtext.setText(mContext.getResources().getString(sid));
        return this;
    }

    public void show() {
        try {
            if (this != null && !isShowing())
                super.show();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void dismiss() {
        try {
            if (this != null && isShowing())
                super.dismiss();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // time = System.currentTimeMillis();
            playerUtil.refreshTime();
        }
        return super.dispatchTouchEvent(ev);
    }
}