package com.krenvpravo.safefacebook

import android.os.Build

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

object Constants {
    const val MAIN_URL = "https://facebook.com/"
    const val MAIN_HOSTNAME: String = "facebook.com"
    val mobileAgent = "Mozilla/5.0 (Linux; Android ${Build.VERSION.RELEASE})"
    val desktopAgent = "Mozilla/5.0 (X11; Linux ${System.getProperty("os.arch")})"
}

data class SiteUrls(val main: String, val links: Map<String, String>)

fun getFacebookUrls() = SiteUrls("https://facebook.com/", mutableMapOf(
        "Groups" to "https://facebook.com/groups/?seemore",
        "Friends" to "https://facebook.com/buddylist.php",
        "Messages" to "https://facebook.com/messages/"))

fun getVkUrls() = SiteUrls("https://m.vk.com/", mutableMapOf(
        "Groups" to "https://m.vk.com/groups/?seemore",
        "Friends" to "https://m.vk.com/buddylist.php",
        "Messages" to "https://m.vk.com/messages/"))

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

