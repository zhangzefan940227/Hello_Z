package com.helloz.app.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.helloz.app.R
import com.helloz.app.impl.BindDataListenerImpl
import com.helloz.app.interfaces.OnItemClickListener
import com.helloz.app.model.MenuModel
import com.helloz.app.utils.LogUtils
import com.helloz.app.utils.MenuUtils
import com.helloz.app.utils.XmlPullParserUtils
import java.util.Objects

open class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"

    open lateinit var mMenuModelList: List<MenuModel>
    open lateinit var mBaseAdapter: BaseAdapter<MenuModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 沉浸式状态栏
         * enableEdgeToEdge() 方法通常用于启用边缘到边缘布局，从而将应用程序界面元素与系统UI（如状态栏和导航栏）进行协调，以实现沉浸式体验。
         * 具体来说，它的作用是调整应用程序界面元素的布局和系统UI设置，以使内容能够无缝地延伸至设备屏幕的边缘，消除常规情况下的屏幕边缘空白或系统栏（如状态栏和导航栏）
         */
        enableEdgeToEdge()
        LogUtils.i(TAG, "onCreate")
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

    /**
     * 通过xml文件获取菜单列表, 达到动态配置菜单的目的
     *
     * @param path xml文件路径
     * @return 菜单列表
     */
    fun getMenuModelList(path: String): List<MenuModel> {
        try {
            val menuStream =
                Objects.requireNonNull(javaClass.classLoader).getResourceAsStream(path)
            return XmlPullParserUtils.getMenuList(menuStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    /**
     * 创建BaseAdapter对象，并设置点击事件监听器。
     *
     * @param menuModelList 菜单列表
     * @return BaseAdapter对象
     */
    fun getBaseAdapter(menuModelList: List<MenuModel>): BaseAdapter<MenuModel> {
        // 创建BaseAdapter对象，并设置点击事件监听器。
        return BaseAdapter(menuModelList,
            object : BindDataListenerImpl<MenuModel>(R.id.menu_item_tv, R.layout.menu_item, R.id.menu_item_tv) {
                override fun onBindViewHolder(model: MenuModel, viewHolder: BaseViewHolder?, type: Int, position: Int) {
                    viewHolder?.setText(R.id.menu_item_tv, model.getTitle().toString())
                }
            }
        )
    }

    /**
     * 设置点击事件监听器。
     *
     * @param context 上下文对象
     * @param adapter BaseAdapter对象
     */
    fun setOnClickListener(context: Context, adapter: BaseAdapter<MenuModel>) {
        adapter.setClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                LogUtils.i(TAG, "onItemClick: ")
                val intent = Intent(context, mMenuModelList[position].getJumpToWhere())
                startActivity(intent)
            }
        })
    }

    // TODO 每次新建Activity自动添加的代码，暂时还不理解这里具体是做什么的，待后续补充
    fun insetsCompat(id: Int) {
        // 用于设置视图的内边距
        // 通过ViewCompat.setOnApplyWindowInsetsListener方法为指定的视图(通过findViewById方法获取)设置了一个窗口窗口内边距监听器。
        // 当视图的窗口内边距发生变化时，会调用这个监听器的onApplyWindowInsets方法，并传递一个WindowInsetsCompat对象作为参数。
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id),
            // 获取WindowInsetsCompat对象中的系统栏位的边距信息，并将其设置为View对象的内边距。最后返回原始的WindowInsetsCompat对象。
            fun(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                return insets
            })
    }
}