package com.tmdb.movietray.movies.home.toprated.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import com.movietray.base.extension.toTransitionGroup
import com.movietray.base.ui.BaseViewHolder
import com.tmdb.movietray.databinding.ListItemTopRatedMoviesBinding
import com.tmdb.movietray.movies.home.HomeFragmentDirections
import com.tmdb.movietray.movies.home.toprated.data.local.db.entity.TopRatedMovie

/**
 * @author Niharika.Arora
 */
class TopRatedMoviesAdapter :
    PagingDataAdapter<TopRatedMovie, TopRatedMoviesAdapter.ViewHolder>(
        TopRatedMoviesDiffUtil
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it as TopRatedMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemTopRatedMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ListItemTopRatedMoviesBinding) :
        BaseViewHolder<TopRatedMovie>(binding.root) {
        override fun bindItem(item: TopRatedMovie?) {
            binding.movie = item
            binding.viewHolder = this
            onItemClick(item)
            binding.executePendingBindings()
        }

        private fun onItemClick(item: TopRatedMovie?) {
            binding.clickListener = View.OnClickListener {
                if (item != null) {
                    val destination =
                        HomeFragmentDirections
                            .navToItemDetailFragment(
                                item.poster,
                                item.title,
                                item.description
                            )
                    val extras = FragmentNavigatorExtras(
                        binding.movieTitle.toTransitionGroup(),
                        binding.itemImage.toTransitionGroup(),
                    )
                    it.findNavController().navigate(destination, extras)
                }
            }
        }

    }

}