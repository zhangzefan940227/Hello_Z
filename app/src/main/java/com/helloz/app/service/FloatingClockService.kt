package com.helloz.app.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.helloz.app.R
import java.text.SimpleDateFormat
import java.util.Date

/**
 * 悬浮时钟服务类，用于创建和管理悬浮窗口显示实时时钟
 * 功能包含：窗口创建/更新、触摸拖动、时间实时刷新等
 */
class FloatingClockService : Service() {

    private lateinit var windowManager: WindowManager
    public lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var displayView: View

    companion object {
        private val tag: String = this::class.simpleName ?: "FloatingClockService"
        var isStarted = false
    }

    override fun onCreate() {
        super.onCreate()
        initFloatingClock()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showFloatingWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        isStarted = false
        windowManager.removeViewImmediate(displayView)
    }

    /**
     * 初始化悬浮窗口参数
     */
    private fun initFloatingClock() {
        // 标记服务已启动
        isStarted = true
        // 获取WindowManager服务
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        // 初始化LayoutParams对象
        layoutParams = WindowManager.LayoutParams()
        // 设置窗口类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        // 设置窗口格式
        layoutParams.format = PixelFormat.RGBA_8888

        // 设置窗口的标志，窗口不获取焦点且不拦截触摸事件
        layoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        // 设置窗口的宽度和高度
        layoutParams.width = 400;
        layoutParams.height = 200;
        // 设置窗口的初始位置
        layoutParams.x = 0;
        layoutParams.y = 0;
    }

    /**
     * 显示悬浮窗口并启动时间刷新
     */
    @SuppressLint("SetTextI18n")
    private fun showFloatingWindow() {
        // 检查是否有悬浮窗权限
        if (Settings.canDrawOverlays(this)) {
            // 获取LayoutInflater服务
            val layoutInflater = LayoutInflater.from(this);
            // 加载悬浮窗布局
            displayView = layoutInflater.inflate(R.layout.image_diaplay, null);
            // 设置触摸监听器，使悬浮窗可以拖动
            displayView.setOnTouchListener(FloatingOnTouchListener());
            // 获取TextView控件
            val textView: TextView = displayView.findViewById(R.id.textView);

            // 创建Handler对象
            val handler: Handler = Handler(Looper.getMainLooper());
            handler.post(object : Runnable {
                override fun run() {
                    // 获取当前时间
                    val currentTime = System.currentTimeMillis();
                    val date: Date = Date(currentTime);
                    // 格式化时间戳精确到秒
                    val sdf = SimpleDateFormat("HH:mm:ss:SSS");
                    val formattedTime = sdf.format(date);
                    // 设置TextView的文本内容
                    textView.setText(formattedTime + "");
                    // 延迟1秒后再次执行
                    handler.postDelayed(this, 10);
                }
            })

            // 将悬浮窗添加到窗口中
            windowManager.addView(displayView, layoutParams);
        }
    }

    /**
     * 触摸事件监听器实现窗口拖动
     */
    inner class FloatingOnTouchListener : View.OnTouchListener {
        private var x: Int = 0
        private var y: Int = 0


        override fun onTouch(view: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }

                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt();
                    val nowY = event.rawY.toInt();
                    val movedX = nowX - x;
                    val movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x += movedX;
                    layoutParams.y += movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                }
            }
            return false
        }
    }
}