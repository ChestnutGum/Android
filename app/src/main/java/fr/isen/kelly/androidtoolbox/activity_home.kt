package fr.isen.kelly.androidtoolbox
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class activity_home : AppCompatActivity() {

    private lateinit var myButtonCycle:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //bouton de cycle de vie
        myButtonCycle=findViewById(R.id.button_cycle)

        //action qui s'exécute quand on clique sur le bouton "cycle de vie"
        myButtonCycle.setOnClickListener {
            //redirection vers l'activité activity_cycle
            val CycleActivity = Intent(this@activity_home, activity_cycle::class.java)
            startActivity(CycleActivity)
        }
    }
}
