package com.movietray.base.ui.loading

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.core.databinding.LoadingViewBinding

class LoadStateViewHolder(
    private val binding: LoadingViewBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retry.setOnClickListener { retry.invoke() }
    }

    fun bindViewHolder(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
        binding.retry.isVisible = loadState is LoadState.Error
        binding.progress.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun createViewHolder(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LoadingViewBinding.inflate(layoutInflater, parent, false)
            return LoadStateViewHolder(binding, retry)
        }
    }
}