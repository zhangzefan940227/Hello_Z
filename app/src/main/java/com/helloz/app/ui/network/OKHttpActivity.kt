package com.helloz.app.ui.network

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityOkhttpBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject

class OKHttpActivity : BaseActivity() {
    private val TAG = "OKHttpActivity"
    private lateinit var mOkHttpBinding: ActivityOkhttpBinding
    private var mOkHttpClient: OkHttpClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        mOkHttpBinding = DataBindingUtil.setContentView(this, R.layout.activity_okhttp)
        mOkHttpClient = OkHttpClient.Builder().build()
        mOkHttpBinding.okhttpBtn.setOnClickListener {
            Log.d(TAG, "init: build request")
            requestBaidu()
        }
    }

    private fun requestBaidu() {
        // 构建请求
        val request = Request.Builder()
            .url("https://www.baidu.com")
            .build()

        Thread {
            try {
                // 发送请求，并拿到响应
                val response = mOkHttpClient!!.newCall(request).execute()

                // 打印响应体数据
                val responseBody = response.body?.string()
                runOnUiThread {
                    mOkHttpBinding.responseBodyTv.text = responseBody
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}