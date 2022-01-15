package com.basemibrahim.movieslist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basemibrahim.movieslist.data.model.api.movie.Result
import com.basemibrahim.movieslist.data.model.api.reviews.ResultX
import com.basemibrahim.movieslist.databinding.ReviewsViewItemBinding


class ReviewsAdapter(data: List<ResultX>) : RecyclerView.Adapter<
        ReviewsAdapter.ReviewsViewHolder>() {
    var list: List<ResultX> = data

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsViewHolder {
        return ReviewsViewHolder(
            ReviewsViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder:ReviewsViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ReviewsViewHolder(
        private var binding:
        ReviewsViewItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ResultX) {
            binding.item = review
            binding.author.text = review.author
            binding.content.text = review.content
            binding.executePendingBindings()
        }
    }

}