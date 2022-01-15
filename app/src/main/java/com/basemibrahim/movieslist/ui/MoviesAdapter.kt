package com.basemibrahim.movieslist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.basemibrahim.movieslist.data.model.api.movie.Result
import com.basemibrahim.movieslist.databinding.GridViewItemBinding


class MoviesAdapter(data: List<Result>) : RecyclerView.Adapter<
        MoviesAdapter.MoviesViewHolder>() {
    var list: List<Result> = data

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        return MoviesViewHolder(
            GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MoviesViewHolder(
        private var binding:
        GridViewItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.item = movie
            binding.root.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToDetailsFragment(
                    title = movie.title,
                    img = movie.poster_path,
                    description = movie.overview,
                    releaseDate = movie.release_date,
                    votesAverage = movie.vote_average.toString(),
                    popularity = movie.popularity.toString(),
                    movieId = movie.id
                )
                binding.root.findNavController().navigate(action)

            }
            binding.executePendingBindings()
        }
    }

}