package com.krenvpravo.safefacebook.domain

import com.krenvpravo.safefacebook.Constants

/**
 * @author Dmitry Borodin on 2017-01-22.
 */
object UrlStateKeeper {
    private val urlStack = ArrayList<String>()

    fun put(url: String) {
        if (url != getLast()) {
            urlStack.add(url)
        }
    }

    fun popTop(): String? {
        if (urlStack.isNotEmpty()) {
            urlStack.removeAt(urlStack.lastIndex)
        }
        if (urlStack.isEmpty()) {
            return null
        }
        return urlStack.last()
    }

    fun getLast(): String {
        if (urlStack.isEmpty()) {
            return Constants.MAIN_URL
        }
        return urlStack.last()
    }
}