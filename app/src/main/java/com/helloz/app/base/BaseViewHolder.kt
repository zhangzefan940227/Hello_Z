package com.helloz.app.base

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder// 构造函数
    (private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mViews: SparseArray<View> = SparseArray()


    // 静态方法, 传入上下文和布局id, 返回一个BaseViewHolder
    companion object {
        fun getBaseViewHolder(context: Context, itemView: View) = BaseViewHolder(context, itemView)
    }

    /**
     * 提供给子类访问View的方法
     *
     * @param viewId view ID.
     * @param <T> View类型.
     * @return 返回泛型类型的View.
     */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 设置TextView的文本
     *
     * @param viewId view ID.
     * @param text   文本.
     * @return 返回当前BaseViewHolder对象.
     */
    fun setText(viewId: Int, text: String): BaseViewHolder {
        val textView: TextView = getView(viewId)
        textView.setText(text)
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): BaseViewHolder {
        val imageView: ImageView = getView(viewId)
        imageView.setImageResource(resId)
        return this
    }
}