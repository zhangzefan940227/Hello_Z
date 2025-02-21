package com.helloz.app.ui.submenu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.databinding.ActivityNetworkStructureBinding
import com.helloz.app.utils.LogUtils
import com.helloz.app.utils.MenuUtils

class NetworkStructureActivity : BaseActivity() {
    private val tag = this::class.simpleName ?: "NetworkStructureActivity"
    private lateinit var mNetworkStructureBinding: ActivityNetworkStructureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        LogUtils.i(tag, "init")
        mNetworkStructureBinding = DataBindingUtil.setContentView(this, R.layout.activity_network_structure)
        mMenuModelList = getMenuModelList(MenuUtils.NETWORK_STRUCTURE_MENU_LIST_PATH)
        mBaseAdapter = getBaseAdapter(mMenuModelList)
        mNetworkStructureBinding.networkStructureMenuRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mNetworkStructureBinding.networkStructureMenuRv.adapter = mBaseAdapter
        setOnClickListener(this, mBaseAdapter)
    }
}