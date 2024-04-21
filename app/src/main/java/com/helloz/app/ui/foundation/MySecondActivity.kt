package com.helloz.app.ui.foundation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityFoundationSecondBinding
import com.helloz.app.utils.CommonUtils

class MySecondActivity : BaseActivity() {
    private val TAG = "MySecondActivity"
    private lateinit var mMySecondBinding: ActivityFoundationSecondBinding
    private lateinit var mTextView: TextView
    private lateinit var mMyIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMySecondBinding = DataBindingUtil.setContentView(this, R.layout.activity_foundation_second)
        init()
    }

    private fun init() {
        mMyIntent = intent
        mTextView = mMySecondBinding.text
    }

    override fun onResume() {
        super.onResume()
        val text: String = mMyIntent.getStringExtra(CommonUtils.TOAST) ?: ""
        mTextView.setText(text)
    }
}