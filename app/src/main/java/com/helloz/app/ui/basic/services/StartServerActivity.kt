package com.helloz.app.ui.basic.services

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityStartServerBinding
import com.helloz.app.utils.CommonUtils
import com.helloz.app.utils.LogUtils

class StartServerActivity : BaseActivity(), View.OnClickListener {
    private val TAG = "StartServerActivity"
    private lateinit var mStartServerBinding: ActivityStartServerBinding
    private lateinit var mStartServerBtn: Button
    private lateinit var mStopServerBtn: Button
    private lateinit var mBindServerBtn: Button
    private lateinit var mUnBindServerBtn: Button
    private lateinit var mClearBtn: Button
    private lateinit var mContentTv: TextView
    private lateinit var mConnection: ServiceConnection

    private lateinit var mContent: String

    private lateinit var localBroadcastManager: LocalBroadcastManager
    private val updateUiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == CommonUtils.ACTION_UPDATE_UI) {
                val data = intent.getStringExtra(CommonUtils.EXTRA_DATA)
                updateContent(data + "\n")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        mStartServerBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_server)

        mContent = ""
        mContentTv = mStartServerBinding.contentTv
        mStartServerBtn = mStartServerBinding.startServer
        mStopServerBtn = mStartServerBinding.stopServer
        mBindServerBtn = mStartServerBinding.bindServer
        mUnBindServerBtn = mStartServerBinding.unBindServer
        mClearBtn = mStartServerBinding.clearBtn

        mStartServerBtn.setOnClickListener(this)
        mStopServerBtn.setOnClickListener(this)
        mBindServerBtn.setOnClickListener(this)
        mUnBindServerBtn.setOnClickListener(this)
        mClearBtn.setOnClickListener(this)

        updateContent("Hello World! \n")

        mConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mContent += "Service connected! \n"
                mContentTv.text = mContent
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }
        }

        // 注册本地广播
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(updateUiReceiver, IntentFilter(CommonUtils.ACTION_UPDATE_UI))
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        val intent = Intent(this, MyService::class.java)
        when (v?.id) {
            R.id.start_server -> {
                startService(intent)
            }

            R.id.stop_server -> {
                stopService(intent)
            }

            R.id.bind_server -> {
                bindService(intent, mConnection, BIND_AUTO_CREATE)
            }

            R.id.unBind_server -> {
                try {
                    unbindService(mConnection)
                } catch (e: Exception) {
                    updateContent("Unbind error! \n")
                }
            }
            R.id.clear_btn -> {
                mContent = ""
                mContentTv.text = mContent
            }

            else -> {
                LogUtils.w(TAG, "Onclick error!")
            }
        }
    }

    /**
     * 通过TextView实时打印Service绑定的状态
     */
    fun updateContent(content: String) {
        mContent += content
        mContentTv.text = mContent
    }
}