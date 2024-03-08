package com.example.dahee_prac.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.dahee_prac.R
import com.example.dahee_prac.pojo.ResultHanaAPI
import com.example.dahee_prac.viewModel.RecyclerViewModel
import com.example.dahee_prac.databinding.SecondActivityBinding
import com.example.dahee_prac.retrofit.RetrofitInstance
import com.example.dahee_prac.viewModel.RetrofitViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: SecondActivityBinding
    private val viewModel : RecyclerViewModel by viewModels()
    private lateinit var retrofitVM: RetrofitViewModel
    private var liveText: MutableLiveData<String> = MutableLiveData()
    private var count = 0 // button을 누르면 증가 될 숫자
    val Accept = "application/json"
    val UserAgent = "{\"platform\":\"Android\",\"brand\":\"samsung\",\"model\":\"SM-F721N\",\"version\":\"13\",\"deviceId\":\"ffffffff-a227-fda6-0000-00000001295b\",\"phoneNumber\":\"\",\"countryIso\":\"\",\"telecom\":\"KT\",\"simSerialNumber\":\"\",\"subscriberId\":\"\",\"appVersion\":\"2.39\",\"phoneName\":\"\",\"appName\":\"OneQPlus\",\"deviceWidth\":1080,\"deviceHeight\":2402,\"uid\":\"ffffffff-a227-fda6-0000-00000001295b\",\"hUid\":\"ffffffff-a227-fda6-0000-00000001295b\",\"terminalInfoId\":\"56eca2df-92ea-47ad-9a92-2159aa81f790\",\"etcStr\":\"\",\"userAgent\":\"Mozilla/5.0 (Linux; Android 13; SM-F721N Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/108.0.5359.128 Mobile Safari/537.36\"}"
    var responsetext : String = "?????"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        retrofitVM = ViewModelProvider(this).get(RetrofitViewModel::class.java)

        //그냥 레트로핏 호출
        //retrofitVM.callRetrofit()
        //observerRetrofitLivedata()

        //******코루틴 레트로핏 호출
        retrofitVM.refresh()
        observeViewModel()

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

        binding.numBtn.setOnClickListener{
            //liveText.value="내 숫자는?: ${count++}"
            liveText.value=responsetext
        }
    }

    private fun observerRetrofitLivedata() {
        retrofitVM.observeRetrofitLivedata().observe(this, object : Observer<ResultHanaAPI>{
            override fun onChanged(value: ResultHanaAPI) {
                val responseBodyList = value.data?.cmsWidget?.data
                Log.d("YMC", "onResponse 성공: $responseBodyList");

                if (responseBodyList != null) { //받아온 content 값을 recyclerview에 띄운다
                    for (i in responseBodyList)
                        viewModel.addTodo(i.content)
                }
            }

        })
    }

    //코루틴 사용 레트로핏 옵저버
    fun observeViewModel() {
        retrofitVM.hanadatas.observe(this, Observer {hanadatas ->
            hanadatas?.let {
                val responseBodyList = hanadatas.data?.cmsWidget?.data
                if (responseBodyList != null) { //받아온 content 값을 recyclerview에 띄운다
                    for (i in responseBodyList)
                        viewModel.addTodo(i.content)
                 }
            }
        })

        retrofitVM.dataLoadError.observe(this, Observer { isError ->
            Log.d("YMC", "onResponse 실패");
        })

        retrofitVM.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                Log.d("YMC", "onResponse 로딩");
            }
        })
    }
}
