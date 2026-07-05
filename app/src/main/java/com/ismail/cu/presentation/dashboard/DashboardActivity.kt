package com.ismail.cu.presentation.dashboard

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(
    viewBindingFactory = ActivityDashboardBinding::inflate
){
    override fun initOnCreate(savedInstanceState: Bundle?) {

        setupWithNavController(
            binding.bottomNav, findNavController(this, R.id.fragment_container)
        )
    }
}