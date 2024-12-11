package com.example.newtechnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.FragmentNewsDetailsBinding
import com.example.newtechnews.ui.components.ArticleDetailsCard
import com.example.newtechnews.ui.viewmodel.NewsDetailsViewModel

class NewsDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = NewsDetailsFragment()
    }

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsDetailsViewModel by viewModels()

    private lateinit var article: Article


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        article = arguments.let {
            NewsDetailsFragmentArgs.fromBundle(it!!).article
        }
        viewModel.isArticleInBookmark(article)
        binding.composeView.setContent {
            val isBookmarked by viewModel.isBookmarked.collectAsState()
            ArticleDetailsCard(article = article,
                isBookmarked = isBookmarked,
                onBookmarkClick = { viewModel.toggleBookmark(article) }
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}