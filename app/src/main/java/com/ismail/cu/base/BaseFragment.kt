package com.ismail.cu.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ismail.cu.base.event.EventObserver
import com.ismail.cu.util.makeGone
import com.ismail.cu.util.makeVisible
import com.ismail.cu.util.showToast
import com.ismail.cu.widget.PrimaryLoader

abstract class BaseFragment<VB: ViewBinding>(
    private val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected abstract fun initViewModel(): BaseViewModel?

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onInflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initOnViewCreated(savedInstanceState)

        initViewModel()?.messageEvent?.observe(
            viewLifecycleOwner, EventObserver {
                if (it.isNotEmpty()) context.showToast(it)
            }
        )
        initViewModel()?.loadingEvent?.observe(
            viewLifecycleOwner, EventObserver {
                if (initPrimaryLoader() != null) {
                    when(it) {
                        true -> initPrimaryLoader().makeVisible()
                        else -> initPrimaryLoader().makeGone()
                    }
                }
            }
        )
        initViewModel()?.navigateEvent?.observe(
            viewLifecycleOwner, EventObserver {
                onNavigateEvent(it.id, it.bundle)
            }
        )
    }

    protected abstract fun initOnViewCreated(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected abstract fun initPrimaryLoader(): PrimaryLoader?

    protected open fun onNavigateEvent(id: Int, bundle: Bundle?) {}
}