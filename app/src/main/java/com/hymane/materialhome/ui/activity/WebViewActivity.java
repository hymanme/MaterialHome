package com.hymane.materialhome.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hymane.materialhome.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/3/21 0021
 * Description:
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvents() {
        if (webView == null) {
            throw new NullPointerException("must init WebView before invoking initWebView.");
        }
        setTitle(url);
        //启用JS
        webView.getSettings().setJavaScriptEnabled(true);
        //设置是否支持变焦
        webView.getSettings().setSupportZoom(true);
        //启用缓存
        webView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int progress) {
                                           if (progress >= 100) {
                                               progressBar.setVisibility(View.GONE);
                                           } else {
                                               if (progressBar.getVisibility() == View.GONE) {
                                                   progressBar.setVisibility(View.VISIBLE);
                                               }
                                               progressBar.setProgress(progress);
                                           }
                                           super.onProgressChanged(view, progress);
                                       }

                                       @Override
                                       public void onReceivedTitle(WebView view, String title) {
                                           super.onReceivedTitle(view, title);
                                           setTitle(title);
                                       }
                                   }
        );
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.loadUrl(url);
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_open_in_browser) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getMenuID() {
        return R.menu.menu_web_view;
    }

    @Override
    protected void onDestroy() {
        webView.stopLoading();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
