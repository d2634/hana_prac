package com.example.dahee_prac.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.dahee_prac.HanaAPI
import com.example.dahee_prac.R
import com.example.dahee_prac.ResultHanaAPI
import com.example.dahee_prac.User
import com.example.dahee_prac.viewModel.MainViewModel
import com.example.dahee_prac.databinding.SecondActivityBinding
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: SecondActivityBinding
    private val viewModel : MainViewModel by viewModels()
    private var liveText: MutableLiveData<String> = MutableLiveData()
    private var count = 0 // button을 누르면 증가 될 숫자
    val Accept = "application/json"
    val UserAgent = "{\"platform\":\"Android\",\"brand\":\"samsung\",\"model\":\"SM-F721N\",\"version\":\"13\",\"deviceId\":\"ffffffff-a227-fda6-0000-00000001295b\",\"phoneNumber\":\"\",\"countryIso\":\"\",\"telecom\":\"KT\",\"simSerialNumber\":\"\",\"subscriberId\":\"\",\"appVersion\":\"2.39\",\"phoneName\":\"\",\"appName\":\"OneQPlus\",\"deviceWidth\":1080,\"deviceHeight\":2402,\"uid\":\"ffffffff-a227-fda6-0000-00000001295b\",\"hUid\":\"ffffffff-a227-fda6-0000-00000001295b\",\"terminalInfoId\":\"56eca2df-92ea-47ad-9a92-2159aa81f790\",\"etcStr\":\"\",\"userAgent\":\"Mozilla/5.0 (Linux; Android 13; SM-F721N Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/108.0.5359.128 Mobile Safari/537.36\"}"
    var responsetext : String = "?????"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder().baseUrl("https://dev12-mbp.hanabank.com:18080/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(HanaAPI::class.java);

        service.getHanaData(Accept,UserAgent).enqueue(object : Callback<ResultHanaAPI>{
            override fun onResponse(call: Call<ResultHanaAPI>, response: Response<ResultHanaAPI>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성고된 경우
                    val responseBodyList = response.body()?.data?.cmsWidget?.data
                    Log.d("YMC", "onResponse 성공: $responseBodyList");

                    if (responseBodyList != null) { //받아온 content 값을 recyclerview에 띄운다
                        for (i in responseBodyList)
                            viewModel.addTodo(i.content)
                    }
                    Log.d("responsetext", "onResponse 성공: $responsetext");

                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<ResultHanaAPI>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })

        //단순한 okhttp3로 호출하는 코드
        /* val url = "https://dev12-mbp.hanabank.com:18080/common/appInfo.do"
        val okHttpClient = OkHttpClient();
        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("실패", "실패ㅠㅠ")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBodyString = response.body!!.string() // response.body는 한번만 호출해야 함!!!!
                Log.d("응답!", responseBodyString)
                responsetext = responseBodyString
            }
        }) */

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
            //liveText.value="내 숫자는?: ${count++}"
            liveText.value=responsetext
        }


    }
}