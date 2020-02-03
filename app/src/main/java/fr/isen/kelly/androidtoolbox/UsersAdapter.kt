package fr.isen.kelly.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(val users: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val userView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return ViewHolder(userView)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView = itemView.userName
            val mailTextView = itemView.userMail
            val addressTextView = itemView.userAddr
        }


        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val user = users.get(position)
            //viewHolder.nameTextView.text(user.name)
            val textName = viewHolder.nameTextView
            val textMail=viewHolder.mailTextView
            val textAddr=viewHolder.addressTextView
            textName.text = user.name
            textMail.text=user.email
            textAddr.text=user.adresse
        }

        override fun getItemCount(): Int = users.size
}