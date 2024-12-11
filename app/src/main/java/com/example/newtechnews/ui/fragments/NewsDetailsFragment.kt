package com.example.newtechnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.FragmentNewsDetailsBinding
import com.example.newtechnews.ui.components.ArticleDetailsCard

class NewsDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = NewsDetailsFragment()
    }

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var article: Article


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        article = arguments.let {
            NewsDetailsFragmentArgs.fromBundle(it!!).article
        }
//        setupObservers()
        binding.composeView.setContent {
            ArticleDetailsCard(article = article,isBookmarked = false, onBookmarkClick = {})
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    private fun setupObservers() {

    }

}