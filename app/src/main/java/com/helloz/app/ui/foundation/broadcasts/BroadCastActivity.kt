package com.helloz.app.ui.foundation.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityBroadCastBinding
import com.helloz.app.utils.CommonUtils
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 1. 创建一个BroadcastReceiver
 * 2. 注册BroadcastReceiver
 * 3. 销毁BroadcastReceiver
 */

class BroadCastActivity : BaseActivity() {

    private val TIME_CHANGED_ACTION: String = "android.intent.action.TIME_TICK"
    private val MY_DEFINED_ACTION: String = "com.helloz.app.ui.foundation.broadcasts.MY_DEFINED_ACTION"

    private lateinit var mBroadCastBinding: ActivityBroadCastBinding
    private lateinit var mSendBroadcastBtn: MaterialButton
    private lateinit var mTimeTv: TextView
    private lateinit var mTipsTv: TextView
    private lateinit var mMyBroadcastReceiver: MyBroadcastReceiver
    private lateinit var mBroadcastContentEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        registerReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mMyBroadcastReceiver)
    }

    private fun initView() {
        mBroadCastBinding = DataBindingUtil.setContentView(this, R.layout.activity_broad_cast)

        mSendBroadcastBtn = mBroadCastBinding.sendBroadcastBtn
        mSendBroadcastBtn.setOnClickListener {
            var content: String = mBroadcastContentEt.text.toString()
            when {
                TextUtils.isEmpty(content) -> {
                    content = CommonUtils.DEFAULT_MESSAGE
                }
            }
            val intent: Intent = Intent(MY_DEFINED_ACTION)
            // 因为默认发出的是隐式广播,所以需要设置包名
            intent.setPackage(packageName)
            intent.putExtra(CommonUtils.TOAST, content)
            sendBroadcast(intent)
        }

        mBroadcastContentEt = mBroadCastBinding.broadcastContentEt

        mTimeTv = mBroadCastBinding.timeTv
        mTipsTv = mBroadCastBinding.tipsTv

        mTimeTv.text = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        mTipsTv.text = getString(R.string.broadcast_tips)

        mMyBroadcastReceiver = MyBroadcastReceiver()
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(TIME_CHANGED_ACTION)
        intentFilter.addAction(MY_DEFINED_ACTION)
        registerReceiver(mMyBroadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == TIME_CHANGED_ACTION) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentTime = simpleDateFormat.format(System.currentTimeMillis())
                mTimeTv.text = currentTime
            }
            if (intent?.action == MY_DEFINED_ACTION) {
                Toast.makeText(context, intent.getStringExtra(CommonUtils.TOAST), Toast.LENGTH_SHORT).show()
            }
        }
    }
}