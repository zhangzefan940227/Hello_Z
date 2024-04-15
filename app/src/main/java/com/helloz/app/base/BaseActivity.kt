package com.helloz.app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloz.app.impl.BindDataListenerImpl
import com.helloz.app.model.MenuModel
import com.helloz.app.utils.LogUtils

open class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"

    lateinit var mMenuModelList: List<MenuModel>
    lateinit var mLayoutManager: LinearLayoutManager

    open lateinit var mBaseAdapter: MyBaseAdapter<MenuModel>
    open lateinit var mBindDataListenerImpl: BindDataListenerImpl<MenuModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.i(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.i(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i(TAG, "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtils.i(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtils.i(TAG, "onRestoreInstanceState")
    }

    override fun onNewIntent(intent: android.content.Intent?) {
        super.onNewIntent(intent)
        LogUtils.i(TAG, "onNewIntent")
    }
}