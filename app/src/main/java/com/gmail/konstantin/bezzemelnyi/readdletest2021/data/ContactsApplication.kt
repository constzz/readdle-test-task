package com.gmail.konstantin.bezzemelnyi.readdletest2021.data

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactsApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ContactRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactRepository(database.contactDao()) }
}