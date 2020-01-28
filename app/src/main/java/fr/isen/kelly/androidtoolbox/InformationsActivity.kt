package fr.isen.kelly.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_informations.*

class InformationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)
        myList.layoutManager = LinearLayoutManager(this)
        myList.adapter = ContactsAdapter(listOf(Contact("Kelly", true), Contact("Melissa", false)))

        val itemDecoration=DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        myList.addItemDecoration(itemDecoration)
        /*myList.addItemDecoration{
        DividerItemDecoration{
            this@RecyclerActivity
        }*/
    }
}
