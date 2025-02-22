package com.helloz.app.ui.tools

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityFloatingClockBinding

class FloatingClockActivity : BaseActivity() {
    private val tag: String = this::class.simpleName ?: "FloatingClockActivity"
    private lateinit var mFloatingClockBinding: ActivityFloatingClockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        mFloatingClockBinding = DataBindingUtil.setContentView(this, R.layout.activity_floating_clock)
    }
}