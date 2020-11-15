package com.tmdb.movietray.movies.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.movietray.base.utils.Constants
import com.tmdb.movietray.movies.home.popular.ui.fragment.PopularMoviesFragment
import com.tmdb.movietray.movies.home.toprated.ui.fragment.TopRatedMoviesFragment


/**
 * @author Niharika.Arora
 */
class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val homeTabFragments: Map<Int, () -> Fragment> = mapOf(
        Constants.POPULAR_MOVIE_INDEX to { PopularMoviesFragment() },
        Constants.TOP_RATED_MOVIE_INDEX to { TopRatedMoviesFragment() }
    )

    override fun getItemCount() = homeTabFragments.size

    override fun createFragment(position: Int): Fragment {
        return homeTabFragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}