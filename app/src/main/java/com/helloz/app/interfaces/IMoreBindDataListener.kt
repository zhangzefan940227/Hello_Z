package com.helloz.app.interfaces


interface IMoreBindDataListener<T> : IBindDataListener<T> {
    fun getItemType(position: Int): Int
}