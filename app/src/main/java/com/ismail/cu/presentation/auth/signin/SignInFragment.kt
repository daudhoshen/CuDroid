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
import com.ismail.cu.widget.PrimaryLoader

class SignInFragment : BaseFragment<FragmentSigninBinding>(
    onInflate = FragmentSigninBinding::inflate
) {
    private val viewModel: SignInViewModel by viewModels {
        SignInViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(
        savedInstanceState: Bundle?
    ) {
        binding.emailInput.setText("mdismailmollah2002@gmail.com")
        binding.passwordInput.setText("12345678")

        binding.signInButton.setOnClickListener {
            viewModel.validateInputItemsAndExecuteSignup(
                emailInputEt = binding.emailInput,
                passwordInputEt = binding.passwordInput,
                firebaseAuth = FirebaseAuth.getInstance()
            )
        }
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(
                resId = R.id.action_signInFragment_to_signupFragment
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) {
            startActivity(
                Intent(
                    context,
                    DashboardActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
        }
    }
}