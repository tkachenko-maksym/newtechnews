package com.example.newtechnews.ui.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.repository.NewsRepository
import com.example.newtechnews.utils.NetworkUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NewsRepository(application)

    private val _newsState = MutableLiveData<NewsState>()
    val newsState: LiveData<NewsState> = _newsState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentJob: Job? = null
    private var page: Int = 1
//    private var isInitialFetch = true

    fun fetchNews(query: String? = null) {
//        if (!isInitialFetch) return
//        isInitialFetch = false
        fetchData(query)
    }

    fun fetchNextPage(
        query: String? = null,
    ) {
        page += 1
        fetchData(query)
    }

    fun cleanDatabase() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            repository.deleteAllArticles()
        }
    }

    private fun fetchData(query: String?) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch articles from the database
                repository.getAllArticles().let { articles ->
                    _newsState.value = NewsState.Success(articles)
                }
                repository.getTopHeadlines(query, page).onSuccess { response ->
                    val filteredArticles = response.articles.filter {
                        !it.articleSource.id.isNullOrEmpty() || (it.articleSource.name != "[Removed]" && it.title != "[Removed]")
                    }
                    filteredArticles.forEach { Log.d("images", it.urlToImage.toString()) }
                    val oldArticles =
                        (_newsState.value as? NewsState.Success?)?.articles?.toMutableList()
                            ?: mutableListOf()
                    oldArticles.addAll(filteredArticles)
                    repository.insertArticles(oldArticles)
                    _newsState.value = NewsState.Success(oldArticles)
                }.onFailure { error ->
                    Log.e("errorOnFailure", error.toString())
                    _newsState.value = NewsState.Error(error.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
                _newsState.value = NewsState.Error(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadFromDatabase() {
        repository.getAllArticles().let { articles ->
            _newsState.value = if (articles.isEmpty()) {
                NewsState.Error("No cached articles available")
            } else {
                NewsState.Success(articles)
            }
        }
    }

    suspend fun deleteAllArticles() {
        repository.deleteAllArticles()
    }

    fun reset() {
        page = 1
//        isInitialFetch = true
        _newsState.value = NewsState.Success(emptyList())
        currentJob?.cancel()
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}

sealed class NewsState {
    data class Success(val articles: List<Article>) : NewsState()
    data class Error(val message: String) : NewsState()
}