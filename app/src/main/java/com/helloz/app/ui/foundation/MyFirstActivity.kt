package com.helloz.app.ui.foundation

import android.os.Bundle
import com.helloz.app.R
import com.helloz.app.base.BaseActivity

class MyFirstActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_first)
    }
}