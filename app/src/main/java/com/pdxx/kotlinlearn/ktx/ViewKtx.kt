package com.pdxx.kotlinlearn.ktx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Set view visibility to VISIBLE.
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Set view visibility to INVISIBLE.
 */
fun View.hide() {
    visibility = View.INVISIBLE
}

/**
 * Set view visibility to GONE.
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Inflate a layout for a ViewGroup.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

/**
 * Load an image from a URL into an ImageView using Glide.
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(this.context).load(url).into(this)
}
