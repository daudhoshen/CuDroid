package com.ismail.cu.presentation.assignment.assignmentview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.model.AssignmentData

class AssignmentViewViewModel : BaseViewModel() {
    val assignmentLiveData: LiveData<AssignmentData?>
        field = MutableLiveData<AssignmentData?>()

    fun fetchAssignmentFromBundle(bundle: String?) {
        try {
            assignmentLiveData.postValue(
                Gson().fromJson(bundle, AssignmentData::class.java)
            )
        } catch (e: Exception) {
            fireMessageEvent(e.localizedMessage)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AssignmentViewViewModel() as T
        }
    }
}