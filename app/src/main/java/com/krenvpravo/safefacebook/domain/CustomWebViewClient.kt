package com.krenvpravo.safefacebook.domain

import android.content.Context
import android.net.http.SslError
import android.webkit.*
import com.krenvpravo.safefacebook.R

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class CustomWebViewClient( val context: Context, val callback: WebLoadingCallback) : WebViewClient() {

    private val FACEBOOK_HOSTNAME: String = "facebook.com"

    @Suppress("RedundantIf")
    override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
        if (FACEBOOK_HOSTNAME in request) {
            return false //not start browser
        } else {
            return true //open in browser
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        callback.onUrlLoaded()
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        callback.onUrlLoadFailed(error?.toString() ?: context.getString(R.string.unknown_loading_error))
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        callback.onUrlLoadFailed(errorResponse?.toString() ?: context.getString(R.string.unknown_loading_error))
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        callback.onUrlLoadFailed(error?.toString() ?: context.getString(R.string.unknown_loading_error))
    }

    interface WebLoadingCallback {
        fun onUrlLoaded()
        fun onUrlLoadFailed(reason: String)
    }
}
