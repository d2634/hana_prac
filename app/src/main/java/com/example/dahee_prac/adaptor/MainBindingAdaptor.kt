package com.example.dahee_prac.adaptor

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.dahee_prac.R

object MainBindingAdaptor {
    @BindingAdapter("imgurl")
    @JvmStatic
    fun setimgData(imageView : ImageView, img : Int ){
        imageView.setImageResource(img)
    }

    @BindingAdapter("title")
    @JvmStatic
    fun settitleData(textView: TextView, text: String){
        textView.text = text
    }

}