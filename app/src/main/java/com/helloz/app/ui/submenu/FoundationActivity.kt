package com.helloz.app.ui.submenu

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityFoundationBinding
import com.helloz.app.utils.MenuUtils

class FoundationActivity : BaseActivity() {
    private val TAG = "FoundationActivity"
    private lateinit var mFoundationBinding: ActivityFoundationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        mFoundationBinding = DataBindingUtil.setContentView(this, R.layout.activity_foundation)
        mMenuModelList = getMenuModelList(MenuUtils.FOUNDATION_MENU_LIST_PATH)
        mBaseAdapter = getBaseAdapter(mMenuModelList)
        mFoundationBinding.foundationMenuRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mFoundationBinding.foundationMenuRv.adapter = mBaseAdapter
        // 设置点击事件监听器
        setOnClickListener(this, mBaseAdapter)
    }
}