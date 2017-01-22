package com.krenvpravo.safefacebook.domain

import com.krenvpravo.safefacebook.Constants
import java.util.*

/**
 * @author Dmitry Borodin on 2017-01-22.
 */
object UrlStateKeeper {
    val backstack: MutableList<String> by lazy {
        ArrayList<String>()
    }

    fun put(url : String) {
        backstack.add(url);
    }

    fun pup() : String? {
        if (backstack.isEmpty()) {
            return null
        }
        val value = backstack.last()
        backstack.removeAt(backstack.lastIndex)
        return value
    }

    fun getLast() : String {
        if (backstack.isEmpty()) {
            return Constants.MAIN_URL
        }
        return backstack.last()
    }
}