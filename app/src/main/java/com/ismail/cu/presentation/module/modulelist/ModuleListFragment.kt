package com.ismail.cu.presentation.module.modulelist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentModuleListBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeVisible
import com.ismail.cu.util.showToast
import com.ismail.cu.widget.PrimaryLoader

class ModuleListFragment : BaseFragment<FragmentModuleListBinding>(
    viewBindingFactory = FragmentModuleListBinding::inflate
) {
    private val viewModel: ModuleListViewModel by viewModels {
        ModuleListViewModel.Factory()
    }
    private val adapter: ModuleListAdapter by lazy {
        ModuleListAdapter(
            onClickListener = {
                try {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(it?.link))
                    )
                } catch (e: Exception) {
                    context.showToast(e.localizedMessage)
                }
            },
            onUpdateClickListener = {
                viewModel.navigateToModuleUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchModuleListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().admin
        if (adapter.isAdmin == true) binding.addButton.makeVisible()

        viewModel.moduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.moduleRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_moduleListFragment_to_moduleAddFragment,null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}