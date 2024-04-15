package com.helloz.app.impl

import com.helloz.app.base.BaseViewHolder
import com.helloz.app.interfaces.IBindDataListener

open class BindDataListenerImpl<T>() : IBindDataListener<T> {

    private var viewId: Int = 0
    private var layoutId: Int = 0
    private var itemId: Int = 0

    constructor(viewId: Int, layoutId: Int, itemId: Int) : this() {
        this.viewId = viewId
        this.layoutId = layoutId
        this.itemId = itemId
    }

    open override fun onBindViewHolder(model: T, viewHolder: BaseViewHolder?, type: Int, position: Int) {
        viewHolder?.setText(this.viewId, model.toString())
    }

    open override fun getLayoutId(type: Int): Int {
        return this.layoutId
    }

    open override fun getClickedItemId(type: Int): Int {
        return this.itemId
    }
}