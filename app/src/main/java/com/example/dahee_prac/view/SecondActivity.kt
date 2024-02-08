package com.example.dahee_prac.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.dahee_prac.R
import com.example.dahee_prac.viewModel.MainViewModel
import com.example.dahee_prac.databinding.SecondActivityBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: SecondActivityBinding
    private val viewModel : MainViewModel by viewModels()
    private var liveText: MutableLiveData<String> = MutableLiveData()
    private var count = 0 // button을 누르면 증가 될 숫자

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.second_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.addButton.setOnClickListener {
            var mytext = binding.editText.text.toString()
            viewModel.addTodo(mytext)
        }

        binding.subtext.text = intent.getStringExtra("data1")
        //내용 작성하기

        liveText.observe(this, Observer {
            // it로 넘어오는 param은 LiveData의 value
            binding.subtext.text = it
        })

        binding.numbtn.setOnClickListener{
            liveText.value="내 숫자는?: ${count++}"
        }


    }
}