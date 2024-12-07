package com.example.newtechnews.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _newsState = MutableLiveData<NewsState>()
    val newsState: LiveData<NewsState> = _newsState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentJob: Job? = null
    private var page: Int = 1

    fun fetchNews(
        query: String? = null,
    ) {
        fetchData(query)
    }

    fun fetchNextPage(
        query: String? = null,
    ) {
        page+=1
        fetchData(query)
    }

    private fun fetchData(query: String?) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getTopHeadlines(query, page).onSuccess { response ->
                        val filteredArticles = response.articles.filter {
                            it.articleSource?.name != "[Removed]"
                        }
                        val oldArticles = (_newsState.value as? NewsState.Success?)?.articles?.toMutableList()?: mutableListOf()
                        oldArticles.addAll(filteredArticles)
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

    fun reset() {
        page = 1
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