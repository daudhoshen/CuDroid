package com.ismail.cu.base

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ismail.cu.util.SharedPref

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPref.init(applicationContext)
    }
}
