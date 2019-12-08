package com.krenvpravo.safefacebook

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

object Constants {
    const val MAIN_URL = "https://m.facebook.com/"
    const val MAIN_HOSTNAME: String = "facebook.com"
    const val USERAGENT_CHROME = "Mozilla/5.0 (Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36"
}

data class SiteUrls(val main: String, val links: Map<String, String>)

fun getFacebookUrls() = SiteUrls("https://m.facebook.com/", mutableMapOf(
        "Groups" to "https://m.facebook.com/groups/?seemore",
        "Friends" to "https://m.facebook.com/buddylist.php",
        "Messages" to "https://m.facebook.com/messages/"))

fun getVkUrls() = SiteUrls("https://m.vk.com/", mutableMapOf(
        "Groups" to "https://m.vk.com/groups/?seemore",
        "Friends" to "https://m.vk.com/buddylist.php",
        "Messages" to "https://m.vk.com/messages/"))

/**
 * @author Dmitry Borodin on 2017-01-22.
 */

