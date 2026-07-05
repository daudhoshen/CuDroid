package com.ismail.cu.presentation.notice.noticeupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef.provideNoticeRef
import com.ismail.cu.model.NoticeData
import com.ismail.cu.util.getCurrentDate
import com.ismail.cu.util.showErrorOnUi

class NoticeUpdateViewModel : BaseViewModel() {

    fun validateNoticeInputAndUploadToFirebase(
        key: String?, noticeTitleEt: EditText, noticeDetailsEt: EditText
    ) {
        if (noticeTitleEt.getText().toString().isEmpty()) {
            noticeTitleEt.showErrorOnUi("Enter Notice Title")
            return
        }
        if (noticeDetailsEt.getText().toString().isEmpty()) {
            noticeDetailsEt.showErrorOnUi("Enter Notice Details")
            return
        }
        uploadNoticeTOFirebase(
            noticeData = NoticeData(
                title = noticeTitleEt.text.toString(),
                details = noticeDetailsEt.text.toString(),
                createdAt = getCurrentDate(), key = key
            )
        )
    }

    private fun uploadNoticeTOFirebase(noticeData: NoticeData) {
        fireLoadingEvent(true)
        provideNoticeRef()?.child(noticeData.key ?: "")?.setValue(
            noticeData
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Notice Updated Successfully")
                fireNavigateEvent(0, null)
            } else fireMessageEvent(task.exception!!.localizedMessage)
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoticeUpdateViewModel() as T
        }
    }
}