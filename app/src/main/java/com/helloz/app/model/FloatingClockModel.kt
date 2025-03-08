package com.helloz.app.model

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable


data class FloatingClockModel(var textSize: Float?, var textColor: String?, var bgColor: Drawable?) {

    companion object {
        @JvmStatic
        public fun getDefault(): FloatingClockModel {
            val bgDrawable = ColorDrawable(Color.parseColor("#40000000"))
            return FloatingClockModel(30f, "#FFFFFF", bgDrawable)
        }
    }
}