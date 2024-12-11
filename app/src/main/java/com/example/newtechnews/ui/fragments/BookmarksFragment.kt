package com.example.newtechnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.FragmentBookmarksBinding
import com.example.newtechnews.ui.components.ArticleListCard
import com.example.newtechnews.ui.viewmodel.BookmarksViewModel

class BookmarksFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarksFragment()
    }

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        viewModel.loadBookmarkedArticles()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bookmarkedArticles.observe(viewLifecycleOwner) { bookmarkedArticles ->
            binding.composeView.setContent {
                BookmarksContent(articles = bookmarkedArticles)
            }
        }
    }

    @Composable
    fun BookmarksContent(articles: List<Article>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(6.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(articles) { article ->
                ArticleListCard(
                    article = article,
                    onClick = {
                        findNavController().navigate(
                            BookmarksFragmentDirections.actionBookmarksFragmentToNewsDetailsFragment(article)
                        )
                    }
                )
            }
        }

        fun onArticleClick(article: Int) {

        }
    }
}
//onItemClick = { article ->
//
//    findNavController().navigate(
//        NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(article)
//    )
//},