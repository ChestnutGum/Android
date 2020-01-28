package fr.isen.kelly.androidtoolbox

import android.text.BoringLayout
import kotlin.properties.Delegates

class Contact(val name: String, val isOnline:Boolean) {


    companion object{
        private var lastContactId=0
        fun createContactList(numContacts: Int):ArrayList<Contact>{
            val contacts=ArrayList<Contact>()
            for(i in 1..numContacts){
                contacts.add(Contact("Person "+ ++lastContactId,i <= numContacts/2))
            }
            return contacts
        }
    }
}