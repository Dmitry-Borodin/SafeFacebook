package com.krenvpravo.safefacebook.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.webkit.*
import android.widget.Toast
import com.krenvpravo.safefacebook.R


/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class CustomWebViewClient(val activityContext: Context, val callback: WebLoadingCallback) : WebViewClient() {

    private val FACEBOOK_HOSTNAME: String = "facebook.com"

    @Suppress("RedundantIf")
    override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
        if (FACEBOOK_HOSTNAME in request) {
            return false //show in web view
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(request))
            if (intent.resolveActivity(activityContext.packageManager) != null) {
                activityContext.startActivity(intent)
            } else {
                Toast.makeText(activityContext, activityContext.getString(R.string.webbrowser_not_sound), Toast.LENGTH_LONG).show()
            }
            return true //NOT show in web view - i opened browser
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        callback.onLoadedNewUrl(url ?: "")
        callback.onUrlLoaded()
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        callback.onUrlLoadFailed(error?.toString() ?: activityContext.getString(R.string.unknown_loading_error))
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        callback.onUrlLoadFailed(errorResponse?.toString() ?: activityContext.getString(R.string.unknown_loading_error))
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        callback.onUrlLoadFailed(error?.toString() ?: activityContext.getString(R.string.unknown_loading_error))
    }

    interface WebLoadingCallback {
        fun onUrlLoaded()
        fun onUrlLoadFailed(reason: String)
        fun onLoadedNewUrl(newUrl: String)
    }
}
