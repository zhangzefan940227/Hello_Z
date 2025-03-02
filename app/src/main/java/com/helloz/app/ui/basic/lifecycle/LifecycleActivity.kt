package com.helloz.app.ui.basic.lifecycle

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityLifecycleBinding
import com.helloz.app.utils.LogUtils

class LifecycleActivity : BaseActivity(), DataCallback {
    private val tag = this::class.simpleName ?: "LifeCycleActivity"

    private var activityStr: StringBuilder = StringBuilder()
    lateinit var mLifecycleBinding: ActivityLifecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.i(tag, "onCreate")
        init()
        updateContent("onCreate\n")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.i(tag, "onStart")
        updateContent("onStart\n")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.i(tag, "onResume")
        updateContent("onResume\n")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.i(tag, "onPause")
        updateContent("onPause\n")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.i(tag, "onStop")
        updateContent("onStop\n")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i(tag, "onDestroy")
    }

    private fun init() {
        mLifecycleBinding = DataBindingUtil.setContentView(this, R.layout.activity_lifecycle)
        lifecycle.addObserver(LifecycleObserver("$tag observer", this))
    }

    private fun updateContent(content: String) {
        activityStr.append(content)
        mLifecycleBinding.lifecycleActivityTv.text = activityStr.toString()
    }

    override fun onDataReceived(data: String) {
        mLifecycleBinding.lifecycleObserverTv.text = data
    }
}

open class LifecycleObserver(private val tag: String, private val callback: DataCallback) : DefaultLifecycleObserver {
    private var observerStr: StringBuilder = StringBuilder()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        LogUtils.i(tag, "lifecycle observer: onCreate")
        updateContent("onCreate\n")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        LogUtils.i(tag, "lifecycle observer: onStart")
        updateContent("onStart\n")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        LogUtils.i(tag, "lifecycle observer: onResume")
        updateContent("onResume\n")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        LogUtils.i(tag, "lifecycle observer: onPause")
        updateContent("onPause\n")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        LogUtils.i(tag, "lifecycle observer: onStop")
        updateContent("onStop\n")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        LogUtils.i(tag, "lifecycle observer: onDestroy")
    }

    private fun updateContent(content: String) {
        observerStr.append(content)
        callback.onDataReceived(observerStr.toString())
    }
}

interface DataCallback {
    fun onDataReceived(data: String)
}