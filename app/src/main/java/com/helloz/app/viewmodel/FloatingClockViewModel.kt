package com.helloz.app.viewmodel

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.helloz.app.model.FloatingClockModel
import com.helloz.app.repository.FloatingClockRepository

class FloatingClockViewModel(application: Application) : AndroidViewModel(application) {

    public val clockLiveData = MutableLiveData<FloatingClockModel>().apply { FloatingClockModel.getDefault() }
    public val mRepository = FloatingClockRepository()

    public fun updateClockData(textSize: Float?, textColor: String?, bgColor: Drawable?) {
        clockLiveData.postValue(mRepository.updateClockData(textSize, textColor, bgColor))
    }

    public fun resetClockData() {
        clockLiveData.postValue(mRepository.getDefault())
    }
}