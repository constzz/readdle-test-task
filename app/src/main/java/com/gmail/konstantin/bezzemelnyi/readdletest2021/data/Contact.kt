package com.gmail.konstantin.bezzemelnyi.readdletest2021.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.konstantin.bezzemelnyi.readdletest2021.utils.GravatarUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contact_table")
data class Contact(
        @ColumnInfo(name = "first_name") val firstName: String,
        @ColumnInfo(name = "last_name") val lastName: String,
        @PrimaryKey @ColumnInfo(name = "email") val email: String,
        @ColumnInfo(name = "is_online") var isOnline: Boolean,
        val imageUri: String = GravatarUtil.getProfileImageLink(email),
        val fullName: String = "$firstName $lastName"
) : Parcelable