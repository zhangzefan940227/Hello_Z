package com.helloz.app.ui.basic.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.helloz.app.utils.CommonUtils
import java.lang.ref.WeakReference

class MyService : Service() {
    private val TAG = "MyService"
    private lateinit var mWeakReferenceHandler: WeakRefHandler

    override fun onCreate() {
        super.onCreate()
        mWeakReferenceHandler = WeakRefHandler(this)
        sendUpdateUiMessage(CommonUtils.MSG_START_SERVICE)
    }

    override fun onBind(intent: Intent): IBinder? {
        sendUpdateUiMessage(CommonUtils.MSG_BIND_SERVICE)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        sendUpdateUiMessage(CommonUtils.MSG_UNBIND_SERVICE)
        return super.onUnbind(intent)
    }

    override fun stopService(name: Intent?): Boolean {
        sendUpdateUiMessage(CommonUtils.MSG_STOP_SERVICE)
        return super.stopService(name)
    }

    override fun onDestroy() {
        sendUpdateUiMessage(CommonUtils.MSG_STOP_SERVICE)
        super.onDestroy()
    }

    fun sendUpdateUiMessage(messageId: String) {
        val intent: Intent = Intent(CommonUtils.ACTION_UPDATE_UI)
        intent.putExtra(CommonUtils.EXTRA_DATA, messageId)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private class WeakRefHandler(target: MyService) : Handler(Looper.getMainLooper()) {

        private val weakTarget = WeakReference(target)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            weakTarget.get() ?: return // 如果目标对象已被回收，直接返回
        }
    }
}