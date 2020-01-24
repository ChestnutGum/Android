package fr.isen.kelly.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class activity_home : AppCompatActivity() {

    private lateinit var myButtonCycle:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //bouton de cycle de vie
        myButtonCycle=findViewById(R.id.button_cycle)


        // set on-click listener
        myButtonCycle.setOnClickListener {

            //redirection vers l'activité activity_cycle
            val CycleActivity = Intent(this@activity_home, activity_cycle::class.java)
            startActivity(CycleActivity)


        }
    }
}
