package com.ismail.cu.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismail.cu.base.event.Event
import com.ismail.cu.base.event.NavigateEvent

abstract class BaseViewModel : ViewModel() {
    val messageEvent: LiveData<Event<String>>
        field = MutableLiveData()
    val loadingEvent: LiveData<Event<Boolean>>
        field = MutableLiveData()
    val navigateEvent: LiveData<Event<NavigateEvent>>
        field = MutableLiveData()

    protected fun fireMessageEvent(
        message: String?
    ) {
        if (!message.isNullOrEmpty()) messageEvent.postValue(
            Event(message)
        )
    }

    protected fun fireLoadingEvent(
        isLoading: Boolean
    ) {
        loadingEvent.postValue(
            Event(isLoading)
        )
    }

    fun fireNavigateEvent(
        id: Int,
        bundle: Bundle?
    ) {
        navigateEvent.postValue(
            Event(NavigateEvent(id, bundle))
        )
    }
}