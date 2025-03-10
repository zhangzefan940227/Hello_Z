package com.helloz.app.ui.basic.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityFoundationFirstBinding
import com.helloz.app.utils.CommonUtils
import com.helloz.app.utils.LogUtils

class FoundationFirstActivity : BaseActivity(), View.OnClickListener {
    private val tag = "MyFirstActivity"
    private lateinit var mMyFirstBinding: ActivityFoundationFirstBinding
    private lateinit var mEditText: EditText
    private lateinit var mToastBtn: Button
    private lateinit var mJumpBtn: Button

    private var mToastText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        LogUtils.i(tag, "init")
        mMyFirstBinding = DataBindingUtil.setContentView(this, R.layout.activity_foundation_first)
        mEditText = mMyFirstBinding.editText
        mToastBtn = mMyFirstBinding.toastBtn
        mJumpBtn = mMyFirstBinding.jumpBtn

        mToastBtn.setOnClickListener(this)
        mJumpBtn.setOnClickListener(this)
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        mToastText = mEditText.text.toString()
        when {
            TextUtils.isEmpty(mToastText) -> {
                mToastText = CommonUtils.DEFAULT_MESSAGE
            }
        }

        when (v?.id) {
            R.id.toast_btn -> {
                Toast.makeText(this, mToastText, Toast.LENGTH_SHORT).show()
            }

            R.id.jump_btn -> {
                if (true) {
                    // 显式调用
                    val myIntent: Intent = Intent(this, FoundationSecondActivity::class.java)
                    myIntent.putExtra(CommonUtils.TOAST, mToastText)
                    startActivity(myIntent)
                } else {
                    // 隐式调用
                    val myIntent: Intent = Intent("com.helloz.foundation.second.ACTION_SECOND")
                    myIntent.putExtra(CommonUtils.TOAST, mToastText)
                    startActivity(myIntent)
                }
            }
        }
    }
}