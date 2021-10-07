package com.tmdb.movietray.movies.home.toprated.ui.fragment

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
import com.tmdb.movietray.databinding.FragmentTopRatedMoviesBinding
import com.tmdb.movietray.movies.home.toprated.ui.adapter.TopRatedMoviesAdapter
import com.tmdb.movietray.movies.home.toprated.ui.viewmodel.TopRatedMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Niharika.Arora
 */
@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {

    private val topRatedMovieViewModel: TopRatedMovieViewModel by viewModels()
    private lateinit var binding: FragmentTopRatedMoviesBinding
    private lateinit var adapter: TopRatedMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)

        configureList()
        configureStateListener()
        fetchTopRatedMovies()

        binding.retryButton.setOnClickListener { adapter.retry() }

        return binding.root
    }

    private fun configureList() {
        binding.topRatedMoviesList.layoutManager = GridLayoutManager(view?.context, 2)
        adapter = TopRatedMoviesAdapter()
        binding.topRatedMoviesList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter.retry() },
            footer = LoadingStateAdapter { adapter.retry() }
        )
    }

    private fun fetchTopRatedMovies() {
        lifecycleScope.launch {
            topRatedMovieViewModel.getTopRatedMovies().collectLatest {
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
        when (loadState.source.refresh) {
            is LoadState.NotLoading -> {
                binding.topRatedMoviesList.isVisible = true
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = false
                if(adapter.itemCount<1){
                    binding.retryButton.isVisible=true
                }
            }
            is LoadState.Loading -> {
                binding.progressBar.isVisible = true
                binding.retryButton.isVisible = false
            }
            is LoadState.Error -> {
                binding.topRatedMoviesList.isVisible = false
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = true
            }
        }
    }
}