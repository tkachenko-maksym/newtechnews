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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository: NewsRepository = NewsRepository(application)

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _bookmarkedArticles = MutableLiveData<List<Article>>()
    val bookmarkedArticles: LiveData<List<Article>> = _bookmarkedArticles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var currentJob: Job? = null
    private var page: Int = 1

    init {
        viewModelScope.launch {
            loadCachedArticles()
        }
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery.asFlow()
                .debounce(500L)
                .distinctUntilChanged()
                .collect {
                    if (!_searchQuery.value.isNullOrEmpty()) {
                        fetchNews()
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun fetchNews() {
        page = 1
        cleanDatabase()
        fetchData(_searchQuery.value)
    }

    fun fetchNextPage(
    ) {
        page += 1
        fetchData(_searchQuery.value)
    }

    fun cleanDatabase() {
        viewModelScope.launch {
            repository.deleteAllArticles()
            _articles.value = emptyList()
        }
    }
    private fun fetchData(query: String?) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch articles from the database
                loadCachedArticles()
                // Fetch new articles

                repository.getTopHeadlines(query, page).onSuccess { response ->
                    val filteredArticles = response.articles.filter {
                        !it.articleSource.id.isNullOrEmpty() || (it.articleSource.name != "[Removed]" && it.title != "[Removed]")
                    }
                    val currentArticles = _articles.value?.toMutableList() ?: mutableListOf()
                    currentArticles.addAll(filteredArticles)
                    repository.insertArticles(currentArticles)
                    _articles.value = currentArticles
                }.onFailure { error ->
                    Log.e("errorOnFailure", error.toString())
                    if (error !is UnknownHostException) {
                        _error.value = error.message ?: "Unknown error"
                    }

                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _error.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadCachedArticles() {
        viewModelScope.launch {
            repository.getAllArticles().let { cached ->
                _articles.value = cached
            }
        }
    }

    //bookmarks
    fun loadBookmarkedArticles() {
        viewModelScope.launch {
            _bookmarkedArticles.value = repository.getBookmarkedArticles()
        }
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
            loadBookmarkedArticles() // Refresh bookmarked articles
        }
    }

    fun isArticleInBookmark(article: Article): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val isBookmarked = repository.isArticleInBookmark(article)
            result.postValue(isBookmarked)
        }

        return result
    }

    fun reset() {
        page = 1
        _articles.value = emptyList()
        _error.value = null
        currentJob?.cancel()
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}