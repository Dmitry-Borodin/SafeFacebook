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
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.krenvpravo.safefacebook.Constants
import com.krenvpravo.safefacebook.domain.CustomWebViewClient
import com.krenvpravo.safefacebook.domain.CustomWebViewClient.WebLoadingCallback
import com.krenvpravo.safefacebook.R
import com.krenvpravo.safefacebook.domain.UrlStateKeeper

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class MainActivity : Activity() {

    private var progressDialog: ProgressDialog? = null

    private val webView: WebView by lazy {
        val webView = WebView(this)
        webView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }


    private fun initViews() {
        showProgressDialog()
        webView.setWebViewClient(CustomWebViewClient(this, object : WebLoadingCallback {
            override fun onUrlLoaded() {
                dismissProgressDialog()
            }

            override fun onUrlLoadFailed(reason: String) {
                showRetryDialog(reason)
            }
        }))
        setUpWebSettings(webView.settings)
        setUpCookies(webView)
        setContentView(webView)
        webView.loadUrl(UrlStateKeeper.getLast())
    }

    private fun dismissProgressDialog() {
        if (progressDialog?.isShowing ?: false) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    private fun showProgressDialog() {
        if (progressDialog == null) progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(getString(R.string.progress_dialog_initial_title))
        progressDialog?.show()
    }

    private fun showRetryDialog(whyFailed: String) {
        val sbContent = StringBuilder()
        sbContent.append(getString(R.string.load_fail_content1))
                .append(whyFailed)
                .append(getString(R.string.load_fail_content2))
                .append(getString(R.string.load_fail_content3))

        MaterialDialog.Builder(this)
                .title(getString(R.string.load_fail_title))
                .content(sbContent.toString())
                .positiveText(getString(R.string.retry_button))
                .onPositive { dialog, which ->
                    webView.reload()
                    showProgressDialog()
                }
                .negativeText(getString(R.string.exit_button))
                .onNegative { dialog, which -> finish() }
                .show()
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
