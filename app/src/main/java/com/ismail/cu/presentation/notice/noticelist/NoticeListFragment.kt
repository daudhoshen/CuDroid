package com.ismail.cu.presentation.notice.noticelist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentNoticeListBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeVisible
import com.ismail.cu.widget.PrimaryLoader

class NoticeListFragment : BaseFragment<FragmentNoticeListBinding>(
    viewBindingFactory = FragmentNoticeListBinding::inflate
) {
    private val viewModel: NoticeListViewModel by viewModels {
        NoticeListViewModel.Factory()
    }
    private val adapter: NoticeListAdapter by lazy {
        NoticeListAdapter(
            onClickListener = {
                viewModel.navigateToNoticeViewFragment(it)
            },
            onUpdateClickListener = {
                viewModel.navigateToNoticeUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchNoticeListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().admin
        if (adapter.isAdmin == true) binding.addNoticeButton.makeVisible()

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.noticeRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addNoticeButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_noticeListFragment_to_addNoticeFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}