package com.jsqix.gxt.tv.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.jsqix.gxt.tv.utils.PlayerUtil;

public class MyDialog extends AlertDialog {
	private PlayerUtil playerUtil;

	public MyDialog(Context context) {
		super(context);
		setCancelable(true);
		playerUtil=PlayerUtil.getInstence(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
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