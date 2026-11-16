package com.ismail.cu.presentation.assignment.assignmentadd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef.provideAssignmentRef
import com.ismail.cu.model.AssignmentData
import com.ismail.cu.util.getCurrentDate
import com.ismail.cu.util.showErrorOnUi

class AssignmentAddViewModel : BaseViewModel() {

    fun validateAssignmentInputAndUploadToFirebase(
        assignmentTitleEt: EditText, assignmentDetailsEt: EditText
    ) {
        if (assignmentTitleEt.text.toString().isEmpty()) {
            assignmentTitleEt.showErrorOnUi("Enter Assignment Title")
            return
        }
        if (assignmentDetailsEt.text.toString().isEmpty()) {
            assignmentDetailsEt.showErrorOnUi("Enter Assignment Details")
            return
        }
        uploadAssignmentTOFirebase(
            assignmentTitleEt.text.toString(),
            assignmentDetailsEt.text.toString(),
            getCurrentDate()
        )
    }

    private fun uploadAssignmentTOFirebase(title: String, details: String, date: String) {
        fireLoadingEvent(true)
        val dbPushRes = provideAssignmentRef()?.push()
        dbPushRes?.setValue(
            AssignmentData(
                title = title,
                details = details,
                createdAt = date,
                key = dbPushRes.key
            )
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Assignment Saved Successfully")
                fireNavigateEvent(0, null)
            } else {
                fireMessageEvent(task.exception?.localizedMessage)
            }
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AssignmentAddViewModel() as T
        }
    }
}