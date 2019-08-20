package com.app.ascentsparkmachinetest.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.app.ascentsparkmachinetest.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webViewUrl;
    private String strPageUrl;
    private RelativeLayout relProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar();
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initUI();
        if (getIntent().getStringExtra("detailsUrl") != null) {
            strPageUrl = getIntent().getStringExtra("detailsUrl");
            initWebView(strPageUrl);
        }
        webViewUrl.loadUrl(strPageUrl);
        webViewUrl.setWebViewClient(new MyWebViewClient());
        webViewUrl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (webView.canGoBack()) {
                            webView.goBack();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    private void initUI() {
        webViewUrl = findViewById(R.id.web_view_url);
        relProgressBar = findViewById(R.id.rel_pB);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        webViewUrl.getSettings().setJavaScriptEnabled(true);
        strPageUrl = url;
        webViewUrl.loadUrl(strPageUrl);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           /* view.loadUrl(url);
            return true;*/
            Uri uri = Uri.parse(url);
            if (uri.getHost() != null && uri.getHost().contains(url)) {
                view.loadUrl(url);
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            relProgressBar.setVisibility(View.GONE);
            webViewUrl.setVisibility(View.VISIBLE);
            super.onPageFinished(view, url);
            invalidateOptionsMenu();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            relProgressBar.setVisibility(View.VISIBLE);
            webViewUrl.setVisibility(View.VISIBLE);
            strPageUrl = url;
            super.onPageStarted(view, url, favicon);
            invalidateOptionsMenu();
        }
    }

    // Prevent the back-button from closing the app
    @Override
    public void onBackPressed() {
        if(webViewUrl.canGoBack()) {
            webViewUrl.goBack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
