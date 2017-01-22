package com.krenvpravo.safefacebook.ui

import android.app.Activity
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.krenvpravo.safefacebook.Constants
import com.krenvpravo.safefacebook.domain.CustomWebViewClient
import com.krenvpravo.safefacebook.domain.CustomWebViewClient.WebLoadingCallback
import com.krenvpravo.safefacebook.R
import com.krenvpravo.safefacebook.domain.UrlStateKeeper

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class MainActivity : Activity() {

    private val webView: WebView by lazy {
        val webView = WebView(this)
        webView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val progressdialog = ProgressDialog(this)
        progressdialog.setTitle(getString(R.string.progress_dialog_initial_title))
        webView.setWebViewClient(CustomWebViewClient(object : WebLoadingCallback{
            override fun onUrlLoaded() {
                progressdialog.dismiss()
            }

            override fun onUrlLoadFialed() {
                onLoadFailed()
            }
        }))
        setUpWebSettings(webView.settings)
        setUpCookies(webView)
        setContentView(webView)
        webView.loadUrl(UrlStateKeeper.getLast())
    }

    private fun onLoadFailed() {
        webView.reload()
    }


    private fun setUpWebSettings(settings: WebSettings) {
        settings.setGeolocationEnabled(false)
        settings.javaScriptEnabled = false
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    }

    private fun setUpCookies(webView: WebView) {
        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true)
        } else {
            cookieManager.setAcceptCookie(true)
        }
    }
}
