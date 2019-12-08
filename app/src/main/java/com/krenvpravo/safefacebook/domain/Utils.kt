package com.krenvpravo.safefacebook.domain

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author Dmitry Borodin on 12/8/19.
 */

fun Context.isNetworkAvailable(): Boolean {
	val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val activeNetworkInfo = connectivityManager.activeNetworkInfo
	return activeNetworkInfo != null
}