package com.example.newtechnews.ui.news

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newtechnews.R
import com.example.newtechnews.data.mock_articles
import com.example.newtechnews.databinding.FragmentNewsBinding
import com.example.newtechnews.ui.adapters.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.atomic.AtomicBoolean

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
//        setupSearchBar()
        setupSwipeRefresh()
        setupObservers()

        // Initial news fetch
        viewModel.fetchNews()
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            newsAdapter = NewsAdapter(
                onItemClick = { article ->

                    findNavController().navigate(
                        NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment()
                    )
                },
                onBookmarkClick = { article ->
                    // TODO: Implement bookmark functionality
                }
            )
            newsAdapter.setHasStableIds(true)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter=newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    Log.d("visibleItemCount", visibleItemCount.toString())
                    Log.d("totalItemCount", totalItemCount.toString())
                    Log.d("firstVisibleItemPosition", firstVisibleItemPosition.toString())
                    Log.d("isDataFetching", isDataFetching.get().toString())
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

//    private fun setupSearchBar() {
//        binding.searchBar.onQueryTextSubmit(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.fetchNews(query = query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean = false
//        })
//    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchNews()
        }
    }

    private fun setupObservers() {
        viewModel.newsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NewsState.Success -> {
                    binding.newsRecyclerView.isVisible = true
                    newsAdapter.setData(state.articles)
                    newsAdapter.notifyDataSetChanged()
                }

                is NewsState.Error -> {
                    binding.newsRecyclerView.isVisible = false
                    Snackbar.make(
                        binding.root,
                        state.message,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            isDataFetching.set(isLoading)
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.reset()
        _binding = null
    }
}