package com.ismail.cu.presentation.module.moduleadd

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentModuleAddBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.getCurrentDate
import com.ismail.cu.widget.PrimaryLoader

class ModuleAddFragment : BaseFragment<FragmentModuleAddBinding>(
    viewBindingFactory = FragmentModuleAddBinding::inflate
) {
    private val viewModel: ModuleAddViewModel by viewModels {
        ModuleAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.saveButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateModuleInputsAndUploadToFirebase(
                binding.moduleTitle, binding.moduleLink, getCurrentDate()
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}