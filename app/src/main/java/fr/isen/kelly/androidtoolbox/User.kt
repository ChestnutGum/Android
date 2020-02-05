package fr.isen.kelly.androidtoolbox

import android.graphics.Bitmap
import com.squareup.picasso.Picasso

class User(val name: String, val email:String,val adresse:String,val photo:String) {
    //,val photo:Bitmap
    /*companion object{
        private var lastContactId=0
        fun createContactList(numContacts: Int):ArrayList<Contact>{
            val contacts=ArrayList<Contact>()
            for(i in 1..numContacts){
                contacts.add(Contact("Person "+ ++lastContactId,i <= numContacts/2))
            }
            return contacts
        }
    }*/
}