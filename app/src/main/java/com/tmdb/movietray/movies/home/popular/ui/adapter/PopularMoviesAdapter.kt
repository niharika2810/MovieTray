package com.tmdb.movietray.movies.home.popular.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import com.movietray.base.extension.toTransitionGroup
import com.movietray.base.ui.BaseViewHolder
import com.tmdb.movietray.databinding.ListItemPopularMoviesBinding
import com.tmdb.movietray.movies.home.HomeFragmentDirections
import com.tmdb.movietray.movies.home.popular.data.local.entity.PopularMovie

class PopularMoviesAdapter :
    PagingDataAdapter<PopularMovie, PopularMoviesAdapter.ViewHolder>(PopularMoviesDiffUtil) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindItem(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemPopularMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ListItemPopularMoviesBinding) :
        BaseViewHolder<PopularMovie>(binding.root) {
        override fun bindItem(item: PopularMovie?) {
            binding.movie = item
            binding.viewHolder = this
            onItemClick(item)
            binding.executePendingBindings()
        }

        private fun onItemClick(item: PopularMovie?) {
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