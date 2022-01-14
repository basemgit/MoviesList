package com.basemibrahim.movieslist.ui

import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basemibrahim.movieslist.R
import com.basemibrahim.movieslist.data.model.api.MoviesResponse
import com.basemibrahim.movieslist.data.model.api.Result
import com.basemibrahim.movieslist.databinding.ListFragmentBinding
import com.basemibrahim.movieslist.utils.Constants.Companion.NETWORK_TAG
import com.basemibrahim.movieslist.utils.NetworkResult
import com.basemibrahim.movieslist.utils.Utils
import com.basemibrahim.movieslist.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var _binding: ListFragmentBinding
    private var loading = true
    private var totalPages = 0
    var page = 1
    private lateinit var adapter: MoviesAdapter
    var list = ArrayList<Result>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentBinding.inflate(inflater)
        _binding.lifecycleOwner = this
        _binding.viewModel = mainViewModel
        adapter = MoviesAdapter(list)
        _binding.list.adapter = adapter
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchResponse(page)
        loadMore()
    }

    private fun fetchResponse(page: Int) {
        if (Utils.isNetworkAvailable(requireContext())) {
            mainViewModel.getNowPlayingMovies(page)
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
        mainViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        totalPages = it.total_pages
                        loading = false;
                        //  mainViewModel.saveResponseToDb(response.data)
                        showMovies(it)
                    }
                    _binding.pbDog.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    _binding.pbDog.visibility = View.GONE
                    Log.d(NETWORK_TAG, response.message.toString())
                }

                is NetworkResult.Loading -> {
                    _binding.pbDog.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun showMovies(data: MoviesResponse) {

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
                    fetchResponse(page)
                }
            }
        })

    }
}