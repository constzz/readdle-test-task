package com.gmail.konstantin.bezzemelnyi.readdletest2021.data

import androidx.annotation.WorkerThread

class ContactRepository(private val contactDao: ContactDao) {
    val allContacts = contactDao.getFirstNameAlphabetizedContacts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contact: Contact) {
        contactDao.insertContact(contact)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllContacts() {
        contactDao.deleteAllContacts()
    }
}