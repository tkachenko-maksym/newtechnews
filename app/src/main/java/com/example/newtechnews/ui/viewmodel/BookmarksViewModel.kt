package com.example.newtechnews.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class BookmarksViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository: NewsRepository = NewsRepository(application)
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _filteredArticles = MutableLiveData<List<Article>>()
    val filteredArticles: LiveData<List<Article>> = _filteredArticles

    private val _bookmarkedArticles = MutableLiveData<List<Article>>()
    val bookmarkedArticles: LiveData<List<Article>> = _bookmarkedArticles

    private var searchJob: Job? = null
    init {
        observeSearchQuery()
        loadBookmarkedArticles()
    }
    private fun observeSearchQuery() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchQuery.asFlow()
                .debounce(300L)
                .distinctUntilChanged()
                .collect { query ->
                    filterArticles(query)
                }
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun filterArticles(query: String) {
        val currentArticles = _bookmarkedArticles.value ?: emptyList()
        val filtered = if (query.isEmpty()) {
            currentArticles
        } else {
            currentArticles.filter { article ->
                article.title.contains(query, ignoreCase = true) ||
                        article.description?.contains(query, ignoreCase = true) == true
            }
        }
        _filteredArticles.postValue(filtered)
    }
     fun loadBookmarkedArticles() {
        viewModelScope.launch {
            val bookmarked = repository.getBookmarkedArticles()
            _bookmarkedArticles.postValue(bookmarked)
            _filteredArticles.postValue(bookmarked)
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }
}