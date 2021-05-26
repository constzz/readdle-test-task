package com.gmail.konstantin.bezzemelnyi.readdletest2021.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.konstantin.bezzemelnyi.readdletest2021.R
import com.gmail.konstantin.bezzemelnyi.readdletest2021.adapters.ContactListViewAdapter
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.Contact
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.ContactsApplication
import com.gmail.konstantin.bezzemelnyi.readdletest2021.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_list_simple_item_view.*


class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.ContactsViewModelFactory((application as ContactsApplication).repository)
    }

    private lateinit var contactsListRecyclerView: RecyclerView
    private lateinit var simulateChangesBtn: Button

    private lateinit var contactListLayoutManager: GridLayoutManager
    private lateinit var contactsListViewAdapter: ContactListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initSettings()

        setUpContactListRecyclerView()

        mViewModel.allContacts.observe(this, { contacts ->
            // Update the cached copy of the words in the adapter.
            contacts?.let { contactsListViewAdapter.submitList(it) }
        })

        simulateChangesBtn.setOnClickListener {
            mViewModel.simulateChanges(this)
        }

    }

    private fun initViews() {
        contactsListRecyclerView = findViewById(R.id.contacts_list_recycler_view)
        simulateChangesBtn = findViewById(R.id.main_simulate_changes_btn)
    }

    private fun initSettings() {
        val firstLaunch = !getPreferences(Context.MODE_PRIVATE).contains(CONTACT_LIST_GRID_LAYOUT)
        if (firstLaunch) {
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean(CONTACT_LIST_GRID_LAYOUT, false)
                .apply()
        }
    }

    private fun getListSpanCount(): Int {
        return if (getPreferences(Context.MODE_PRIVATE).getBoolean(
                CONTACT_LIST_GRID_LAYOUT,
                false
            )
        ) {
            return DETAILED_LAYOUT_SPAN_COUNT
        } else SIMPLE_LAYOUT_SPAN_COUNT
    }

    private fun setUpContactListRecyclerView() {
        contactListLayoutManager = GridLayoutManager(this, getListSpanCount())
        contactsListRecyclerView.layoutManager = contactListLayoutManager

        contactsListViewAdapter =
            ContactListViewAdapter(contactListLayoutManager) { selectedContact: Contact ->
                val openContactDetailsIntent =
                    Intent(this@MainActivity, DetailsActivity::class.java)
                openContactDetailsIntent.putExtra(
                    DetailsActivity.selectedContactExtra,
                    selectedContact
                )

                startActivity(openContactDetailsIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }
        contactsListRecyclerView.adapter = contactsListViewAdapter
    }

    private fun switchContactListLayout() {
        val curGridLayoutMode = getListSpanCount() == DETAILED_LAYOUT_SPAN_COUNT

        if (!curGridLayoutMode) {
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean(CONTACT_LIST_GRID_LAYOUT, true)
                .apply()
            contactListLayoutManager.spanCount = DETAILED_LAYOUT_SPAN_COUNT
        } else {
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean(CONTACT_LIST_GRID_LAYOUT, false)
                .apply()
            contactListLayoutManager.spanCount = SIMPLE_LAYOUT_SPAN_COUNT
        }

        contactsListViewAdapter.notifyItemRangeChanged(
            0,
            contactsListViewAdapter.itemCount
        )

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.activity_main_menu_layout_switch_item)?.title =
            if (getListSpanCount() == SIMPLE_LAYOUT_SPAN_COUNT) "Grid" else "List"
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.activity_main_menu_layout_switch_item -> {
                switchContactListLayout()
                item.title =
                    if (getListSpanCount() == SIMPLE_LAYOUT_SPAN_COUNT) getString(R.string.grid)
                    else getString(R.string.list)
            }
            else -> error("Item with this id not exist in menu.")
        }
        return true
    }

    companion object {
        private const val CONTACT_LIST_GRID_LAYOUT = "contactListGridLayout"
        private const val SIMPLE_LAYOUT_SPAN_COUNT = 1
        private const val DETAILED_LAYOUT_SPAN_COUNT = 5
    }
}