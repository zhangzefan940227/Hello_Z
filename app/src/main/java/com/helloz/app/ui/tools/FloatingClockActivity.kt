package com.helloz.app.ui.tools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityFloatingClockBinding
import com.helloz.app.service.FloatingClockService

/**
 * 浮动时钟活动类，用于管理浮动时钟服务的启动和停止。
 */
class FloatingClockActivity : BaseActivity() {
    private val tag: String = this::class.simpleName ?: "FloatingClockActivity"
    private lateinit var mFloatingClockBinding: ActivityFloatingClockBinding
    private lateinit var mResultLauncher: ActivityResultLauncher<Intent>

    /**
     * 活动创建时调用的方法，用于初始化界面和功能。
     * @param savedInstanceState 保存的实例状态
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FloatingClockService.isStarted) {
            stopService(Intent(this, FloatingClockService::class.java))
        }
        init()
        checkPermission()
    }

    /**
     * 初始化界面和功能的方法。
     */
    private fun init() {
        mFloatingClockBinding = DataBindingUtil.setContentView(this, R.layout.activity_floating_clock)
        mFloatingClockBinding.clockSwitchSc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startService(Intent(this, FloatingClockService::class.java))
            } else {
                stopService(Intent(this, FloatingClockService::class.java))
            }
        }

        // 注册用于处理权限请求结果的 ActivityResultLauncher
        mResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                    startService(Intent(this, FloatingClockService::class.java))
                }
            }
        }
    }

    /**
     * 检查并请求悬浮窗权限的方法。
     */
    private fun checkPermission() {
        if (FloatingClockService.isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            mResultLauncher.launch(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
        }
    }
}