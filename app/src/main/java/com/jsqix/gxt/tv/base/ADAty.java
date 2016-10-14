package com.jsqix.gxt.base;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.utils.PlayerUtil;

/**
 * Created by dongqing on 2016/10/12.
 */

public class ADAty extends BaseAty implements TextWatcher {
    public PlayerUtil playerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerUtil = PlayerUtil.getInstence(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerUtil.refreshTime();
        playerUtil.startTask();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
                return true;
            } else {
                playerUtil.refreshTime();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            playerUtil.refreshTime();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    private GestureDetector gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {

        // 鼠标按下的时候，会产生onDown。由一个ACTION_DOWN产生。
        public boolean onDown(MotionEvent event) {

            return false;
        }

        // 用户按下触摸屏、快速移动后松开,这个时候，你的手指运动是有加速度的。
        // 由1个MotionEvent ACTION_DOWN,
        // 多个ACTION_MOVE, 1个ACTION_UP触发
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            return false;
        }

        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
        public void onLongPress(MotionEvent event) {

        }

        // 滚动事件，当在触摸屏上迅速的移动，会产生onScroll。由ACTION_MOVE产生
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // distanceX：距离上次产生onScroll事件后，X抽移动的距离
        // distanceY：距离上次产生onScroll事件后，Y抽移动的距离
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {

            return false;
        }

        //点击了触摸屏，但是没有移动和弹起的动作。onShowPress和onDown的区别在于
        //onDown是，一旦触摸屏按下，就马上产生onDown事件，但是onShowPress是onDown事件产生后，
        //一段时间内，如果没有移动鼠标和弹起事件，就认为是onShowPress事件。
        public void onShowPress(MotionEvent event) {


        }

        // 轻击触摸屏后，弹起。如果这个过程中产生了onLongPress、onScroll和onFling事件，就不会
        // 产生onSingleTapUp事件。
        public boolean onSingleTapUp(MotionEvent event) {
            playerUtil.refreshTime();
            return false;
        }

    });

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (0 != (event.getSource() & InputDevice.SOURCE_CLASS_POINTER)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_SCROLL:
                    playerUtil.refreshTime();
                    break;
                case MotionEvent.ACTION_MOVE:
                    playerUtil.refreshTime();
                    break;
            }
        }
        return super.onGenericMotionEvent(event);
    }

    @Override
    protected void onPause() {
        playerUtil.stopTask();
        super.onPause();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        playerUtil.refreshTime();

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }
}
