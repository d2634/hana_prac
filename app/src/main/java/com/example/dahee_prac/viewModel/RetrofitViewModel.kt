package com.example.dahee_prac.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dahee_prac.model.Todo
import com.example.dahee_prac.pojo.ResultHanaAPI
import com.example.dahee_prac.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitViewModel(): ViewModel() {
    private var retrofitliveData = MutableLiveData<ResultHanaAPI>()
    val Accept = "application/json"
    val UserAgent = "{\"platform\":\"Android\",\"brand\":\"samsung\",\"model\":\"SM-F721N\",\"version\":\"13\",\"deviceId\":\"ffffffff-a227-fda6-0000-00000001295b\",\"phoneNumber\":\"\",\"countryIso\":\"\",\"telecom\":\"KT\",\"simSerialNumber\":\"\",\"subscriberId\":\"\",\"appVersion\":\"2.39\",\"phoneName\":\"\",\"appName\":\"OneQPlus\",\"deviceWidth\":1080,\"deviceHeight\":2402,\"uid\":\"ffffffff-a227-fda6-0000-00000001295b\",\"hUid\":\"ffffffff-a227-fda6-0000-00000001295b\",\"terminalInfoId\":\"56eca2df-92ea-47ad-9a92-2159aa81f790\",\"etcStr\":\"\",\"userAgent\":\"Mozilla/5.0 (Linux; Android 13; SM-F721N Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/108.0.5359.128 Mobile Safari/537.36\"}"
    var responsetext : String = "?????"
    fun callRetrofit(){
        RetrofitInstance.api.getHanaData(Accept,UserAgent).enqueue(object :
            Callback<ResultHanaAPI> {
            override fun onResponse(call: Call<ResultHanaAPI>, response: Response<ResultHanaAPI>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성공된 경우\

                    if (response.body() != null) { //받아온 content 값을 recyclerview에 띄운다
                        val retrofitBody: ResultHanaAPI = response.body()!!
                        Log.d("YMC", "onResponse 성공: ${retrofitBody}");
                        retrofitliveData.value=retrofitBody
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
    }
    fun observeRetrofitLivedata(): LiveData<ResultHanaAPI> {
        return retrofitliveData;
    }
//*********************************************************************
    //코루틴을 사용해 레트로핏 비동기처리하기
    val hanaData = RetrofitInstance.gethanaApi()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    val hanadatas = MutableLiveData<ResultHanaAPI>()
    val dataLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchRetrofit()
    }

    private fun fetchRetrofit() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = hanaData.getCoroutineData(Accept,UserAgent)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    hanadatas.value = response.body()
                    dataLoadError.value = ""
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }
    private fun onError(message: String) {
        dataLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        job?.cancel()
    }

}