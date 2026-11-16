package com.ismail.cu.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
//import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.enableEdgeToEdgeXmlActivity
import com.ismail.cu.util.setupSystemBarsAppearance

abstract class BaseActivity<VB: ViewBinding>(
    private val onInflate: (LayoutInflater) -> VB,
    private val reserveNavInset: Boolean = true,
    private val isLightSystemBars: Boolean = true
) : AppCompatActivity() {
    protected lateinit var binding: VB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        if (Build.VERSION.SDK_INT < 30) window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        else window?.setDecorFitsSystemWindows(true)*/
        enableEdgeToEdgeXmlActivity(
            reserveNavInset = reserveNavInset
        )
        setupSystemBarsAppearance(
            isLightSystemBars = isLightSystemBars
        )
        binding = onInflate(layoutInflater)
        setContentView(binding.root)
        SharedPref.init(context = this)
        initOnCreate(savedInstanceState)
    }

    abstract fun initOnCreate(savedInstanceState: Bundle?)
}