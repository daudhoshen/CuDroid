package com.ismail.cu.presentation.classmatelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef.provideStudentRef
import com.ismail.cu.model.UserProfileData

class ClassMateListViewModel(
    private val ownUserId: String?
) : BaseViewModel() {
    val classMateLiveData: LiveData<List<UserProfileData?>?>
        field = MutableLiveData<List<UserProfileData?>?>()

    fun fetchClassMateListFromFirebase() {
        fireLoadingEvent(true)
        provideStudentRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val classMateList: MutableList<UserProfileData?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) {
                                moduleSnapshot.getValue(UserProfileData::class.java)?.apply {
                                    if (userId != ownUserId) classMateList.add(this)
                                }
                            }
                        }
                        try {
                            classMateList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            classMateLiveData.postValue(classMateList)
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

    /*fun navigateToFacultyUpdateFragment(facultyData: FacultyData?) {
        if (facultyData != null) {
            fireNavigateEvent(
                R.id.action_facultyListFragment_to_facultyUpdateFragment,
                FacultyUpdateFragment.generateBundle(facultyData)
            )
        } else fireMessageEvent("Something went wrong")
    }*/

    class Factory(
        private val ownUserId: String?
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClassMateListViewModel(ownUserId) as T
        }
    }
}