package com.helloz.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.helloz.app.R
import com.helloz.app.base.BaseActivity
import com.helloz.app.base.BaseViewHolder
import com.helloz.app.base.MyBaseAdapter
import com.helloz.app.databinding.ActivityMainBinding
import com.helloz.app.impl.BindDataListenerImpl
import com.helloz.app.interfaces.OnItemClickListener
import com.helloz.app.model.MenuModel
import com.helloz.app.utils.LogUtils
import com.helloz.app.utils.XmlPullParserUtils

import java.util.Objects

/**
 * @author: zzfan
 * @date: 2024年4月15日
 * @description: 应用主界面
 */
class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private val MENU_FILE_PATH = "assets/main_menu_list.xml"

    // lateinit 用于延迟初始化变量, 在声明时不立即初始化，而是延迟到使用时再初始化。
    private lateinit var mMainBinding: ActivityMainBinding

    // 创建Activity时调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 沉浸式状态栏
         * enableEdgeToEdge() 方法通常用于启用边缘到边缘布局，从而将应用程序界面元素与系统UI（如状态栏和导航栏）进行协调，以实现沉浸式体验。
         * 具体来说，它的作用是调整应用程序界面元素的布局和系统UI设置，以使内容能够无缝地延伸至设备屏幕的边缘，消除常规情况下的屏幕边缘空白或系统栏（如状态栏和导航栏）
         */
//        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        // 用于设置视图的内边距
        // 通过ViewCompat.setOnApplyWindowInsetsListener方法为指定的视图(通过findViewById方法获取)设置了一个窗口窗口内边距监听器。
        // 当视图的窗口内边距发生变化时，会调用这个监听器的onApplyWindowInsets方法，并传递一个WindowInsetsCompat对象作为参数。
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
            // 获取WindowInsetsCompat对象中的系统栏位的边距信息，并将其设置为View对象的内边距。最后返回原始的WindowInsetsCompat对象。
            fun(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                return insets
            })
        init()
    }

    fun init() {
        getMenuList()

        // 通过DataBindingUtil.setContentView方法将activity_main.xml布局文件绑定到Activity，并获取Activity的根视图。
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 创建LinearLayoutManager对象，用于管理RecyclerView的布局，并设置LinearLayoutManager的布局方向为垂直方向。
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 将LinearLayoutManager对象设置给RecyclerView的布局管理器。
        mMainBinding.mainMenuRv.layoutManager = mLayoutManager

        // 创建MyBaseAdapter对象，并设置点击事件监听器。
        mBaseAdapter = MyBaseAdapter(
            mMenuModelList,
            object : BindDataListenerImpl<MenuModel>(R.id.menu_item_tv, R.layout.menu_item, R.id.menu_item_tv) {
                override fun onBindViewHolder(model: MenuModel, viewHolder: BaseViewHolder?, type: Int, position: Int) {
                    viewHolder?.setText(R.id.menu_item_tv, model.getTitle().toString())
                }
            }
        )

        // 将刚刚创建好的MyBaseAdapter对象设置给RecyclerView的适配器。
        mMainBinding.mainMenuRv.adapter = mBaseAdapter

        // 设置点击事件监听器
        mBaseAdapter.setClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                LogUtils.i(TAG, "onItemClick: ")
                val intent = Intent(this@MainActivity, mMenuModelList[position].getJumpToWhere())
                startActivity(intent)
            }
        })
    }

    /**
     * 通过xml文件获取菜单列表, 达到动态配置菜单的目的
     */
    fun getMenuList() {
        try {
            // 获取assets目录下的main_menu_list.xml文件的字节流
            val menuStream =
                Objects.requireNonNull(javaClass.classLoader).getResourceAsStream(MENU_FILE_PATH)
            // 解析main_menu_list.xml文件，获取菜单列表
            mMenuModelList = XmlPullParserUtils.getMenuList(menuStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}