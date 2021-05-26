package com.gmail.konstantin.bezzemelnyi.readdletest2021.utils

import android.content.Context
import com.gmail.konstantin.bezzemelnyi.readdletest2021.R
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.Contact
import java.util.*
import kotlin.random.Random

object DataGenerator {

    fun getContactListData(context: Context, size: Int): List<Contact> {
        val contacts: MutableList<Contact> = ArrayList<Contact>(size)

        val firstNameArr = context.resources.getStringArray(R.array.contact_list_first_names)
        val lastNameArr = context.resources.getStringArray(R.array.contact_list_last_names)
        val emailArr = context.resources.getStringArray(R.array.contact_list_emails)

        for (i in emailArr.indices) {
            val obj = Contact(
                firstName = firstNameArr.random(),
                lastName = lastNameArr.random(),
                email = emailArr[i],
                isOnline = Random.Default.nextBoolean()
            )
            contacts.add(obj)
        }

        contacts.shuffle()
        return contacts
    }


}