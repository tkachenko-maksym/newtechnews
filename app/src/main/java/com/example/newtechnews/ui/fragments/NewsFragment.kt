package com.example.newtechnews.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newtechnews.R
import com.example.newtechnews.databinding.FragmentNewsBinding
import com.example.newtechnews.ui.adapters.NewsAdapter
import com.example.newtechnews.ui.viewmodel.NewsViewModel
import com.example.newtechnews.utils.NetworkUtils
import java.util.concurrent.atomic.AtomicBoolean

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val isDataFetching = AtomicBoolean(false)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_app_bar, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView =
            searchItem?.actionView as androidx.appcompat.widget.SearchView // Correct cast

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.onSearchQueryChanged(query)
                return true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        setupObservers()
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.cleanDatabase()
            viewModel.fetchNews()
        }
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            Toast.makeText(
                requireContext(),
                "No internet connection! Cached news is displayed.",
                Toast.LENGTH_LONG
            ).show()
        }
        viewModel.loadBookmarkedArticles()
        Log.d("onViewCreated", "onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.loadCachedArticles()
        }
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            newsAdapter = NewsAdapter(
                onItemClick = { article ->
                    findNavController().navigate(
                        NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(article)
                    )
                },
                onBookmarkClick = { article ->
                    viewModel.toggleBookmark(article)
                },
                bookmarkedArticles = viewModel.bookmarkedArticles
            )
            newsAdapter.setHasStableIds(true)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        if (!isDataFetching.getAndSet(true)) {
                            viewModel.fetchNextPage()
                        }
                    }
                }
            })
        }
    }


    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                viewModel.cleanDatabase()
                viewModel.fetchNews()
            }
        }

    }

    private fun setupObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles.isNullOrEmpty()) {
                binding.newsRecyclerView.isVisible = false
            } else {
                binding.newsRecyclerView.isVisible = true
                newsAdapter.setData(articles)
                newsAdapter.notifyDataSetChanged()
            }
        }
        viewModel.bookmarkedArticles.observe(viewLifecycleOwner) { bookmarkedArticles ->
            newsAdapter.notifyDataSetChanged()
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            isDataFetching.set(isLoading)
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                binding.newsRecyclerView.isVisible = false
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.reset()

        _binding = null
    }
}