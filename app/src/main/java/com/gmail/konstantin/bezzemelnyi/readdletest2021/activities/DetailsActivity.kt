package com.gmail.konstantin.bezzemelnyi.readdletest2021.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gmail.konstantin.bezzemelnyi.readdletest2021.R
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.Contact
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.ContactsApplication
import com.gmail.konstantin.bezzemelnyi.readdletest2021.viewmodels.ContactDetailsActivityViewModel
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity() {

    private val mViewModel: ContactDetailsActivityViewModel by viewModels {
        ContactDetailsActivityViewModel.ContactDetailsViewModelFactory((application as ContactsApplication).repository)
    }

    private var mContact: Contact? = null

    private lateinit var contactProfileImage: ImageView
    private lateinit var contactOnlineStatusTV: TextView
    private lateinit var contactEmailTV: TextView
    private lateinit var contactFullNameTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initViews()
        supportActionBar?.title = getString(R.string.details_activity_support_action_bar_title)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        mContact = intent.getParcelableExtra(selectedContactExtra)

        if (!receivedContactIsNull()) {
            displayContactInformation()
        }
    }

    private fun initViews() {
        contactProfileImage = findViewById(R.id.contact_profile_image)
        contactEmailTV = findViewById(R.id.contact_email)
        contactFullNameTV = findViewById(R.id.contact_full_name)
        contactOnlineStatusTV = findViewById(R.id.contact_online_status)
    }

    private fun displayContactInformation() {
        val contact = mContact as Contact
        contactFullNameTV.text = contact.fullName
        contactEmailTV.text = contact.email
        contactOnlineStatusTV.text =
            if (contact.isOnline) getString(R.string.online) else getString(
                R.string.offline
            )
        Picasso.get()
            .load(contact.imageUri)
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.avatar_error)
            .into(contactProfileImage)
    }

    private fun receivedContactIsNull(): Boolean {
        return if (mContact == null) {
            showContactIsNullError()
            true
        } else false
    }

    private fun showContactIsNullError() {
        Toast.makeText(this, getString(R.string.not_founded_contact), Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val selectedContactExtra = "SELECTED_CONTACT_EXTRA"
    }
}