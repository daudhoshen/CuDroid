package com.ismail.cu.presentation.faculty.facultylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.ismail.cu.R
import com.ismail.cu.base.BaseViewModel
import com.ismail.cu.firebase.FirebaseDbRef.provideFacultyRef
import com.ismail.cu.model.FacultyData
import com.ismail.cu.presentation.faculty.facultyupdate.FacultyUpdateFragment

class FacultyListViewModel : BaseViewModel() {
    val facultyLiveData: LiveData<List<FacultyData?>?>
        field = MutableLiveData<List<FacultyData?>?>()

    fun fetchFacultyListFromFirebase() {
        fireLoadingEvent(true)
        provideFacultyRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val facultyList: MutableList<FacultyData?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) {
                                facultyList.add(
                                    moduleSnapshot.getValue(FacultyData::class.java)
                                )
                            }
                        }
                        try {
                            facultyList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            facultyLiveData.postValue(facultyList)
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

    fun navigateToFacultyUpdateFragment(facultyData: FacultyData?) {
        if (facultyData != null) {
            fireNavigateEvent(
                R.id.action_facultyListFragment_to_facultyUpdateFragment,
                FacultyUpdateFragment.generateBundle(facultyData)
            )
        } else fireMessageEvent("Something went wrong")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FacultyListViewModel() as T
        }
    }
}