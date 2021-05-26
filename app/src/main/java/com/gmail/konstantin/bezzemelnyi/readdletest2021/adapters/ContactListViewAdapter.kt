package com.gmail.konstantin.bezzemelnyi.readdletest2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.konstantin.bezzemelnyi.readdletest2021.R
import com.gmail.konstantin.bezzemelnyi.readdletest2021.data.Contact
import com.squareup.picasso.Picasso


class ContactListViewAdapter(private val layoutManager: GridLayoutManager?, private var onItemClickListener: OnItemClickListener) :
        ListAdapter<Contact, ContactListViewAdapter.SimpleViewHolder>(ContactsComparator()) {

    enum class ViewType {
        SIMPLE,
        DETAILED
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager?.spanCount == 1) ViewType.DETAILED.ordinal
        else ViewType.SIMPLE.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return when (viewType) {
            ViewType.DETAILED.ordinal -> DetailedViewHolder.create(parent)
            else -> SimpleViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val currentContact = getItem(position)

        if (holder is DetailedViewHolder) {
            holder.contactFullNameItemView.text = currentContact.fullName
        }

        holder.contactIsOnlineStatusImageItemView.visibility =
                if (currentContact.isOnline) View.VISIBLE
                else View.GONE

        Picasso.get()
                .load(currentContact.imageUri)
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_error)
                .into(holder.contactProfileImageItemView)

        holder.itemView.setOnClickListener {
            onItemClickListener(currentContact)
        }
    }

    open class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open var contactProfileImageItemView: ImageView = itemView.findViewById(R.id.contact_profile_image)
        open var contactIsOnlineStatusImageItemView: ImageView = itemView.findViewById(R.id.contact_online_status)

        companion object {
            fun create(parent: ViewGroup): SimpleViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.contact_list_simple_item_view, parent, false)
                return SimpleViewHolder(view)
            }
        }
    }

    class DetailedViewHolder(itemView: View) : SimpleViewHolder(itemView) {

        override var contactProfileImageItemView: ImageView = itemView.findViewById(R.id.contact_profile_image)
        override var contactIsOnlineStatusImageItemView: ImageView = itemView.findViewById(R.id.contact_online_status)

        var contactFullNameItemView: TextView = itemView.findViewById(R.id.contact_full_name)

        companion object {
            fun create(parent: ViewGroup): DetailedViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.contact_list_detailed_item_view, parent, false)
                return DetailedViewHolder(view)
            }
        }
    }

    class ContactsComparator : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.firstName == newItem.firstName
        }
    }

}

typealias OnItemClickListener = (contact: Contact) -> Unit