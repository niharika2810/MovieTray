package com.tmdb.movietray.movies.home.details.fragment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.movietray.base.utils.Constants
import com.tmdb.movietray.databinding.FragmentDetailsBinding

/**
 * @author Niharika.Arora
 */
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_bottom)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = args.imageUrl
        val title = args.title
        val description = args.description
        binding.movieTitle.text = title
        binding.movieDescription.text = description
        Glide.with(binding.itemImage.context).load(Constants.IMAGE_BASE_URL + imageUrl)
            .into(binding.itemImage)
    }
}