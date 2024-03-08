package com.example.dahee_prac.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.dahee_prac.R
import com.example.dahee_prac.databinding.MainActivityBinding
import com.example.dahee_prac.model.MainData
import com.example.dahee_prac.model.item


class MainActivity : Activity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)*/

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.main_activity
        )

        var item1 = item("제목1", R.drawable.ic_launcher_background)
        var item2 = item("제목2", R.mipmap.ic_launcher)
        val setdata = MainData(item1, item2)
        binding.maindata=setdata

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("data1","텍스트를 넘겨요~")
        binding.mainbutton.setOnClickListener(){
            //클릭시 동작할 코드 작성 필요
            startActivity(intent)
        }
        //내용 작성하
    }

}