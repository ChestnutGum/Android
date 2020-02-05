package fr.isen.kelly.androidtoolbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(val users: ArrayList<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

            override fun getItemCount(): Int = users.size

            override fun onCreateViewHolder(viewGroup:ViewGroup, i:Int):UsersAdapter.ViewHolder {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
                return ViewHolder(view)
            }
            override fun onBindViewHolder(viewHolder:ViewHolder, i:Int) {
                viewHolder.myUserName.text = users[i].name
                viewHolder.myUserEmail.text = users[i].email
                viewHolder.myUserAddr.text = users[i].adresse
                Picasso.get().load(users[i].photo).resize(50, 50).into(viewHolder.imgAndroid)
            }
            inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
                internal var myUserName: TextView = view.findViewById(R.id.userName) as TextView
                internal var imgAndroid:ImageView = view.findViewById(R.id.photoUser) as ImageView
                internal var myUserEmail: TextView = view.findViewById(R.id.userMail) as TextView
                internal var myUserAddr: TextView = view.findViewById(R.id.userAddr) as TextView

            }


}
