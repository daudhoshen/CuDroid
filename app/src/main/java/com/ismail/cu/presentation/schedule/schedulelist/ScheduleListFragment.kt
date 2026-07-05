package com.ismail.cu.presentation.schedule.schedulelist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentScheduleListBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeVisible
import com.ismail.cu.widget.PrimaryLoader

class ScheduleListFragment : BaseFragment<FragmentScheduleListBinding>(
    viewBindingFactory = FragmentScheduleListBinding::inflate
) {
    private val viewModel: ScheduleListViewModel by viewModels {
        ScheduleListViewModel.Factory()
    }
    private val adapter: ScheduleListAdapter by lazy {
        ScheduleListAdapter(
            onUpdateClickListener = {
                viewModel.navigateToScheduleUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchScheduleListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().admin
        if (adapter.isAdmin == true) binding.addButton.makeVisible()

        viewModel.scheduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.scheduleRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_scheduleListFragment_to_scheduleAddFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}