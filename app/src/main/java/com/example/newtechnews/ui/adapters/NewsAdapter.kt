package com.example.newtechnews.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.newtechnews.R
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.MainNewsItemBinding
import com.example.newtechnews.utils.formatToDisplayDate
import java.util.Objects

class NewsAdapter(

    private val onItemClick: (Article) -> Unit,
    private val onBookmarkClick: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val news = mutableListOf<Article>()

    inner class NewsViewHolder(private val binding: MainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) = with(binding) {
            titleText.text = article.title
            descriptionText.text = article.description
            dateText.text = article.publishedAt.formatToDisplayDate()

            newsImage?.load(article.urlToImage) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_error_image)
            }
            root.setOnClickListener { onItemClick(article) }
            bookmarkButton.setOnClickListener { onBookmarkClick(article) }
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