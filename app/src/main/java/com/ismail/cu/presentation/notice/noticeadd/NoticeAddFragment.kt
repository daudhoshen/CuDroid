package com.ismail.cu.presentation.notice.noticeadd

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentNoticeAddBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class NoticeAddFragment : BaseFragment<FragmentNoticeAddBinding>(
    onInflate = FragmentNoticeAddBinding::inflate
) {
    private val viewModel: NoticeAddViewModel by viewModels {
        NoticeAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(savedInstanceState: Bundle?) {

        binding.saveButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateNoticeInputAndUploadToFirebase(
                binding.noticeTitle, binding.noticeDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}