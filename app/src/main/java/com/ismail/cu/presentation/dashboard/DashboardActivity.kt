package com.ismail.cu.presentation.dashboard

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(
    onInflate = ActivityDashboardBinding::inflate,
    reserveNavInset = false
) {
    override fun initOnCreate(savedInstanceState: Bundle?) {
        setupWithNavController(
            navigationBarView = binding.bottomNav,
            navController = (
                    supportFragmentManager.findFragmentById(R.id.fragment_container)
                            as NavHostFragment
                    ).navController
        )
    }
}