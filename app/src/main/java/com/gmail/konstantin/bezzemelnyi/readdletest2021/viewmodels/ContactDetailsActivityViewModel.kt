package com.gmail.konstantin.bezzemelnyi.readdletest2021.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.ContactRepository

class ContactDetailsActivityViewModel(
    private val repository: ContactRepository,
) : ViewModel() {

    class ContactDetailsViewModelFactory(
        private val repository: ContactRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactDetailsActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactDetailsActivityViewModel(repository) as T
            }
            error("Unknown ViewModel class")
        }
    }
}