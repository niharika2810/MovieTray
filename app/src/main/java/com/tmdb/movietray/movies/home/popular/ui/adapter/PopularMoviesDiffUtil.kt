package com.tmdb.movietray.movies.home.popular.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie

/**
 * @author Niharika.Arora
 */
object PopularMoviesDiffUtil : DiffUtil.ItemCallback<PopularMovie>() {

    override fun areItemsTheSame(
        oldItem: PopularMovie,
        newItem: PopularMovie
    ): Boolean = oldItem.movieId == newItem.movieId

    override fun areContentsTheSame(
        oldItem: PopularMovie,
        newItem: PopularMovie
    ): Boolean = oldItem == newItem

}