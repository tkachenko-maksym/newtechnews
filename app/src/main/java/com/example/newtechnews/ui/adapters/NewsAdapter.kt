package com.example.newtechnews.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.newtechnews.R
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.MainNewsItemBinding
import com.example.newtechnews.ui.viewmodel.NewsViewModel
import com.example.newtechnews.utils.formatToDisplayDate
import java.util.Objects

class NewsAdapter(
    private val onItemClick: (Article) -> Unit,
    private val onBookmarkClick: (Article) -> Unit,
    private val bookmarkedArticles: LiveData<List<Article>>

) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val news = mutableListOf<Article>()

    inner class NewsViewHolder(private val binding: MainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) = with(binding) {
            Log.d("bind",(bookmarkedArticles.value?.any {
                it.url == article.url
            } == true).toString())
            titleText.text = article.title
            descriptionText.text = article.description
            dateText.text = article.publishedAt.formatToDisplayDate()
            sourceNameText.text = article.articleSource.name
            newsImage?.load(article.urlToImage) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_error_image)
            }
            root.setOnClickListener { onItemClick(article) }
            bookmarkButton.setOnClickListener { onBookmarkClick(article) }
            bookmarkButton.setIconResource(if (bookmarkedArticles.value?.any {
                    it.url == article.url
                } == true) {
                R.drawable.ic_bookmark_remove
            } else {
                R.drawable.ic_bookmark_add
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            MainNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])

    }

    override fun getItemCount(): Int = news.size

    override fun getItemId(position: Int): Long {
        val item = news[position]
        return Objects.hash(item.author, item.title, item.publishedAt).toLong()
    }

    fun setData(articles: List<Article>) {
        news.clear()
        news.addAll(articles)
    }
}