package fr.isen.kelly.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter(val contacts: List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(contactView)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.contactName
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contact = contacts.get(position)
        val textView = viewHolder.nameTextView
        textView.text = contact.name
    }

    override fun getItemCount(): Int = contacts.size

}