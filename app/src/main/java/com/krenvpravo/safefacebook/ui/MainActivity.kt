package com.krenvpravo.safefacebook.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import com.afollestad.materialdialogs.MaterialDialog
import com.krenvpravo.safefacebook.Constants
import com.krenvpravo.safefacebook.R
import com.krenvpravo.safefacebook.domain.CustomWebViewClient
import com.krenvpravo.safefacebook.domain.CustomWebViewClient.WebLoadingCallback
import com.krenvpravo.safefacebook.domain.UrlStateKeeper


/**
 * @author Dmitry Borodin on 2017-01-22.
 */

class MainActivity : Activity() {

    private val progressDialog by lazy { ProgressDialog(this) }

    private val webView: WebView by lazy {
        val webView = WebView(this)
        webView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    private fun initViews() {
        showProgressDialog()
        webView.webViewClient = CustomWebViewClient(this, object : WebLoadingCallback {
            override fun onNewPageLoadStarted() {
                showProgressDialog()
            }

            override fun onLoadedNewUrl(newUrl: String) {
                UrlStateKeeper.put(newUrl)
            }

            override fun onUrlLoaded() {
                dismissProgressDialog()
            }

            override fun onUrlLoadFailed(reason: String) {
                showRetryDialog(reason)
            }
        })
        setUpWebSettings(webView.settings)
        setUpCookies(webView)
        setContentView(webView)

        webView.loadUrl(UrlStateKeeper.getLast())
    }

    override fun onBackPressed() {
        val previousUrl = UrlStateKeeper.skipTop()
        if (previousUrl != null) {
            webView.loadUrl(previousUrl)
        } else {
            super.onBackPressed()
        }
    }

    private fun dismissProgressDialog() {
        progressDialog.dismiss()
    }

    private fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.progress_dialog_initial_title))
        progressDialog.show()
    }

    private fun showRetryDialog(whyFailed: String) {
        val sbContent = StringBuilder().apply {
            append(getString(R.string.load_fail_content1))
            append(whyFailed)
            append(getString(R.string.load_fail_content2))
            append(getString(R.string.load_fail_content3))
        }
        MaterialDialog.Builder(this)
                .title(getString(R.string.load_fail_title))
                .content(sbContent.toString())
                .positiveText(getString(R.string.retry_button))
                .onPositive { _, _ ->
                    if (isNetworkAvailable()) {
                        webView.reload()
                        showProgressDialog()
                    } else {
                        showRetryDialog(getString(R.string.no_network_connection))
                    }
                }
                .negativeText(getString(R.string.exit_button))
                .onNegative { dialog, which -> finish() }
                .show()
    }

    private fun setUpWebSettings(settings: WebSettings) {
        settings.setGeolocationEnabled(false)
        settings.javaScriptEnabled = false
        settings.setAppCacheEnabled(true)
        settings.userAgentString = Constants.USERAGENT_CHROME
        settings.setAppCacheEnabled(true)
        settings.setGeolocationEnabled(false)
        settings.setAppCachePath(cacheDir.path)
    }

    private fun setUpCookies(webView: WebView) {
        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true)
        } else {
            cookieManager.setAcceptCookie(true)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}
