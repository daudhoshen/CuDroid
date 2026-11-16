package com.ismail.cu.presentation.notice.noticelist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.ismail.cu.R
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef
import com.ismail.cu.model.NoticeData
import com.ismail.cu.presentation.notice.noticeupdate.NoticeUpdateFragment

class NoticeListViewModel : BaseViewModel() {
    val noticeLiveData: LiveData<List<NoticeData?>?>
        field = MutableLiveData<List<NoticeData?>?>()

    fun fetchNoticeListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDbRef.provideNoticeRef()?.addValueEventListener(
            object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val noticeList: MutableList<NoticeData?> = ArrayList()
                    for (noticeSnapshot in snapshot.getChildren()) {
                        if (noticeSnapshot.exists()) noticeList.add(
                            noticeSnapshot.getValue(
                                NoticeData::class.java
                            )
                        )
                    }
                    try {
                        noticeList.reverse()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        noticeLiveData.postValue(noticeList)
                    }
                    fireLoadingEvent(false)
                } else fireLoadingEvent(false)
            }

            override fun onCancelled(error: DatabaseError) {
                fireMessageEvent(error.message)
                fireLoadingEvent(false)
            }
        }
        )
    }

    fun navigateToNoticeViewFragment(noticeData: NoticeData?) {
        if (noticeData != null) {
            fireNavigateEvent(
                R.id.action_noticeListFragment_to_noticeViewFragment,
                Bundle().apply { putString("notice_data", Gson().toJson(noticeData)) }
            )
        } else fireMessageEvent("Something went wrong")
    }

    fun navigateToNoticeUpdateFragment(noticeData: NoticeData?) {
        if (noticeData != null) {
            fireNavigateEvent(
                R.id.action_noticeListFragment_to_noticeUpdateFragment,
                NoticeUpdateFragment.generateBundle(noticeData)
            )
        } else fireMessageEvent("Something went wrong")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoticeListViewModel() as T
        }
    }
}