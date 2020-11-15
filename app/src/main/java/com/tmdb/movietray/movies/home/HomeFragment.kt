package com.tmdb.movietray.movies.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.movietray.base.utils.Constants
import com.movietray.base.utils.UtilityClass
import com.tmdb.movietray.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var listener: HomeCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        configureListener(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener?.onBackPressedFromHome()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun configureListener(context: Context) {
        listener = UtilityClass.getParent(this, HomeCallback::class.java)
        if (listener == null) {
            throw ClassCastException(
                StringBuilder(context.javaClass.simpleName).append("must implement")
                    .append(HomeFragment::class.java.simpleName).toString()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.pager.adapter = HomePagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            Constants.POPULAR_MOVIE_INDEX -> "Popular"
            Constants.TOP_RATED_MOVIE_INDEX -> "Top rated"
            else -> null
        }
    }

    interface HomeCallback {
        fun onBackPressedFromHome()
    }
}