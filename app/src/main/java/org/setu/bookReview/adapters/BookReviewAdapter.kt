package org.setu.bookReview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.setu.bookReview.databinding.CardBookReviewBinding
import org.setu.bookReview.models.BookReviewModel


interface BookReviewListener {
    fun onBookReviewClick(bookReview: BookReviewModel)
}

class BookReviewAdapter constructor(private var bookReviews: List<BookReviewModel>,
                                   private val listener: BookReviewListener) :
    RecyclerView.Adapter<BookReviewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookReviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val bookReview = bookReviews[holder.adapterPosition]
        holder.bind(bookReview, listener)
    }

    override fun getItemCount(): Int = bookReviews.size

    class MainHolder(private val binding : CardBookReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookReview: BookReviewModel, listener: BookReviewListener) {
            binding.bookTitle.text = bookReview.bookTitle
            binding.review.text = bookReview.review
            binding.ratingBar.rating=bookReview.rating
//            binding.genre.text = bookReview.genre
//            binding.stageOfReading.text = bookReview.stageOfReading
            Picasso.get().load(bookReview.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onBookReviewClick(bookReview) }
        }
    }
}