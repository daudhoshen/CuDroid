package com.ismail.cu.presentation.dashboard.chat

import android.os.Bundle
import androidx.activity.viewModels
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.base.event.EventObserver
import com.ismail.cu.databinding.ActivityChatTextListBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeGone
import com.ismail.cu.util.makeVisible
import com.ismail.cu.util.showToast

class ChatTextListActivity : BaseActivity<ActivityChatTextListBinding> (
    onInflate = ActivityChatTextListBinding::inflate
) {
    private val viewModel: ChatTextListViewModel by viewModels {
        ChatTextListViewModel.Factory(
            ownUserId = SharedPref.getUserProfile().userId
        )
    }

    override fun initOnCreate(savedInstanceState: Bundle?) {
        binding.toolBar.title.text = intent?.getStringExtra(CLIENT_NAME) ?: ""

        viewModel.loadingEvent?.observe(this, EventObserver {
            if (it) binding.primaryLoader.makeVisible()
            else binding.primaryLoader.makeGone()
        })

        viewModel.validateIdsAndFetchChatTextListFromFirebase(
            clientUserId = intent?.getStringExtra(CLIENT_USER_ID)
        )

        viewModel.chatTextLiveData.observe(this) {
            binding.chatRecycler.adapter = ChatTextListAdapter().apply {
                submitList(facultyList = it, ownUserId = viewModel.ownUserId)
            }
        }

        binding.sendButton.setOnClickListener {
            viewModel.validateIdsAndUploadToFirebase(
                textInputEt = binding.textInput,
                sendButton = binding.sendButton,
                clientUserId = intent?.getStringExtra(CLIENT_USER_ID)
            )
        }

        viewModel.messageEvent?.observe(this, EventObserver {
            if (it.isNotEmpty()) showToast(it)
        })

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val CLIENT_NAME = "CLIENT_NAME"
        const val CLIENT_USER_ID = "CLIENT_USER_ID"
    }
}