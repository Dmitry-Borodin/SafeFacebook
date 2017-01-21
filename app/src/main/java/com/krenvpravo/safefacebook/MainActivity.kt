package com.krenvpravo.safefacebook

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val webView = WebView(this)
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return false
            }
        })
        setUpWebSettings(webView.settings)
        setUpCookies(webView)
        setContentView(webView)
        webView.loadUrl(Constants.MAIN_URL)
    }

    private fun setUpWebSettings(settings: WebSettings) {
        settings.setGeolocationEnabled(false)
        settings.javaScriptEnabled = false
    }

    private fun setUpCookies(webview: WebView) {
        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webview, true)
        } else {
            cookieManager.setAcceptCookie(true)
        }
    }
}
