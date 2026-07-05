package com.ismail.cu.presentation.faculty.facultylist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ismail.cu.R
import com.ismail.cu.base.BaseFragment
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.databinding.FragmentFacultyListBinding
import com.ismail.cu.util.SharedPref
import com.ismail.cu.util.makeVisible
import com.ismail.cu.util.showToast
import com.ismail.cu.widget.PrimaryLoader

class FacultyListFragment : BaseFragment<FragmentFacultyListBinding>(
    viewBindingFactory = FragmentFacultyListBinding::inflate
) {
    private val viewModel: FacultyListViewModel by viewModels {
        FacultyListViewModel.Factory()
    }
    private val adapter: FacultyListAdapter by lazy {
        FacultyListAdapter(
            onCallNowClickListener = {
                try {
                    startActivity(
                        Intent().apply {
                            action = Intent.ACTION_DIAL
                            data = Uri.parse("tel:$it")
                        }
                    )
                } catch (e: Exception) {
                    context?.showToast("something went wrong")
                    e.printStackTrace()
                }
            },
            onUpdateClickListener = {
                viewModel.navigateToFacultyUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchFacultyListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().admin
        if (adapter.isAdmin == true) binding.addButton.makeVisible()

        viewModel.facultyLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.moduleRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_facultyListFragment_to_facultyAddFragment,null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}