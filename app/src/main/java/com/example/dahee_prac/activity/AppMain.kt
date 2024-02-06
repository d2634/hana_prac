package com.example.dahee_prac.activity

import android.app.Activity
import android.os.Bundle
import com.example.dahee_prac.R
import kotlinx.android.synthetic.main.app_main.*

class AppMain : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main)

        mainbutton.setOnClickListener(){
            //클릭시 동작할 코드 작성 필요

        }
        //내용 작성하기
    }
}