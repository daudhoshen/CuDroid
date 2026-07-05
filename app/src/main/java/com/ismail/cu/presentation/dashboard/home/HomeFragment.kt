package com.ismail.cu.presentation.dashboard.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentHomeBinding
import com.ismail.cu.presentation.assignment.AssignmentActivity
import com.ismail.cu.presentation.faculty.FacultyActivity
import com.ismail.cu.presentation.module.ModuleActivity
import com.ismail.cu.presentation.notice.NoticeActivity
import com.ismail.cu.presentation.notice.noticelist.NoticeListAdapter
import com.ismail.cu.presentation.portal.PortalActivity
import com.ismail.cu.presentation.schedule.ScheduleActivity
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeVisible
import com.ismail.cu.widget.PrimaryLoader

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    viewBindingFactory = FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchUserProfileLiveData()
        viewModel.fetchNoticeListFromFirebase()

        viewModel.userProfileDataLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.userInfo.text = String.format(
                    "%s\n%s",
                    "${it.name} ${if (it.admin == true) "(Admin)" else ""}",
                    SharedPref.getUserBatch()
                )
            }
        }

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.recentNoticeLayer.makeVisible()
                binding.recentNoticeRecycler.adapter = NoticeListAdapter(
                    null,null
                ).apply {
                    submitList(it)
                }
                binding.viewAllButton.setOnClickListener {
                    openAnotherActivity(NoticeActivity::class.java)
                }
            }
        }

        binding.classScheduleButton.setOnClickListener {
            openAnotherActivity(ScheduleActivity::class.java)
        }
        binding.noticeButton.setOnClickListener {
            openAnotherActivity(NoticeActivity::class.java)
        }
        binding.assignmentButton.setOnClickListener {
            openAnotherActivity(AssignmentActivity::class.java)
        }
        binding.courseModuleButton.setOnClickListener {
            openAnotherActivity(ModuleActivity::class.java)
        }
        binding.StudentPortalButton.setOnClickListener {
            openAnotherActivity(PortalActivity::class.java)
        }
        binding.facultyButton.setOnClickListener {
            openAnotherActivity(FacultyActivity::class.java)
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    private fun openAnotherActivity(destinationClass: Class<*>) {
        startActivity(Intent(context, destinationClass))
    }
}