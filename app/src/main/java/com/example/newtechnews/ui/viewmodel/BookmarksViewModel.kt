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
import kotlinx.coroutines.launch

class BookmarksViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository: NewsRepository = NewsRepository(application)
    private val _bookmarkedArticles = MutableLiveData<List<Article>>()
    val bookmarkedArticles: LiveData<List<Article>> = _bookmarkedArticles

    private var currentJob: Job? = null
    //bookmarks
     fun loadBookmarkedArticles() {
        viewModelScope.launch {
            _bookmarkedArticles.value = repository.getBookmarkedArticles()
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}