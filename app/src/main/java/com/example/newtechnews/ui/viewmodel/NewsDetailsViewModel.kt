package com.example.newtechnews.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsDetailsViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: NewsRepository = NewsRepository(application)
    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> get() = _isBookmarked.asStateFlow()
    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            repository.toggleBookmark(article)
            _isBookmarked.value = repository.isArticleInBookmark(article)
        }
    }

    fun isArticleInBookmark(article: Article) {
        viewModelScope.launch {
            _isBookmarked.value = repository.isArticleInBookmark(article)
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}