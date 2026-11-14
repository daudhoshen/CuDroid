package com.ismail.cu.presentation.assignment.assignmentview

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentAssignmentViewBinding
import com.ismail.cu.widget.PrimaryLoader

class AssignmentViewFragment : BaseFragment<FragmentAssignmentViewBinding>(
    onInflate = FragmentAssignmentViewBinding::inflate
) {
    private val viewModel: AssignmentViewViewModel by viewModels {
        AssignmentViewViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(savedInstanceState: Bundle?) {

        viewModel.fetchAssignmentFromBundle(arguments?.getString("assignment_data"))

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            binding.createdAt.text = String.format("Created At: %s", it?.createdAt ?: "")
            binding.assignmentTitle.setText(it?.title ?: "")
            binding.assignmentDetails.setText(it?.details ?: "")
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}