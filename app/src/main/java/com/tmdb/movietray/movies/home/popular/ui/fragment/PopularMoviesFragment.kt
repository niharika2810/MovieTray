package com.tmdb.movietray.movies.home.popular.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.movietray.base.ui.loading.LoadingStateAdapter
import com.tmdb.movietray.databinding.FragmentPopularMoviesBinding
import com.tmdb.movietray.movies.home.popular.ui.adapter.PopularMoviesAdapter
import com.tmdb.movietray.movies.home.popular.ui.viewmodel.PopularMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Niharika.Arora
 */
@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private val popularMovieViewModel: PopularMovieViewModel by viewModels()
    private lateinit var binding: FragmentPopularMoviesBinding
    private lateinit var adapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)

        configureList()
        configureStateListener()
        fetchPopularMovies()

        binding.retryButton.setOnClickListener { adapter.retry() }

        return binding.root
    }

    private fun configureList() {
        binding.popularMoviesList.layoutManager = GridLayoutManager(view?.context, 2)
        adapter = PopularMoviesAdapter()
        binding.popularMoviesList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter.retry() },
            footer = LoadingStateAdapter { adapter.retry() }
        )
    }

    private fun fetchPopularMovies() {
        lifecycleScope.launch {
            popularMovieViewModel.getPopularMovies().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun configureStateListener() {
        adapter.addLoadStateListener { loadState ->
            configureViews(loadState)
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(activity, errorState.error.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun configureViews(loadState: CombinedLoadStates) {
        when ( loadState.source.refresh) {
            is LoadState.NotLoading -> {
                binding.popularMoviesList.isVisible = true
                binding.progressBar.isVisible =false
                binding.retryButton.isVisible = false
            }
            is LoadState.Loading -> {
                binding.progressBar.isVisible = true
                binding.retryButton.isVisible = false
            }
            is LoadState.Error -> {
                binding.popularMoviesList.isVisible = false
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = true
            }
        }
    }
}