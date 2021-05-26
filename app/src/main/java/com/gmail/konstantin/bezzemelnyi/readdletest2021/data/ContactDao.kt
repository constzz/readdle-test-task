package com.gmail.konstantin.bezzemelnyi.readdletest2021.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContacts(vararg contacts: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAllContacts()

    @Query("SELECT * FROM contact_table ORDER BY first_name ASC")
    fun getFirstNameAlphabetizedContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contact_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomContact(): Contact?

}