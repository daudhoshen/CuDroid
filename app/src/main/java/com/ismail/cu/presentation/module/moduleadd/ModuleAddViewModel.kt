package com.ismail.cu.presentation.module.moduleadd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef.provideModuleRef
import com.ismail.cu.model.ModuleData
import com.ismail.cu.util.showErrorOnUi

class ModuleAddViewModel : BaseViewModel() {

    fun validateModuleInputsAndUploadToFirebase(
        moduleTitleEt: EditText,
        moduleLinkEt: EditText,
        createdAt: String
    ) {
        if (moduleTitleEt.text.toString().isEmpty()) {
            moduleTitleEt.showErrorOnUi("Enter Course Module Title")
            return
        }
        if (moduleLinkEt.text.toString().isEmpty()) {
            moduleLinkEt.showErrorOnUi("Enter Course Module Link")
            return
        }
        uploadModuleTOFirebase(
            moduleTitleEt.text.toString(), moduleLinkEt.text.toString(), createdAt
        )
    }

    private fun uploadModuleTOFirebase(moduleTitle: String, moduleLink: String, createdAt: String) {
        fireLoadingEvent(true)
        val dbPushRef: DatabaseReference? = provideModuleRef()?.push()
        dbPushRef?.setValue(
            ModuleData(
                title = moduleTitle,
                link = moduleLink,
                createdAt = createdAt,
                dbPushRef.key
            )
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Course Module Saved Successfully")
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
            return ModuleAddViewModel() as T
        }
    }
}