package com.basemibrahim.movieslist.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.basemibrahim.movieslist.R
import com.basemibrahim.movieslist.utils.Constants.Companion.Poster_BASE_URL

@BindingAdapter("title")
fun bindTitle(textView: TextView, title: String?) {
    title?.let {
        textView.text = title + "                                                                  "
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        imgView.load(Poster_BASE_URL + it)
        {
            placeholder(R.drawable.ic_outline_360_24)
            error(R.drawable.ic_outline_broken_image_24)
        }
    }
}

