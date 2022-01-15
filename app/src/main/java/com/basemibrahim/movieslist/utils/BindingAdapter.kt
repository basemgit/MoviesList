package com.basemibrahim.movieslist.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.basemibrahim.movieslist.utils.Constants.Companion.Poster_BASE_URL

@BindingAdapter("title")
fun bindTitle(textView: TextView, title: String?) {
    title?.let {
        textView.text = title + "                                                                  "
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (!imgUrl.isNullOrEmpty()) {
        imgView.load(Poster_BASE_URL+imgUrl)
        imgView.visibility = View.VISIBLE
    } else {
        imgView.visibility = View.GONE
    }
}

