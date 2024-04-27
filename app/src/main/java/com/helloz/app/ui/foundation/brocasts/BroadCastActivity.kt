package com.helloz.app.ui.foundation.brocasts

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.helloz.app.R

/**
 * 1. 创建一个BroadcastReceiver
 * 2. 注册BroadcastReceiver
 * 3. 销毁BroadcastReceiver
 */

class BroadCastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broad_cast)
    }
}