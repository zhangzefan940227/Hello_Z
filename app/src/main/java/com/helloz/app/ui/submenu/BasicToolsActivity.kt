package com.helloz.app.ui.submenu

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityBasicToolsBinding
import com.helloz.app.utils.LogUtils
import com.helloz.app.utils.MenuUtils

/**
 * @author: zzfan
 * @date: 2024年4月20日
 * @description: 四大基本组件菜单界面
 */
class BasicToolsActivity : BaseActivity() {
    private val tag = this::class.simpleName ?: "BasicToolsActivity"
    private lateinit var mBasicToolsBinding: ActivityBasicToolsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        LogUtils.i(tag, "init")
        mBasicToolsBinding = DataBindingUtil.setContentView(this, R.layout.activity_basic_tools)
        mMenuModelList = getMenuModelList(MenuUtils.BASIC_TOOLS_MENU_LIST)
        mBaseAdapter = getGridAdapter(mMenuModelList)
        mBasicToolsBinding.foundationMenuRv.layoutManager = GridLayoutManager(this, 2)
        mBasicToolsBinding.foundationMenuRv.adapter = mBaseAdapter
        setOnClickListener(this, mBaseAdapter)
    }
}