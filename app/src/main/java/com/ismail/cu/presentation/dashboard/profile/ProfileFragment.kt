package com.ismail.cu.presentation.dashboard.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentProfileBinding
import com.ismail.cu.presentation.auth.AuthActivity
import com.ismail.cu.presentation.classmatelist.ClassmateListActivity
import com.ismail.cu.presentation.dashboard.home.HomeViewModel
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    onInflate = FragmentProfileBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(savedInstanceState: Bundle?) {

        viewModel.userProfileDataLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.userInfo.text = String.format(
                    "%s\n%s",
                    "${it.name} ${if (it.admin == true) "(Admin)" else ""}",
                    SharedPref.getUserBatch()
                )
                binding.studentId.text = it.studentId ?: ""
                binding.mobileNumber.text = it.phone ?: ""
                binding.email.text = it.email ?: ""
                binding.gender.text = it.gender ?: ""
                binding.blood.text = it.bloodGroup ?: ""
            }
        }
        binding.signOutButton.setOnClickListener {
            viewModel.attemptSignOutUser(FirebaseAuth.getInstance())
        }
        binding.classMatesButton.setOnClickListener {
            viewModel.fireNavigateEvent(1,null)
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        when (id) {
            0 -> {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }

            1 -> {
                startActivity(Intent(requireContext(), ClassmateListActivity::class.java))
            }
        }
    }
}