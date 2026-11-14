package com.ismail.cu.presentation.dashboard.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentChatListBinding
import com.ismail.cu.presentation.classmatelist.ClassmateListActivity
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class ChatListFragment : BaseFragment<FragmentChatListBinding>(
    onInflate = FragmentChatListBinding::inflate
) {
    private val viewModel: ChatListViewModel by viewModels {
        ChatListViewModel.Factory(
            ownUserId = SharedPref.getUserProfile().userId
        )
    }
    private val adapter: ChatListAdapter by lazy {
        ChatListAdapter(
            onClickListener = {
                startActivity(
                    Intent(requireContext(), ChatTextListActivity::class.java).apply {
                        putExtra(ChatTextListActivity.CLIENT_NAME, it?.name)
                        putExtra(ChatTextListActivity.CLIENT_USER_ID, it?.userId)
                    }
                )
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnViewCreated(savedInstanceState: Bundle?) {

        viewModel.fetchChatListThatContainsMyId()

        binding.chatRecycler.adapter = adapter

        viewModel.chatsLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) adapter.submitList(it)
        }

        binding.addChatButton.setOnClickListener {
            startActivity(
                Intent(requireContext(), ClassmateListActivity::class.java)
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader
}