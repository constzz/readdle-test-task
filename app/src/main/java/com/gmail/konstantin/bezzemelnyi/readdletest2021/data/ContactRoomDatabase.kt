package com.gmail.konstantin.bezzemelnyi.readdletest2021.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    private class ContactDatabaseCallback(
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.contactDao())
                }
            }
        }

        suspend fun populateDatabase(contactDao: ContactDao) {
            contactDao.deleteAllContacts()
            contactDao.insertContact(Contact("Kostia", "Bezze", "kostiantyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Jack", "Nope", "ostiantyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Bet", "Fade", "stiantyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Steve", "Banana", "iantyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Jackson", "Yellow", "iantyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Vienna", "Stick", "antyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Cole", "Brown", "iantyn2004@gmail.com", true))
            contactDao.insertContact(Contact("Tyler", "North", "2004@gmail.com", true))
            contactDao.insertContact(Contact("Lion", "Hide", "04@gmail.com", true))
            contactDao.insertContact(Contact("Alex", "Field", "4@gmail.com", true))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ContactRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                )
                    .addCallback(ContactDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}



