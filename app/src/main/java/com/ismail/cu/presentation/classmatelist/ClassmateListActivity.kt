package com.ismail.cu.presentation.classmatelist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.ismail.cu.R
import com.ismail.cu.base.BaseActivity
import com.ismail.cu.base.event.EventObserver
import com.ismail.cu.databinding.ActivityClassmateListBinding
import com.ismail.cu.presentation.dashboard.chat.ChatTextListActivity
import com.ismail.cu.util.makeGone
import com.ismail.cu.util.makeVisible
import com.ismail.cu.util.showToast

class ClassmateListActivity : BaseActivity<ActivityClassmateListBinding>(
    onInflate = ActivityClassmateListBinding::inflate
) {
    private val viewModel: ClassMateListViewModel by viewModels {
        ClassMateListViewModel.Factory(
            ownUserId = FirebaseAuth.getInstance().currentUser?.uid
        )
    }
    private val adapter: ClassMateListAdapter by lazy {
        ClassMateListAdapter(
            onClickListener = {
                startActivity(
                    Intent(this, ChatTextListActivity::class.java).apply {
                        putExtra(ChatTextListActivity.CLIENT_NAME, it?.name)
                        putExtra(ChatTextListActivity.CLIENT_USER_ID, it?.userId)
                    }
                )
            },
            onCallNowClickListener = {
                try {
                    startActivity(
                        Intent().apply {
                            action = Intent.ACTION_DIAL
                            data = Uri.parse("tel:$it")
                        }
                    )
                } catch (e: Exception) {
                    showToast("something went wrong")
                    e.printStackTrace()
                }
            }
        )
    }

    override fun initOnCreate(savedInstanceState: Bundle?) {

        binding.toolBar.title.text = getString(R.string.class_mates)

        viewModel.loadingEvent?.observe(this, EventObserver {
            if (it) binding.primaryLoader.makeVisible()
            else binding.primaryLoader.makeGone()
        })

        viewModel.fetchClassMateListFromFirebase()

        viewModel.classMateLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                binding.classMateRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        viewModel.messageEvent?.observe(this, EventObserver {
            if (it.isNotEmpty()) showToast(it)
        })

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}