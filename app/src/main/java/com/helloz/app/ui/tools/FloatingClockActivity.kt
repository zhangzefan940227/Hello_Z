package com.helloz.app.ui.tools

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityFloatingClockBinding
import com.helloz.app.service.FloatingClockService
import com.helloz.app.viewmodel.FloatingClockViewModel

/**
 * 浮动时钟活动类，用于管理浮动时钟服务的启动和停止。
 */
class FloatingClockActivity : BaseActivity() {
    private val tag: String = this::class.simpleName ?: "FloatingClockActivity"
    private lateinit var mClockBinding: ActivityFloatingClockBinding
    private lateinit var mResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mViewModel: FloatingClockViewModel

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
        mClockBinding = DataBindingUtil.setContentView(this, R.layout.activity_floating_clock)
        mClockBinding.clockSwitchSc.setOnCheckedChangeListener { _, isChecked ->
            switchServiceState(isChecked)
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
        mViewModel = ViewModelProvider(this)[FloatingClockViewModel::class.java]
        mViewModel.clockLiveData.observe(this) { model ->
            if (FloatingClockService.isStarted) {
                val clockTv = FloatingClockService.weakRefView.get()?.findViewById<TextView>(R.id.clock_widget_tv)
                val windowManager = FloatingClockService.weakRefWindowManager
                clockTv?.textSize = model.textSize!!
                clockTv?.background = model.bgColor
                clockTv?.setTextColor(ColorStateList.valueOf(Color.parseColor(model.textColor)))
                val imageDisplay =
                    FloatingClockService.weakRefView.get()?.findViewById<ConstraintLayout>(R.id.image_display)
                val layoutParams = imageDisplay?.layoutParams
                clockTv?.post {
                    layoutParams?.width = clockTv.measuredWidth + clockTv.paddingStart + clockTv.paddingEnd
                    layoutParams?.height = clockTv.measuredHeight + clockTv.paddingTop + clockTv.paddingBottom
                    windowManager.get()?.updateViewLayout(imageDisplay, layoutParams)
                }
            }
        }
        mClockBinding.modifyBt.setOnClickListener {
            updateFloatingClockData()
        }
        mClockBinding.resetBt.setOnClickListener {
            mViewModel.resetClockData()
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

    private fun switchServiceState(isChecked: Boolean) {
        if (isChecked) {
            startService(Intent(this, FloatingClockService::class.java))
        } else {
            stopService(Intent(this, FloatingClockService::class.java))
        }
    }

    private fun updateFloatingClockData() {
        val textSize = mClockBinding.clockTextSizeEt.text?.toString()?.toFloatOrNull() ?: 30f
        val textColorInput = mClockBinding.clockTextColorEt.text?.toString().orEmpty()
        val textColor = if (textColorInput.isNotEmpty()) {
            // 确保颜色值有#前缀（兼容用户是否手动输入#的情况）
            if (!textColorInput.startsWith("#")) "#$textColorInput" else textColorInput
        } else {
            "#FFFFFF"
        }

        val bgColorInput = mClockBinding.clockBgColorEt.text?.toString().orEmpty()
        val bgColor = if (bgColorInput.isNotEmpty()) {
            // 确保颜色值有#前缀（兼容用户是否手动输入#的情况）
            if (!bgColorInput.startsWith("#")) "#$bgColorInput" else bgColorInput
        } else {
            "#40000000" // 默认颜色
        }
        val bgDrawable = ColorDrawable(Color.parseColor(bgColor))
        mViewModel.updateClockData(textSize, textColor, bgDrawable)
    }
}