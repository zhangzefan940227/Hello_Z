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
 * 浮动时钟活动类，负责管理悬浮窗时钟的权限申请、服务控制及样式配置。
 * 主要功能：
 * - 检查并申请 SYSTEM_ALERT_WINDOW 权限
 * - 通过 Switch 开关控制悬浮窗服务的启停
 * - 提供字体大小、颜色、背景色的实时配置
 * - 使用 ViewModel 持久化配置数据
 */
class FloatingClockActivity : BaseActivity() {
    private val tag: String = this::class.simpleName ?: "FloatingClockActivity"
    private lateinit var mClockBinding: ActivityFloatingClockBinding

    // 权限请求结果回调处理器
    private lateinit var mResultLauncher: ActivityResultLauncher<Intent>

    // 管理时钟配置数据的 ViewModel
    private lateinit var mViewModel: FloatingClockViewModel

    /**
     * 生命周期方法：创建活动时执行
     * 主要逻辑：
     * 1. 若服务已运行则立即停止（防止重复实例）
     * 2. 初始化视图绑定和事件监听
     * 3. 检查悬浮窗权限
     * @param savedInstanceState 状态恢复数据包
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 关闭悬浮窗服务
        stopFloatingService()

        // 初始化
        init()

        // 检查权限
        checkPermission()
    }

    /**
     * 初始化核心组件
     */
    private fun init() {
        mClockBinding = DataBindingUtil.setContentView(this, R.layout.activity_floating_clock)
        mClockBinding.clockSwitchSc.setOnCheckedChangeListener { _, isChecked ->
            switchServiceState(isChecked)
        }
        // 注册用于处理权限请求结果的 ActivityResultLauncher
        mResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handlePermissionResult(result.resultCode)
        }
        setOnClickListener()
        observeViewModelChanges()
    }

    /**
     * 处理悬浮窗权限请求结果
     * @param resultCode 来自系统设置页面的返回码
     */
    private fun handlePermissionResult(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            if (!Settings.canDrawOverlays(this)) {
                showToast("授权失败")
            } else {
                showToast("授权成功")
                startFloatingService()
            }
        }
    }


    /**
     * 变更服务状态
     * @param isChecked true开启服务，false关闭服务
     */
    private fun switchServiceState(isChecked: Boolean) {
        if (isChecked) {
            startFloatingService()
        } else {
            stopFloatingService()
        }
    }

    /**
     * 启动悬浮窗服务
     */
    private fun startFloatingService() {
        startService(Intent(this, FloatingClockService::class.java))
    }

    /**
     * 关闭悬浮窗服务
     */
    private fun stopFloatingService() {
        if (FloatingClockService.isStarted) {
            stopService(Intent(this, FloatingClockService::class.java))
        }
    }

    /**
     * 显示弹框
     * @param message 弹框信息
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 注册点击事件
     */
    private fun setOnClickListener() {
        mClockBinding.modifyBt.setOnClickListener {
            updateClockData()
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


    private fun observeViewModelChanges() {
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
    }

    /**
     * 更新时钟样式
     */
    private fun updateClockData() {
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