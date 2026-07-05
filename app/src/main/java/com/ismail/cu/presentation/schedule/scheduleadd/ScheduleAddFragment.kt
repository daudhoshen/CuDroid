package com.ismail.cu.presentation.schedule.scheduleadd

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentScheduleAddBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class ScheduleAddFragment : BaseFragment<FragmentScheduleAddBinding>(
    viewBindingFactory = FragmentScheduleAddBinding::inflate
) {
    private val viewModel: ScheduleAddViewModel by viewModels {
        ScheduleAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.dayZero = resources.getStringArray(R.array.day_spinner)[0]

        binding.daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.day = resources.getStringArray(R.array.day_spinner)[pos]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.saveButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateScheduleInputAndUploadToFirebase(
                courseTitleEt = binding.courseTitle,
                courseCodeEt = binding.courseCode,
                lecturerNameEt = binding.lecturerName,
                startEndTimeEt = binding.startEndTime,
                roomNoEt = binding.roomNo
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}