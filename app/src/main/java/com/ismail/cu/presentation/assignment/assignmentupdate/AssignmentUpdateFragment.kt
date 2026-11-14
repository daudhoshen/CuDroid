package com.ismail.cu.presentation.assignment.assignmentupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentAssignmentUpdateBinding
import com.ismail.cu.model.AssignmentData
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class AssignmentUpdateFragment : BaseFragment<FragmentAssignmentUpdateBinding>(
    onInflate = FragmentAssignmentUpdateBinding::inflate
) {
    private val viewModel: AssignmentUpdateViewModel by viewModels {
        AssignmentUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(savedInstanceState: Bundle?) {

        binding.assignmentTitle.setText(arguments?.getString("title"))
        binding.assignmentDetails.setText(arguments?.getString("details"))

        binding.updateButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateAssignmentInputAndUploadToFirebase(
                key = arguments?.getString("key"),
                assignmentTitleEt = binding.assignmentTitle,
                assignmentDetailsEt = binding.assignmentDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun generateBundle(assignmentData: AssignmentData?) : Bundle = Bundle().apply {
            putString("title", assignmentData?.title)
            putString("details", assignmentData?.details)
            putString("created_at", assignmentData?.createdAt)
            putString("key", assignmentData?.key)
        }
    }
}