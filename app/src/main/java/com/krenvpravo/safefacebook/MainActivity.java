package com.krenvpravo.safefacebook;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

public class MainActivity extends Activity {
    public static final String FACEbOOK_URL = "https://m.facebook.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        WebView webview = new WebView(this);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        setUpCookies(webview);
        setContentView(webview);
        webview.loadUrl(FACEbOOK_URL);
    }

    private void setUpCookies(WebView webview) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webview, true);
        } else {
            cookieManager.setAcceptCookie(true);
        }
    }
}
