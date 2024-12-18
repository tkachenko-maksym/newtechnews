package com.example.newtechnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import com.example.newtechnews.R
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.FragmentBookmarksBinding
import com.example.newtechnews.ui.components.ArticleListCard
import com.example.newtechnews.ui.viewmodel.BookmarksViewModel

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_app_bar, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView // Correct cast

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query submission for News
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.onSearchQueryChanged(query ?: "")
                return true
            }
        })
    }
    override fun onResume() {
        super.onResume()
        viewModel.loadBookmarkedArticles()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filteredArticles.observe(viewLifecycleOwner) { bookmarkedArticles ->
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

    }
}