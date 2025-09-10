package com.pdxx.kotlinlearn.ktx

import android.content.res.Resources
import android.util.Patterns

/**
 * Convert dp to px.
 */
fun Float.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

/**
 * Convert px to dp.
 */
fun Int.pxToDp(): Float {
    return (this / Resources.getSystem().displayMetrics.density)
}

/**
 * Check if a string is a valid email address.
 */
fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Check if a string is a valid URL.
 */
fun String.isUrl(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}
