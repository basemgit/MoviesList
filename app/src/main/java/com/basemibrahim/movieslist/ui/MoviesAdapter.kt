package com.basemibrahim.movieslist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.basemibrahim.movieslist.R
import com.basemibrahim.movieslist.data.model.api.movie.Result
import com.basemibrahim.movieslist.databinding.GridViewItemBinding


class MoviesAdapter(data: List<Result>,favList:ArrayList<Result>, internal var onFavClicked:OnFavClicked) : RecyclerView.Adapter<
        MoviesAdapter.MoviesViewHolder>() {
    var list: List<Result> = data
    var favouritesList = favList

    interface OnFavClicked {
        fun saveFav()
    }

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

   inner class MoviesViewHolder(
        private var binding:
        GridViewItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.item = movie
            if(movie.isFavourite)
            {
                binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
            else
            {
                binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)

            }
            binding.image.setOnClickListener {
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
            binding.favBtn.setOnClickListener {
                if(movie.isFavourite)
                {
                    it.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    movie.isFavourite = false
                    if(favouritesList.contains(movie))
                    {
                        favouritesList.remove(movie)

                    }
                }
                else
                {
                    it.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    movie.isFavourite = true
                    if(!favouritesList.contains(movie))
                    {
                        favouritesList.add(movie)

                    }
                }
                  onFavClicked.saveFav()
            }
            binding.executePendingBindings()
        }
    }

}