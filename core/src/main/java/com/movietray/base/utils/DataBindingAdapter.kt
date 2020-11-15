package com.movietray.base.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.movietray.base.utils.Constants
import com.tmdb.core.R

/**
 * @author Niharika.Arora
 */
object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, relativeImageUrl: String?) {
        val requestOptions = RequestOptions().priority(Priority.IMMEDIATE)
            .placeholder(R.drawable.ic_movie_placeholder)

        Glide.with(view.context).load(Constants.IMAGE_BASE_URL + relativeImageUrl)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }
}