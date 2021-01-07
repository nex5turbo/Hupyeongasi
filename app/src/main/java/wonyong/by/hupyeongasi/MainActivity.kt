package wonyong.by.hupyeongasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.*
import wonyong.by.hupyeongasi.databinding.ActivityMainBinding
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    var api_key = ""
    lateinit var binding:ActivityMainBinding
    lateinit var retrofit:Retrofit
    lateinit var service:API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        retrofit = RetrofitClient.getInstnace()
        service = retrofit.create(API::class.java)
        init()
    }

    private fun init() {
        binding.button.setOnClickListener {
            var name = binding.editTextTextPersonName.text
            if(name.equals("")){
                binding.textView.text = "소환사명을 입력하시구려"
            }else{
                api_key = binding.KeyText.text.toString()
                if(api_key.equals("")){
                    binding.textView.text = "열쇠없으면 못봐용~"
                }else {
                    binding.button.isEnabled = false
                    requestSummoner(name.toString())
                }
            }
        }
    }

    fun requestSummoner(name:String){
        service.getsummoner(name,api_key).enqueue(object : Callback<Summoner>{
            override fun onFailure(call: Call<Summoner>, t: Throwable) {
                Log.d("###", "Failed")
            }

            override fun onResponse(call: Call<Summoner>, response: Response<Summoner>) {
                Log.d("###", "success")
                Log.d("###response", response.body().toString())
                if(response.body() == null){
                    binding.textView.text = name+"이(가) 사람이름임? ㄹㅇㅋㅋ"
                    binding.button.isEnabled = true
                }else {
                    requestSpectator(response.body()!!.encryptedSummonerId)
                }
            }
        })
    }
    fun requestSpectator(id:String){
        service.getspectator(id, api_key).enqueue(object : Callback<Spectator>{
            override fun onFailure(call: Call<Spectator>, t: Throwable) {
                Log.d("###", "Failed")
            }

            override fun onResponse(call: Call<Spectator>, response: Response<Spectator>) {
                Log.d("###", "success")
                Log.d("###response", response.body().toString())
                if(response.body() == null){
                    binding.textView.text = "게임안하는중"
                }else {
                    var gameTime = response.body()!!.gameStartTime
                    if(gameTime <= 0){
                        binding.textView.text = "로딩중"
                    }else {
                        var nowTime = System.currentTimeMillis()
                        var timeDifference = (nowTime - gameTime) / 1000
                        var showTime = timeCalc(timeDifference.toInt())
                        binding.textView.text = showTime
                    }
                }
                binding.button.isEnabled = true
            }
        })
    }
    fun timeCalc(time:Int):String{
        var min = (time/60).toString()
        var sec = (time%60).toString()
        var rtString = min+"분"+sec+"초 지났습니당"
        return rtString
    }
}