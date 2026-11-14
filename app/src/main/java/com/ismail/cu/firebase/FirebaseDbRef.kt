package com.ismail.cu.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ismail.cu.base.BaseApp
import com.ismail.cu.util.SharedPref.getUserBatch

object FirebaseDbRef {
    private val firebaseDataRef: DatabaseReference
        get() = FirebaseDatabase.getInstance().getReference()

    fun provideBatchRef(): DatabaseReference? {
        return firebaseDataRef?.child("BATCH_REF")
    }

    fun provideStudentRef(): DatabaseReference? {
        return firebaseDataRef?.child("STUDENT")?.child(getUserBatch())
    }

    fun provideScheduleRef(): DatabaseReference? {
        return firebaseDataRef?.child("SCHEDULE")?.child(getUserBatch())
    }

    fun provideNoticeRef(): DatabaseReference? {
        return firebaseDataRef?.child("NOTICE")?.child(getUserBatch())
    }

    fun provideAssignmentRef(): DatabaseReference? {
        return firebaseDataRef?.child("ASSIGNMENT")?.child(getUserBatch())
    }

    fun provideModuleRef(): DatabaseReference? {
        return firebaseDataRef?.child("MODULE")?.child(getUserBatch())
    }

    fun provideFacultyRef(): DatabaseReference? {
        return firebaseDataRef?.child("FACULTY")?.child(getUserBatch())
    }

    fun provideChatRef(): DatabaseReference? {
        return firebaseDataRef?.child("CHAT")?.child(getUserBatch())
    }
}