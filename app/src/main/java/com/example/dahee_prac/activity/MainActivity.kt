package com.example.dahee_prac.activity

import android.app.Activity
import android.os.Bundle
import com.example.dahee_prac.R
import kotlinx.android.synthetic.main.main_activity.mainbutton

class MainActivity : Activity() {

    //private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //binding = MainActivityBinding.inflate(layoutInflater)
        //val view = binding.root
        //setContentView(view)

        mainbutton.setOnClickListener(){
            //클릭시 동작할 코드 작성 필요

        }
        //내용 작성하
    }

}