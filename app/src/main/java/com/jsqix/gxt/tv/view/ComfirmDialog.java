package com.jsqix.gxt.tv.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.utils.PlayerUtil;


public class ComfirmDialog extends Dialog {
	private Context mContext;
	private LayoutParams lp;
	private PlayerUtil playerUtil;

	public ComfirmDialog(Context context) {
		super(context, R.style.CampainDialog);
		this.mContext = context;
		setCanceledOnTouchOutside(true);
		setCancelable(true);
		playerUtil = PlayerUtil.getInstence(context);

	}

	public void setView(View view) {
		setContentView(view);
		WindowManager m = ((Activity) mContext).getWindowManager();
		// 设置window属性
		lp = getWindow().getAttributes();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		lp.gravity = Gravity.CENTER;
		lp.alpha = 1.0f; // 设置本身透明度
		lp.dimAmount = 0.6f; // 设置黑暗度
		getWindow().setAttributes(lp);
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

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (0 != (event.getSource() & InputDevice.SOURCE_CLASS_POINTER)) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_SCROLL:
				playerUtil.refreshTime();
				break;
			}
		}
		return super.onGenericMotionEvent(event);
	}

}
