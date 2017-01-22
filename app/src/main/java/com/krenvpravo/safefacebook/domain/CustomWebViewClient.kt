package com.krenvpravo.safefacebook.domain

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class CustomWebViewClient(val callback: WebLoadingCallback) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return false //not start browser
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        callback.onUrlLoaded()
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        callback.onUrlLoadFialed()
    }

    interface WebLoadingCallback {
        fun onUrlLoaded()
        fun onUrlLoadFialed()
    }
}
