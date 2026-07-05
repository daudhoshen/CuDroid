package com.ismail.cu.presentation.faculty.facultyadd

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentFacultyAddBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.widget.PrimaryLoader

class FacultyAddFragment : BaseFragment<FragmentFacultyAddBinding>(
    viewBindingFactory = FragmentFacultyAddBinding::inflate
) {
    private val viewModel: FacultyAddViewModel by viewModels {
        FacultyAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.genderZero = resources.getStringArray(R.array.gender_spinner)[0]

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.gender = resources.getStringArray(R.array.gender_spinner)[pos]
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.saveButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateFacultyInputsAndUploadToFirebase(
                facultyNameEt = binding.facultyName,
                facultyDesignationEt = binding.facultyDesignation,
                facultyPhoneEt = binding.facultyPhone
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}