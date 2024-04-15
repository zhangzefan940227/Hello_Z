package com.helloz.app.model

class MenuModel() {

    private var title: String? = null

    private var jumpActivity: Class<*>? = null

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setJumpToWhere(jumpActivity: Class<*>?) {
        this.jumpActivity = jumpActivity
    }

    fun getTitle(): String? {
        return title
    }

    fun getJumpToWhere(): Class<*>? {
        return jumpActivity
    }
}