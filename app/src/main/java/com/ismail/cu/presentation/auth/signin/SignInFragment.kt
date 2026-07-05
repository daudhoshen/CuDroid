package com.ismail.cu.presentation.auth.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentSigninBinding
import com.ismail.cu.presentation.dashboard.DashboardActivity
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class SignInFragment : BaseFragment<FragmentSigninBinding>(
    viewBindingFactory = FragmentSigninBinding::inflate
) {
    private val viewModel: SignInViewModel by viewModels {
        SignInViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.signInButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateInputItemsAndExecuteSignup(
                binding.emailInput, binding.passwordInput,
                FirebaseAuth.getInstance()
            )
        }
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_signInFragment_to_signupFragment
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) {
            startActivity(Intent(context, DashboardActivity::class.java))
            activity?.finish()
        }
    }
}