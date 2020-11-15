package com.tmdb.movietray.movies.home.toprated.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie

/**
 * @author Niharika.Arora
 */
object TopRatedMoviesDiffUtil : DiffUtil.ItemCallback<TopRatedMovie>() {


    override fun areItemsTheSame(
        oldItem: TopRatedMovie,
        newItem: TopRatedMovie
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: TopRatedMovie,
        newItem: TopRatedMovie
    ): Boolean {
        return oldItem.movieId == newItem.movieId
    }
}