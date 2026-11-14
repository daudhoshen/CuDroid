package com.ismail.cu.presentation.assignment.assignmentlist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentAssignmentListBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeVisible
import com.ismail.cu.widget.PrimaryLoader

class AssignmentListFragment : BaseFragment<FragmentAssignmentListBinding>(
    onInflate = FragmentAssignmentListBinding::inflate
) {
    private val viewModel: AssignmentListViewModel by viewModels {
        AssignmentListViewModel.Factory()
    }
    private val adapter: AssignmentListAdapter by lazy {
        AssignmentListAdapter(
            onClickListener = {
                viewModel.navigateToAssignmentViewFragment(it)
            },
            onUpdateClickListener = {
                viewModel.navigateToAssignmentUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(savedInstanceState: Bundle?) {

        viewModel.fetchAssignmentListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().admin
        if (adapter.isAdmin == true) binding.addAssignmentButton.makeVisible()

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.assignmentRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addAssignmentButton.setOnClickListener {
            viewModel.fireNavigateEvent(
               R.id.action_assignmentListFragment_to_addAssignmentFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}