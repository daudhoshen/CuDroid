package com.ismail.cu.presentation.module.moduleupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef.provideModuleRef
import com.ismail.cu.model.ModuleData
import com.ismail.cu.util.getCurrentDate
import com.ismail.cu.util.showErrorOnUi

class ModuleUpdateViewModel : BaseViewModel() {

    fun validateModuleInputsAndUploadToFirebase(
        key: String?, moduleTitleEt: EditText, moduleLinkEt: EditText
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
            moduleData = ModuleData(
                title = moduleTitleEt.text.toString(),
                link = moduleLinkEt.text.toString(),
                createdAt = getCurrentDate(), key = key
            )
        )
    }

    private fun uploadModuleTOFirebase(moduleData: ModuleData) {
        fireLoadingEvent(true)
        provideModuleRef()?.child(moduleData.key ?: "")?.setValue(
            moduleData
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Course Module Updated Successfully")
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
            return ModuleUpdateViewModel() as T
        }
    }
}