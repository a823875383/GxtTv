package com.jsqix.gxt.tv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.utils.DensityUtil;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.view.TitleBar;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WebActivity extends BaseAty {
    @ViewInject(R.id.titleBar)
    private TitleBar titleBar;
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.video_view)
    private FrameLayout videoView;
    private String urlString = "";

    private ProgressBar progressbar;
    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initBar();
    }

    private void initBar() {
        titleBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
        titleBar.showRight(true);
        titleBar.setRightBackground(R.mipmap.icon_top_close);
        titleBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void executeMessage(int instructions) {
        if (instructions == 1001) {//禁用
            aCache.put(KeyUtils.S_STATUS, "-1");
            Intent intent = new Intent(this, BDVideoViewActivity.class);
            startActivity(intent);
        } else if (instructions == 1003) {//启用

        } else if (instructions == 1002) {
            actualShot();
        }

    }


    @Override
    public void initView() {
        urlString = getIntent().getExtras().getString("url");

        progressbar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 3)));
        progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true); // 显示放大缩小 controler
        settings.setSupportZoom(true); // 可以缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
        settings.setUseWideViewPort(true); // 为图片添加放大缩小功能

        webView.addView(progressbar);
        webView.loadUrl(urlString);
        webView.setWebChromeClient(chromeClient);
        webView.setWebViewClient(viewClient);
    }

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url,
                                   String message, final JsResult result) {
            // TODO Auto-generated method stub
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleBar.setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE) {
                    progressbar.setVisibility(View.VISIBLE);
                }
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            webView.setVisibility(View.GONE);
            titleBar.setVisibility(View.GONE);
            //如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHideCustomView() {
            if (xCustomView == null)//不是全屏播放状态
                return;
            xCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            videoView.removeView(xCustomView);
            xCustomView = null;
            videoView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            webView.setVisibility(View.VISIBLE);
            titleBar.setVisibility(View.VISIBLE);
        }
    };
    private WebViewClient viewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
//            showLoading();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
//            hideLoading();
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 设置点击网页里面的链接还是在当前的webview里跳转
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, android.net.http.SslError error) {
            // 设置webview处理https请求
            handler.proceed();
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // 加载页面报错时的处理
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
