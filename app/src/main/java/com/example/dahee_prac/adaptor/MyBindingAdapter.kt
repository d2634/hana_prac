package com.example.dahee_prac.adaptor

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dahee_prac.model.Todo

object MyBindingAdapter {
    @BindingAdapter("items")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView, todoList: List<Todo>?){
        if(recyclerView.adapter == null){
            val adapter = RecyclerAdapter()
            recyclerView.adapter = adapter
        }

        todoList?.let{
            val myAdapter = recyclerView.adapter as RecyclerAdapter
            myAdapter.submitList(it)
            myAdapter.notifyDataSetChanged()
        }
    }
}