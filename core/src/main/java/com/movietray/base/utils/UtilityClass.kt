package com.movietray.base.utils

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment

/**
 * @author Niharika.Arora
 */
object UtilityClass {

    @JvmStatic
    fun <T> getParent(@NonNull fragment: Fragment, @NonNull parentClass: Class<T>): T? {
        val parentFragment = fragment.parentFragment
        if (parentClass.isInstance(parentFragment)) {
            return parentFragment as T
        } else if (parentClass.isInstance(fragment.activity)) {
            return fragment.activity as T
        }
        return null
    }
}