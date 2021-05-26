package com.gmail.konstantin.bezzemelnyi.readdletest2021.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.Contact
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.ContactRepository
import com.gmail.konstantin.bezzemelnyi.readdletest2021.utils.DataGenerator
import kotlinx.coroutines.launch

class MainActivityViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    val allContacts = contactRepository.allContacts.asLiveData()

    private fun insertContact(contact: Contact) = viewModelScope.launch {
        contactRepository.insert(contact)
    }

    private fun deleteContact(contact: Contact) = viewModelScope.launch {
        contactRepository.delete(contact)
    }

    private fun deleteAllContacts() {
        viewModelScope.launch {
            contactRepository.deleteAllContacts()
        }
    }

    fun simulateChanges(context: Context) {
        deleteAllContacts()
        val randomData = DataGenerator.getContactListData(context, 30)
        randomData.forEach { contact -> insertContact(contact) }
    }


    class ContactsViewModelFactory(private val repository: ContactRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(repository) as T
            }
            error("Unknown ViewModel class")
        }
    }
}