package com.helloz.app.ui.submenu

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityBasicFunctionsBinding
import com.helloz.app.utils.LogUtils
import com.helloz.app.utils.MenuUtils

/**
 * @author: zzfan
 * @date: 2024年4月20日
 * @description: 基本功能学习
 */
class BasicFunctionsActivity : BaseActivity() {
    private val tag = this::class.simpleName ?: "BasicToolsActivity"
    private lateinit var mBasicToolsBinding: ActivityBasicFunctionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        LogUtils.i(tag, "init")
        mBasicToolsBinding = DataBindingUtil.setContentView(this, R.layout.activity_basic_functions)
        mMenuModelList = getMenuModelList(MenuUtils.BASIC_TOOLS_MENU_LIST_PATH)
        mBaseAdapter = getBaseAdapter(mMenuModelList)
//        mBasicToolsBinding.foundationMenuRv.layoutManager = GridLayoutManager(this, 2)
        mBasicToolsBinding.basicFunctionRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBasicToolsBinding.basicFunctionRv.adapter = mBaseAdapter
        setOnClickListener(this, mBaseAdapter)
    }
}