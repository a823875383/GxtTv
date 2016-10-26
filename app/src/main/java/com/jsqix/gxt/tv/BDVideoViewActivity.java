package com.jsqix.gxt.tv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.baidu.cyberplayer.core.BMediaController;
import com.baidu.cyberplayer.core.BVideoView;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionListener;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionWithParamListener;
import com.baidu.cyberplayer.core.BVideoView.OnErrorListener;
import com.baidu.cyberplayer.core.BVideoView.OnInfoListener;
import com.baidu.cyberplayer.core.BVideoView.OnNetworkSpeedListener;
import com.baidu.cyberplayer.core.BVideoView.OnPlayingBufferCacheListener;
import com.baidu.cyberplayer.core.BVideoView.OnPreparedListener;
import com.google.gson.Gson;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.api.InterfaceJSONPost;
import com.jsqix.gxt.tv.api.JSONPost;
import com.jsqix.gxt.tv.base.MsgAty;
import com.jsqix.gxt.tv.interfaces.AdList;
import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.BitmapHelp;
import com.jsqix.gxt.tv.utils.Constant;
import com.jsqix.gxt.tv.utils.FormatTools;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.SdUtils;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.TimeUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class BDVideoViewActivity extends MsgAty implements ViewFactory,
        OnPreparedListener, OnCompletionListener, OnErrorListener,
        OnInfoListener, OnPlayingBufferCacheListener,
        OnCompletionWithParamListener, OnNetworkSpeedListener,
        InterfaceJSONPost, AdList {
    private final String TAG = "BDVideoViewActivity";

    private String srcPath = "/mnt/usb/sda1/video/";

    /**
     * 您的AK 请到http://console.bce.baidu.com/iam/#/iam/accesslist获取
     */
    private String AK = "0dd259bc8657429896d08434ead80e1e"; // 请录入您的AK !!!
    private String mVideoSource = null;
    @ViewInject(R.id.switcher)
    private ImageSwitcher mSwitcher;
    @ViewInject(R.id.video_view)
    private BVideoView mVV;
    @ViewInject(R.id.controllerbar)
    private BMediaController mVVCtl;

    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;

    private final Object SYNC_Playing = new Object();

    private final int EVENT_PLAY = 0;
    private static final int UI_EVENT_TAKESNAPSHOT = 2;

    private WakeLock mWakeLock = null;
    private static final String POWER_LOCK = "VideoViewPlayingActivity";
    private Toast toast;

    /**
     * 播放状态
     */
    private enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }

    private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;

    /**
     * 记录播放位置
     */
    private int mLastPos = 0;

    private Bitmap bitmap;
    Timer timer1;
    TimerTask task1;
    Timer timer2;
    TimerTask task2;
    int imgCount = 0, ainCount = 0;
    private ACache aCache;
    List<String> localList = new ArrayList<String>();
    List<String> serverList = new ArrayList<String>();
    List<String> pathList = new ArrayList<String>();
    List<Animation> anims = new ArrayList<Animation>();
    public AdList adList;
    private static BDVideoViewActivity sInstence = null;


    private boolean isMsg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bdvideo_view);
        aCache = ACache.get(this);
        aCache.put("onStop", false);
        sInstence = this;
        setAdList(this);
        init();
    }


    private void init() {
        ViewUtils.inject(this);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);
        initUI();
        mSwitcher.setFactory(this);
        mSwitcher.setImageResource(R.mipmap.loading);
        constant = Constant.getInstance(this);
        initData();
    }

    private List<String> getSrcFileName(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        List<String> fileNames = new ArrayList<String>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                fileNames.add(path + files[i].getName());
                Log.d(TAG, fileNames.get(i));
            }
        }
        return fileNames;
    }

    private void initData() {
        // srcPath = Environment.getExternalStorageDirectory().getAbsolutePath()
        // + "/video/";
        anims.add(AnimationUtils.loadAnimation(this, R.anim.scale_rotate));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.zoom_out_enter));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.appear_bottom_right_in));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.flip_horizontal_in));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.appear_top_left_in));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.grow_from_top));
        anims.add(AnimationUtils.loadAnimation(this, R.anim.in_translate_top));
        localList.add("assets/sy_ad1.jpg");
        localList.add("assets/sy_ad2.jpg");

        getAds();
        startQuery();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                display("" + msg.obj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private void display(String path) throws Exception {
        mSwitcher.setInAnimation(anims.get(ainCount));
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
        }
        if ("".equals(path)) {
            path = localList.get(imgCount % localList.size()).substring(7);
            mVV.setVisibility(View.GONE);
            mVVCtl.setVisibility(View.GONE);
            mSwitcher.setVisibility(View.VISIBLE);
            bitmap = FormatTools.getInstance().getBitmapFromStream(
                    getResources().getAssets().open(path), constant.getWidth(),
                    constant.getHeight());
            mSwitcher.setImageDrawable(new BitmapDrawable(bitmap));
        } else {
            if (BitmapHelp.isImage(path)) {
                mVV.setVisibility(View.GONE);
                mVVCtl.setVisibility(View.GONE);
                mSwitcher.setVisibility(View.VISIBLE);
                if (path.startsWith("assets")) {
                    path = path.substring(7);
                    bitmap = FormatTools.getInstance().getBitmapFromStream(
                            getResources().getAssets().open(path),
                            constant.getWidth(), constant.getHeight());
                } else {
                    bitmap = FormatTools.getInstance().getBitmapFromFile(
                            new File(path), constant.getWidth(),
                            constant.getHeight());// 显示图片
                }
                mSwitcher.setImageDrawable(new BitmapDrawable(bitmap));
            } else {
                mVV.setVisibility(View.VISIBLE);
                mVVCtl.setVisibility(View.VISIBLE);
                mSwitcher.setVisibility(View.GONE);
                stop();
                play(path);
            }
        }
        imgCount++;
        ainCount++;
        if (imgCount == pathList.size()) {
            imgCount = 0;
        }
        if (ainCount == anims.size()) {
            ainCount = 0;
        }
    }

    // 开始
    private void start() {
        if (timer1 == null) {
            timer1 = new Timer();
        }
        task1 = new TimerTask() {

            @Override
            public void run() {
                String imagePath = "", url = "";
                if (pathList.size() != 0) {
                    if (imgCount >= pathList.size()) {
                        imgCount = 0;
                    }
                    url = pathList.get(imgCount);
                }
                if ((url.startsWith("http://") || url.startsWith("https://")) && (BitmapHelp.isImage(url) || BitmapHelp.isVideo(url))) {
                    imagePath = BitmapHelp.getImagePath(
                            BDVideoViewActivity.this, url);
                } else {
                    imagePath = url;
                }
                Message msg = new Message();
                msg.obj = imagePath;
                mHandler.sendMessage(msg);

            }
        };
        if (timer1 != null && task1 != null) {
            timer1.schedule(task1, 0, 10 * 1000);
        }

    }

    // 停止
    private void stop() {
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }
        if (task1 != null) {
            task1.cancel();
            task1 = null;
        }

    }

    // 开始
    private void startQuery() {
        if (timer2 == null) {
            timer2 = new Timer();
        }
        task2 = new TimerTask() {

            @Override
            public void run() {
                isMsg = false;
                getAds();
            }
        };
        if (timer2 != null && task2 != null) {
            timer2.schedule(task2, TimeUtils.GET_ADS, TimeUtils.GET_ADS);
        }

    }

    // 停止
    private void stopQuery() {
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
        if (task2 != null) {
            task2.cancel();
            task2 = null;
        }

    }

    /**
     * 获取广告
     */
    private void getAds() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceId", aCache.getAsString(KeyUtils.S_ID));
        JSONPost post = new JSONPost(this, map, this) {

            @Override
            public void onPreExecute() {
                // TODO Auto-generated method stub

            }
        };
        post.execute(HttpUtil.GET_ADVERT);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        try {
            JSONObject ret = new JSONObject(result);
            if ("000".equals(ret.getString("code"))) {
                JSONArray array = ret.getJSONArray("obj");
                serverList.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String name = obj.getString("hyperlink").trim();
//                    String name = array.getString(i).trim();
                    serverList.add(name);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getPathsList();
        if (isMsg == false) {
            if (mVV.isPlaying()) {

            } else {
                stop();
                start();
            }
        } else {
            stop();
            start();
        }
    }

    private void getPathsList() {
        pathList.clear();
        if (serverList.size() == 0) {
            pathList.addAll(localList);
        } else {
            pathList.addAll(serverList);
        }
        List<String> sdCardPaths = SdUtils.getSDCardPaths();
        if (sdCardPaths.size() > 2) {
            for (int i = sdCardPaths.size() - 1; i >= 0; i--) {
                srcPath = sdCardPaths.get(i) + "/video/";
                if (getSrcFileName(srcPath).size() > 0)
                    break;
            }
        }
        if (getSrcFileName(srcPath).size() > 0) {
        } else {
            srcPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/video/";
        }
        //是否把本地文件作为播放
        if (getSrcFileName(srcPath).size() > 0) {
            if (StringUtils.toInt(aCache.getAsString(KeyUtils.S_STATUS)) == 1) {
                pathList = getSrcFileName(srcPath);
            }
        }
        Logger logger = Logger.getLogger("path:");
        logger.error(new Gson().toJson(pathList));
    }

    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFFFFFF);
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return i;
    }

    /**
     * 开始播放
     */
    private void play(String filePath) {
        if (filePath.startsWith("http://") || filePath.split("/").length > 0) {
            mVideoSource = filePath;
        } else {
            List<String> paths = SdUtils.getSDCardPaths();
            if (paths.size() == 0) {
                Toast.makeText(this, "请插入U盘", Toast.LENGTH_LONG).show();
                start();
                return;
            }
            for (String usbPath : paths) {
                File file = new File(usbPath, filePath);
                if (file.exists()) {
                    mVideoSource = file.getAbsolutePath();
                    break;
                }

            }
        }
        if (StringUtils.isEmpty(mVideoSource)) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
            start();
            return;
        }
        mEventHandler.sendEmptyMessage(EVENT_PLAY);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        /**
         * 设置ak
         */
        BVideoView.setAK(AK);
        /**
         * 注册listener
         */
        mVV.setOnPreparedListener(this);
        mVV.setOnNetworkSpeedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnCompletionWithParamListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnPlayingBufferCacheListener(this);
        mVV.setOnInfoListener(this);
        mVVCtl.setPreNextListener(mPreListener, mNextListener);
        mVVCtl.setSnapshotListener(mSnapshotListener);
        /**
         * 关联BMediaController
         */
        mVV.setMediaController(mVVCtl);
        // disable dolby audio effect
        // mVV.setEnableDolby(false);
        /**
         * 设置解码模式
         */
        mVV.setDecodeMode(BVideoView.DECODE_SW);
        /**
         * 开启后台事件处理线程
         */
        mHandlerThread = new HandlerThread("event handler thread",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mEventHandler = new EventHandler(mHandlerThread.getLooper());
    }

    /**
     * 实现切换示例
     */
    private View.OnClickListener mPreListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.v(TAG, "pre btn clicked");
            /**
             * 如果已经开发播放，先停止播放
             */
            if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                mVV.stopPlayback();
            }

            /**
             * 发起一次新的播放任务
             */
            if (mEventHandler.hasMessages(EVENT_PLAY))
                mEventHandler.removeMessages(EVENT_PLAY);
            mEventHandler.sendEmptyMessage(EVENT_PLAY);
        }
    };

    private View.OnClickListener mNextListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.v(TAG, "next btn clicked");
        }
    };

    private View.OnClickListener mSnapshotListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // mVV.takeSnapshot(null);
            mEventHandler.sendEmptyMessage(UI_EVENT_TAKESNAPSHOT);
            Log.v(TAG, "Snapshot clicked");
        }
    };

    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_PLAY:
                    /**
                     * 如果已经播放了，等待上一次播放结束
                     */
                    if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                        synchronized (SYNC_Playing) {
                            try {
                                SYNC_Playing.wait();
                                Log.v(TAG, "wait player status to idle");
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    /**
                     * 设置播放url
                     */

                    mVV.setVideoPath(mVideoSource);
                    mVV.setVideoScalingMode(BVideoView.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//                    /**
//                     * 续播，如果需要如此
//                     */
//                    if (mLastPos > 0) {
//
//                        mVV.seekTo(mLastPos);
//                        mLastPos = 0;
//                    }

                    /**
                     * 显示或者隐藏缓冲提示
                     */
                    mVV.showCacheInfo(true);
                    /**
                     * 开始播放
                     */
                    mVV.start();

                    mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
                    break;
                case UI_EVENT_TAKESNAPSHOT:
                    boolean sdCardExist = isExternalStorageWritable();
                    File sdDir = null;
                    String strpath = null;
                    Bitmap bitmap = null;
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat(
                            "yyyy-MM-dd-HH-mm-ss-SSSS");
                    String time = formatter.format(date);
                    Log.i(TAG, "sdcardExist=" + sdCardExist);
                    if (sdCardExist) {
                        sdDir = Environment.getExternalStorageDirectory();
                        // check the dir is existed or not!.
                        // File file = new File(sdDir.toString());
                        strpath = sdDir.toString() + "/" + time + ".jpg";
                    } else {
                        strpath = "/data/data/" + "/" + time + ".jpg";
                    }
                    Log.e(TAG, "snapshot save path=" + strpath);

                    bitmap = mVV.takeSnapshot();
                    if (bitmap != null) {
                        FileOutputStream os = null;
                        try {
                            os = new FileOutputStream(strpath, false);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                            os.flush();
                            os.close();
                        } catch (Throwable t) {
                            t.printStackTrace();
                        } finally {
                            if (os != null)
                                try {
                                    os.close();
                                } catch (Throwable t) {
                                }
                        }
                        os = null;
                    }

                    if (toast == null) {
                        toast = Toast.makeText(BDVideoViewActivity.this, strpath,
                                Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(strpath);
                    }
                    toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    toast.show();

                    break;
                default:
                    break;
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public void OnCompletionWithParam(int arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * 当前缓冲的百分比， 可以配合onInfo中的开始缓冲和结束缓冲来显示百分比到界面
     */
    @Override
    public void onPlayingBufferCache(int percent) {

    }

    /**
     * 显示当前网速
     */
    @Override
    public void onNetworkSpeedUpdate(int speed) {

    }

    @Override
    public boolean onInfo(int what, int extra) {
        switch (what) {
            /**
             * 开始缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_START:
                // if (mVV.isPlaying()) {
                // mVV.pause();
                // }
                uiHandler.sendEmptyMessage(1);
                break;
            /**
             * 结束缓冲
             */
            case BVideoView.MEDIA_INFO_BUFFERING_END:
                // if (!mVV.isPlaying())
                // mVV.resume();
                uiHandler.sendEmptyMessage(0);
                break;

        }
        return true;
    }

    /**
     * 播放出错
     */
    @Override
    public boolean onError(int what, int extra) {
        uiHandler.sendEmptyMessage(3);
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        start();
        return true;
    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion() {
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        if (!StringUtils.toBool("" + aCache.getAsObject("onStop")))
            start();

    }

    /**
     * 播放准备就绪
     */
    @Override
    public void onPrepared() {
        uiHandler.sendEmptyMessage(0);
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 更新界面
            if (msg.what == 3) {
                Toast.makeText(BDVideoViewActivity.this, "播放出现异常",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    private Constant constant;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.v(TAG, "onPause");
        /**
         * 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
         */
        if (mVV.isPlaying() && (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mLastPos = (int) mVV.getCurrentPosition();
            // when scree lock,paus is good select than stop
            // don't stop pause
            // mVV.stopPlayback();
            mVV.pause();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.v(TAG, "onResume");
        if (null != mWakeLock && (!mWakeLock.isHeld())) {
            mWakeLock.acquire();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
        // stop();/
        // stopQuery();
        // 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
        if (mVV.isPlaying() && (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mLastPos = (int) mVV.getCurrentPosition();
            // don't stop pause
            // mVV.stopPlayback();
            mVV.pause();
        }
        if (toast != null) {
            toast.cancel();
        }
        aCache.put("onStop", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ((mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mLastPos = (int) mVV.getCurrentPosition();
            mVV.stopPlayback();
        }
        if (toast != null) {
            toast.cancel();
        }
        stop();
        stopQuery();
        sInstence = null;
        /**
         * 结束后台事件处理线程
         */
        mHandlerThread.quit();
        Log.v(TAG, "onDestroy");

    }

    @Override
    protected void executeMessage(int instructions) {
        if (instructions == 1001 || instructions == 1003) {
            if (instructions == 1001) {
                aCache.put(KeyUtils.S_STATUS, "-1");
            } else {
                aCache.put(KeyUtils.S_STATUS, "1");
            }
            isMsg = true;
            getAds();

        } else if (instructions == 1002) {
            actualShot();
        }
    }

    public static BDVideoViewActivity getsInstence() {
        return sInstence;
    }

    public static void setsInstence(BDVideoViewActivity sInstence) {
        BDVideoViewActivity.sInstence = sInstence;
    }

    public AdList getAdList() {
        return adList;
    }

    public void setAdList(AdList adList) {
        this.adList = adList;
    }

    @Override
    public void getList() {
        stop();
        Logger logger = Logger.getLogger("USB");
        logger.info("USB BroadcastReceiver");
        getPathsList();
        start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            finish();
            //是不是禁用
        }
        return super.dispatchTouchEvent(ev);
    }

}
