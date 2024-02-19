package com.example.dahee_prac.adaptor

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.dahee_prac.R

object MainBindingAdaptor {
    @BindingAdapter("imageurl")
    @JvmStatic
    fun setMainData(imageView : ImageView, img : Int ){
        imageView.setImageResource(img)
    }
}