package com.helloz.app.ui.main

import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager

import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityMainBinding
import com.helloz.app.utils.MenuUtils

/**
 * @author: zzfan
 * @date: 2024年4月15日
 * @description: 应用主界面
 */
class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    // lateinit 用于延迟初始化变量, 在声明时不立即初始化，而是延迟到使用时再初始化。
    private lateinit var mMainBinding: ActivityMainBinding

    // 创建Activity时调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        // 通过DataBindingUtil.setContentView方法将activity_main.xml布局文件绑定到Activity，并获取Activity的根视图。
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // 初始化菜单列表
        mMenuModelList = getMenuModelList(MenuUtils.MAIN_MENU_LIST_PATH)
        // 初始化BaseAdapter对象
        mBaseAdapter = getBaseAdapter(mMenuModelList)
        // 创建LinearLayoutManager对象，用于管理RecyclerView的布局，并设置LinearLayoutManager的布局方向为垂直方向。
        // 将LinearLayoutManager对象设置给RecyclerView的布局管理器。
        mMainBinding.mainMenuRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // 将刚刚创建好的BaseAdapter对象设置给RecyclerView的适配器。
        mMainBinding.mainMenuRv.adapter = mBaseAdapter
        // 设置点击事件监听器
        setOnClickListener(this, mBaseAdapter)
    }
}