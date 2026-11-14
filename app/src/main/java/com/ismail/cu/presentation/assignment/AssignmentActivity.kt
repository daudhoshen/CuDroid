package com.ismail.cu.presentation.assignment

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.databinding.ActivityAssignmentBinding

class AssignmentActivity : BaseActivity<ActivityAssignmentBinding>(
    onInflate = ActivityAssignmentBinding::inflate
) {
    override fun initOnCreate(savedInstanceState: Bundle?) {
        try {
            (supportFragmentManager.findFragmentById(
                R.id.fragment_container
            ) as NavHostFragment?)?.navController?.addOnDestinationChangedListener {
                _: NavController?, destination: NavDestination, _: Bundle? ->
                binding.toolBar.title.text = destination.label
            }
        } catch (_: Exception) { }

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}