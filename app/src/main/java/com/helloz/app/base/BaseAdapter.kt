package com.helloz.app.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.helloz.app.interfaces.IBindDataListener
import com.helloz.app.interfaces.IMoreBindDataListener
import com.helloz.app.interfaces.OnItemClickListener

/**
 * @author: zzfan
 * @date: 2024年4月13日
 * @description: 定义一个基础的Adapter
 */
open class BaseAdapter<T : Any>(
    private var mList: List<T>,
    private var mBindDataListener: IBindDataListener<T>
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mClickListener: OnItemClickListener? = null
    private var mMoreBindDataListener: IMoreBindDataListener<T>? = null

    override fun getItemViewType(position: Int): Int {
        if (mMoreBindDataListener != null) {
            return mMoreBindDataListener!!.getItemType(position)
        }
        return 0
    }

    // 创建ViewHolder实例的工厂方法
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutId = mBindDataListener.getLayoutId(viewType)
        val clickedItemId = mBindDataListener.getClickedItemId(viewType)
        val view: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val baseViewHolder = BaseViewHolder.getBaseViewHolder(parent.context, view)
        if (clickedItemId != -1) {
            view.findViewById<View>(clickedItemId).setOnClickListener {
                mClickListener!!.onItemClick(
                    view,
                    baseViewHolder.adapterPosition
                )
            }
        }
        return baseViewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        mBindDataListener.onBindViewHolder(mList.get(position), holder, getItemViewType(position), position)
    }

    // 返回数据源中数据的个数
    override fun getItemCount(): Int {
        return mList.size
    }

    fun setClickListener(listener: OnItemClickListener) {
        mClickListener = listener
    }
}