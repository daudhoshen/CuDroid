package com.ismail.cu.presentation.notice.noticeview

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentNoticeViewBinding
import com.ismail.cu.widget.PrimaryLoader

class NoticeViewFragment : BaseFragment<FragmentNoticeViewBinding>(
    viewBindingFactory = FragmentNoticeViewBinding::inflate
) {
    private val viewModel: NoticeViewViewModel by viewModels {
        NoticeViewViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchNoticeFromBundle(
            arguments?.getString("notice_data")
        )

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            binding.createdAt.text = String.format("Created At: %s", it?.createdAt ?: "")
            binding.noticeTitle.setText(it?.title ?: "")
            binding.noticeDetails.setText(it?.details ?: "")
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}