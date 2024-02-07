package com.example.dahee_prac.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.dahee_prac.databinding.SecondActivityBinding
import kotlinx.android.synthetic.main.second_activity.numbtn

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: SecondActivityBinding
    private var liveText: MutableLiveData<String> = MutableLiveData()
    private var count = 0 // button을 누르면 증가 될 숫자

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SecondActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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