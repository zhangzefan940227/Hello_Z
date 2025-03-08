package com.helloz.app.repository

import android.graphics.drawable.Drawable
import com.helloz.app.model.FloatingClockModel

class FloatingClockRepository {
    private var mClockModel: FloatingClockModel = FloatingClockModel.getDefault()

    public fun updateClockData(textSize: Float?, textColor: String?, bgColor: Drawable?): FloatingClockModel {
        mClockModel.textSize = textSize
        mClockModel.textColor = textColor
        mClockModel.bgColor = bgColor
        return mClockModel
    }

    public fun getDefault() : FloatingClockModel {
        return FloatingClockModel.getDefault()
    }
}