package com.movietray.base.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Niharika.Arora
 */
abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindItem(item: T?)
}