package com.ismail.cu.presentation.notice.noticeupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentNoticeUpdateBinding
import com.ismail.cu.model.NoticeData
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class NoticeUpdateFragment : BaseFragment<FragmentNoticeUpdateBinding>(
    viewBindingFactory = FragmentNoticeUpdateBinding::inflate
) {
    private val viewModel: NoticeUpdateViewModel by viewModels {
        NoticeUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.noticeTitle.setText(arguments?.getString("title"))
        binding.noticeDetails.setText(arguments?.getString("details"))

        binding.updateButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateNoticeInputAndUploadToFirebase(
                key = arguments?.getString("key"),
                noticeTitleEt = binding.noticeTitle,
                noticeDetailsEt = binding.noticeDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun generateBundle(noticeData: NoticeData?) : Bundle = Bundle().apply {
            putString("title", noticeData?.title)
            putString("details", noticeData?.details)
            putString("created_at", noticeData?.createdAt)
            putString("key", noticeData?.key)
        }
    }
}