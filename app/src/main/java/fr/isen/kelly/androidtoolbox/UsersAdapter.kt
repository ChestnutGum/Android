package fr.isen.kelly.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(val users: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val userView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return ViewHolder(userView)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nameTextView = itemView.userName
            var mailTextView = itemView.userMail
            var addressTextView = itemView.userAddr
            //var pictImageView= itemView.photoUser
            /*private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.photoUser)


            var imageUrl = imageUrls[position]
            holder?.updateWithUrl(imageUrl)
            */
        }


        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            var user = users.get(position)
            viewHolder.nameTextView.text = user.name
            viewHolder.mailTextView.text = user.email
            viewHolder.addressTextView.text = user.adresse

        }



        override fun getItemCount(): Int = users.size
}