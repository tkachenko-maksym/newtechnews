package com.example.newtechnews.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newtechnews.R
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.databinding.MainNewsItemBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Objects

class NewsAdapter(

    private val onItemClick: (Article) -> Unit,
    private val onBookmarkClick: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val news = mutableListOf<Article>()

    inner class NewsViewHolder(private val binding: MainNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) = with(binding) {
            Log.d("adapter", article.articleSource.toString())
            titleText.text = article.title
            descriptionText.text = article.description
            sourceText.text = article.articleSource?.name ?: ""
            dateText.text = article.publishedAt.formatToDisplayDate()

            Glide.with(itemView)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.error_image)
                .into(newsImage)

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

fun String.formatToDisplayDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = parser.parse(this)
        formatter.format(date!!)
    } catch (e: Exception) {
        this
    }
}