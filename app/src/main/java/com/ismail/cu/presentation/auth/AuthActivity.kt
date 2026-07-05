package com.ismail.cu.presentation.auth

import android.os.Bundle
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.databinding.ActivityAuthBinding
import com.ismail.cu.util.getColorCompat

class AuthActivity : BaseActivity<ActivityAuthBinding>(
    viewBindingFactory = ActivityAuthBinding::inflate
) {
    override fun initOnCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = resources.getColorCompat(R.color.color_window)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.statusBarColor = resources.getColorCompat(R.color.color_primary)
    }
}