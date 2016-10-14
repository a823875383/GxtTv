package com.jsqix.gxt.tv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable {
	private boolean iscanPullDown = true;
	private boolean iscanPullUp = true;

	public PullableScrollView(Context context) {
		super(context);
	}

	public PullableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (iscanPullDown) {
			if (getScrollY() == 0)
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public boolean canPullUp() {
		if (iscanPullUp) {
			if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
				return true;
			else
				return false;
		}
		return false;
	}

	public boolean isIscanPullDown() {
		return iscanPullDown;
	}

	public void setIscanPullDown(boolean iscanPullDown) {
		this.iscanPullDown = iscanPullDown;
	}

	public boolean isIscanPullUp() {
		return iscanPullUp;
	}

	public void setIscanPullUp(boolean iscanPullUp) {
		this.iscanPullUp = iscanPullUp;
	}

}
