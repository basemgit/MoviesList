package com.basemibrahim.movieslist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.basemibrahim.movieslist.R
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import com.basemibrahim.movieslist.data.model.api.movie.Result
import com.basemibrahim.movieslist.data.model.api.reviews.ResultX
import com.basemibrahim.movieslist.data.model.api.reviews.ReviewsResponse
import com.basemibrahim.movieslist.databinding.DetailsFragmentBinding
import com.basemibrahim.movieslist.utils.Constants
import com.basemibrahim.movieslist.utils.Constants.Companion.DESCRIPTION
import com.basemibrahim.movieslist.utils.Constants.Companion.IMG
import com.basemibrahim.movieslist.utils.Constants.Companion.MOVIE_ID
import com.basemibrahim.movieslist.utils.Constants.Companion.POPULARITY
import com.basemibrahim.movieslist.utils.Constants.Companion.RELEASE_DATE
import com.basemibrahim.movieslist.utils.Constants.Companion.TITLE
import com.basemibrahim.movieslist.utils.Constants.Companion.VOTES_AVERAGE
import com.basemibrahim.movieslist.utils.NetworkResult
import com.basemibrahim.movieslist.utils.Utils
import com.basemibrahim.movieslist.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var _binding: DetailsFragmentBinding
    private lateinit var title: String
    private lateinit var img: String
    private lateinit var description: String
    private lateinit var releaseDate: String
    private lateinit var votesAverage: String
    private lateinit var popularity: String
    private var movieId = 0

    private var loading = true
    private var totalPages = 0
    var page = 1
    private lateinit var adapter: ReviewsAdapter
    var list = ArrayList<ResultX>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString(TITLE).toString()
            img = it.getString(IMG).toString()
            description = it.getString(DESCRIPTION).toString()
            votesAverage = it.getString(VOTES_AVERAGE).toString()
            releaseDate = it.getString(RELEASE_DATE).toString()
            popularity = it.getString(POPULARITY).toString()
             if(it.getInt(MOVIE_ID) > 0) movieId = it.getInt(MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        adapter = ReviewsAdapter(list)
        _binding.list.adapter = adapter
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (img != "null") {
            _binding.image.load(Constants.Poster_BASE_URL +img)
        } else {
            _binding.image.visibility = View.GONE
        }
        _binding.name.text = resources.getString(R.string.name) + " : " +title
        _binding.description.text = resources.getString(R.string.description) + " : " +description
        _binding.releaseDate.text = resources.getString(R.string.releaseDate) + " : " +releaseDate
        _binding.popularity.text = resources.getString(R.string.popularity) + " : " +popularity
        _binding.votesAverage.text = resources.getString(R.string.votesAverage) + " : " +votesAverage
        fetchResponse(movieId,page)
        loadMore()
    }

    private fun fetchResponse(movieId: Int, page: Int) {
        if (Utils.isNetworkAvailable(requireContext())) {
            mainViewModel.getReviews(movieId, page)
            _binding.pbDog.visibility = View.VISIBLE
            processData()
        } else {
            Snackbar.make(
                _binding.root,
                resources.getString(R.string.no_internet),
                Snackbar.LENGTH_SHORT
            )
                .show()
            _binding.pbDog.visibility = View.GONE
        }

    }

    private fun processData() {
        mainViewModel.reviewsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        totalPages = it.total_pages
                        loading = false;
                        //  mainViewModel.saveResponseToDb(response.data)
                        if(it.results.isEmpty())_binding.noReviews.visibility = View.VISIBLE
                        else _binding.noReviews.visibility = View.GONE
                        list.clear()
                        showReviews(it)
                    }
                    _binding.pbDog.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    _binding.pbDog.visibility = View.GONE
                    Log.d(Constants.NETWORK_TAG, response.message.toString())
                }

                is NetworkResult.Loading -> {
                    _binding.pbDog.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun showReviews(data: ReviewsResponse) {
        for (item in data.results) {
            if (!list.contains(item)) {
                list.add(item)
            }
        }
        adapter.notifyDataSetChanged()

    }

    private fun loadMore() {
        _binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView != null) {
                    super.onScrolled(recyclerView, dx, dy)
                }


                val visibleItemCount =
                    (_binding.list.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (_binding.list.layoutManager as LinearLayoutManager).itemCount
                val firstVisible =
                    (_binding.list.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (!loading && (visibleItemCount + firstVisible) >= totalItemCount && page < totalPages && dy > 0) {
                    loading = true
                    page += 1
                    fetchResponse(page,movieId)
                }
            }
        })

    }



}