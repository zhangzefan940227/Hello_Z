package com.helloz.app.interfaces

import com.helloz.app.base.BaseViewHolder

interface IBindDataListener<T> {
    fun onBindViewHolder(model: T, viewHolder: BaseViewHolder?, type: Int, position: Int)

    fun getLayoutId(type: Int): Int

    fun getClickedItemId(type: Int): Int
}